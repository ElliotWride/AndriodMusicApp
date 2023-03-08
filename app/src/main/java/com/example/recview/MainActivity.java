package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements Serializable {
    public static AudioManager audioManager;
    public static AudioFetcher audioFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        audioManager = new AudioManager();
        audioFetcher = new AudioFetcher(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseSongActivity.class);
                startActivity(intent);
            }
        });
        final Button button1 = (Button) findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PlayerActivity.class);
                if(MainActivity.audioManager.getCurrentSong() == null){
                    AudioModel temp = audioFetcher.getAudio().get(ThreadLocalRandom.current().nextInt(0, audioFetcher.getAudio().size() + 1));
                    intent.putExtra("path",temp.path);
                }else{
                    intent.putExtra("path",audioManager.getCurrentSongPath());
                }

                startActivity(intent);
            }
        });

    }
}