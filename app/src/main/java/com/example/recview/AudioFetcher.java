package com.example.recview;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.ContentResolver;

import java.util.ArrayList;
import java.util.List;


public class AudioFetcher {

    private List<AudioModel> audio = new ArrayList<>();

    public AudioFetcher(Context context){
        getAllAudioFromDevice(context);
    }
    private void getAllAudioFromDevice(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null,selection, null, sortOrder);
        ArrayList<AudioModel> audioList = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                // Save to audioList
                try {
                    this.audio.add(new AudioModel(data, title, album, artist));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
        cursor.close();

    }

    public List<AudioModel> getAudio() {
        return audio;
    }
}
