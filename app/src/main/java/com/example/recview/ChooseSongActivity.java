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
import java.util.concurrent.ThreadLocalRandom;

public class ChooseSongActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, Serializable {

private AudioModel song;
private List<AudioModel> audio;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);
        Bundle bundle = getIntent().getExtras();
        // data to populate the RecyclerView with
        ArrayList<String> songs = new ArrayList<>();
        Toast.makeText(this, "b4 import", Toast.LENGTH_SHORT).show();
        audio = MainActivity.audioFetcher.getAudio();
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




    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ChooseSongActivity.this, PlayerActivity.class);
        intent.putExtra("currentSong", (Serializable) song);
        for(AudioModel audioModel: audio) {
            if (audioModel.name == adapter.getItem(position)) {
                intent.putExtra("path", audioModel.path);
                intent.putExtra("album", audioModel.album);
                intent.putExtra("name", audioModel.name);
                song = audioModel;
            }
        }
        intent.putExtra("name", adapter.getItem(position));
        startActivity(intent);
    }
}