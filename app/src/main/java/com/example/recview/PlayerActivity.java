package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {

    // Instantiating the MediaPlayer class
    MediaPlayer music;

    @Override
    protected void onCreate(
            Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Adding the music file to our
        // newly created object music
        music = MediaPlayer.create(
                this, R.raw.igors_theme);
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
        music= MediaPlayer.create(this, R.raw.igors_theme);
    }
}