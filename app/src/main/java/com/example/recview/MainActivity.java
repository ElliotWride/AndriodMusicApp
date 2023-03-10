package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements Serializable {
    public static AudioManager audioManager; //audioManager used app wide to control audio playback
    public static AudioFetcher audioFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = new AudioManager();
        audioFetcher = new AudioFetcher(this);

        final Button button = (Button) findViewById(R.id.button2);
        //Go to choose song activity button - (songs)
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChooseSongActivity.class);
            startActivity(intent);
        });

        final Button button1 = (Button) findViewById(R.id.button3); //Go to player activity
        button1.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,PlayerActivity.class);
            if(MainActivity.audioManager.getCurrentSong() == null){ //when there is no song playing choose random song
                AudioModel temp = audioFetcher.getAudio().get(ThreadLocalRandom.current().nextInt(0, audioFetcher.getAudio().size() + 1));
                intent.putExtra("path",temp.path);
                intent.putExtra("album",temp.album);
                intent.putExtra("name",temp.name);
            }else{
                intent.putExtra("path",audioManager.getCurrentSongPath()); //song already playing pass that song data in
                intent.putExtra("album",audioManager.getAlbum());
                intent.putExtra("name",audioManager.getName());
            }
            startActivity(intent);
        });

        Button button2 = findViewById(R.id.buttonGit); //go to github page - for people wanting to implement their own features
        button2.setOnClickListener(v -> {
            //implicit intent - Action go to github page
            String url = "https://github.com/ElliotWride/AndriodMusicApp";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }
}