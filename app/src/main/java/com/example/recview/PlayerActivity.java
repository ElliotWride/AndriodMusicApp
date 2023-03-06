package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {

    // Instantiating the MediaPlayer class
    MediaPlayer music;
    String currentSong;

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
        music = MediaPlayer.create(this, Uri.parse(bundle.getString("path")));

    }

    // Playing the music
    public void musicplay(View v)
    {
        music.start();
        Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
    }

    // Pausing the music
    public void musicpause(View v)
    {
        music.pause();

    }

    // Stopping the music
    public void musicstop(View v)
    {
        music.stop();
        music= MediaPlayer.create(this, Uri.parse(currentSong));
    }
}