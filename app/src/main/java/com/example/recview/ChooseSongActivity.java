package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseSongActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, Serializable { //Recycler view activity to display and choose songs from
    private List<AudioModel> audio;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);

        // data to populate the RecyclerView with
        ArrayList<String> songs = new ArrayList<>();
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
    public void onItemClick(int position) { //when item in list clicked go to play that song
        Intent intent = new Intent(ChooseSongActivity.this, PlayerActivity.class);

        //search list of songs until it matches the name of the song clicked
        for(AudioModel audioModel: audio) {
            if (audioModel.name.equals(adapter.getItem(position))) {
                intent.putExtra("path", audioModel.path);
                intent.putExtra("album", audioModel.album);
            }
        }
        //pass necessary data to player activity
        intent.putExtra("name", adapter.getItem(position));
        startActivity(intent);
    }
}