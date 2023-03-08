package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

public class PlayerActivity extends AppCompatActivity implements Serializable {

    // Instantiating the MediaPlayer class
    MediaPlayer music;
    String currentSong;
    String oldSong;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Bundle bundle = getIntent().getExtras();
        //String song =  "res/raw/";
        //song += bundle.getString("name")+ ".mp3";
        //Toast.makeText(this, song, Toast.LENGTH_SHORT).show();
        // Adding the music file to our
        // newly created object music
        currentSong = bundle.getString("path");
        //Toast.makeText(this, currentSong, Toast.LENGTH_SHORT).show();
        MainActivity.audioManager.setContext(this);
        Toast.makeText(this,MainActivity.audioManager.getTest(), Toast.LENGTH_SHORT).show();
        MainActivity.audioManager.setTest(bundle.getString("name"));

    }

    // Playing the music
    public void musicplay(View v)
    {
        MainActivity.audioManager.playSong(currentSong);
    }

    // Pausing the music
    public void musicpause(View v)
    {
        MainActivity.audioManager.pauseSong();

    }

    // Stopping the music
    public void musicstop(View v)
    {
        MainActivity.audioManager.stopSong();
    }


}