package com.example.recview;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.Serializable;

public class AudioManager implements Serializable {
    private MediaPlayer currentSong = null;
    private Context context;
    private String test = "";

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCurrentSong(MediaPlayer currentSong) {
        this.currentSong = currentSong;
    }

    public void setTest(String testStr){
        test = testStr;
    }

    public String getTest() {
        return test;
    }

    public void playSong(String newSong){
        MediaPlayer music = MediaPlayer.create(context, Uri.parse(newSong));
        try {
            currentSong.pause();
        }catch (Exception e){
            Toast.makeText(context, "caught", Toast.LENGTH_SHORT).show();
        }
        setCurrentSong(music);
        currentSong.start();
    }

    public void pauseSong(){
        try {
            currentSong.pause();
        }catch (Exception e){

        }
    }

    public void stopSong(){
        try{

        currentSong.stop();}
        catch (Exception e){

        }
    }

    public Context getContext() {
        return context;
    }
}
