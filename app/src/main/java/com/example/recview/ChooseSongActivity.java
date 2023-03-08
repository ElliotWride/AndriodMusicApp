package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseSongActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, Serializable {
private List<AudioModel> audio = new ArrayList<>();
private AudioModel song;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);

        // data to populate the RecyclerView with
        ArrayList<String> songs = new ArrayList<>();
        Toast.makeText(this, "b4 import", Toast.LENGTH_SHORT).show();
        getAllAudioFromDevice();

        for (AudioModel audioModel: audio){
            songs.add(audioModel.name);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, songs);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    private void getAllAudioFromDevice() {
        ContentResolver contentResolver = getContentResolver();
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


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ChooseSongActivity.this, PlayerActivity.class);
        intent.putExtra("currentSong", (Serializable) song);
        for(AudioModel audioModel: audio) {
            if (audioModel.name == adapter.getItem(position)) {
                intent.putExtra("path", audioModel.path);
                song = audioModel;
            }
        }
        intent.putExtra("name", adapter.getItem(position));
        startActivity(intent);
    }
}