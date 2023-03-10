package com.example.recview;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;


public class AudioFetcher{ //class to handle importing music from device storage
    private final List<AudioModel> audio = new ArrayList<>();//List of all imported audio information

    public AudioFetcher(Context context){//constructor to give class context from activity that called it
        getAllAudioFromDevice(context);
    }

    private void getAllAudioFromDevice(Context context) {
        //set up contentResolver to search for audio files
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null,selection, null, sortOrder);

        //go through all music files and store various meta data and audio
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range") String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                // Save to audioList
                try {
                    this.audio.add(new AudioModel(data, title, album, artist));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            cursor.close();
        }
    }

    public List<AudioModel> getAudio() {
        return audio;
    }
}
