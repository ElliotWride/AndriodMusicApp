package com.example.recview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.io.Serializable;

public class AudioManager implements Serializable {
    private MediaPlayer currentSong = null;
    private String currentSongPath;
    private Context context;
    private String test = "";

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCurrentSong(MediaPlayer currentSong) {
        this.currentSong = currentSong;
    }

    public void setCurrentSongPath(String currentSongPath) {
        this.currentSongPath = currentSongPath;
    }

    public void setTest(String testStr){
        test = testStr;
    }

    public String getTest() {
        return test;
    }

    public void playSong(String newSong){
        MediaPlayer music = MediaPlayer.create(context, Uri.parse(newSong));
        currentSongPath = newSong;
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

    public MediaPlayer getCurrentSong() {
        return currentSong;
    }

    public String getCurrentSongPath() {
        return currentSongPath;
    }

    public Bitmap getEmbeddedPicture(){
        android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(currentSongPath);

        byte [] data = mmr.getEmbeddedPicture();
        //coverart is an Imageview object

        // convert the byte array to a bitmap
        if(data != null)
        {
            return BitmapFactory.decodeByteArray(data, 0, data.length);

        }
        else
            return null;
    }
}
