package com.example.recview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.Serializable;

public class AudioManager implements Serializable { //class to handle actions on playing songs, implemented statically.
    private MediaPlayer currentSong = null;
    private String currentSongPath;
    private Context context;
    private String name = "";
    private String album = "";

    public void playSong(String newSong){//plays audio
        MediaPlayer music = MediaPlayer.create(context, Uri.parse(newSong));
        currentSongPath = newSong;
        try {
            currentSong.pause(); //pauses currently playing song so audio doesn't overlap
        }catch (Exception e){
            e.printStackTrace();
        }
        setCurrentSong(music);
        currentSong.start(); //starts song
    }

    public void pauseSong(){//pauses audio
        try {
            currentSong.pause();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopSong(){//stops audio
        try{

        currentSong.stop();}
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap getEmbeddedPicture(){ //returns bitmap of the album cover art of current song
        android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();//used to read song metadata
        mmr.setDataSource(currentSongPath);

        byte[] data = mmr.getEmbeddedPicture();
        //coverart is an Imageview object

        //convert the byte array to a bitmap
        if (data != null) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } else{
            return null;
        }
    }


    //getters and setters
    public void setContext(Context context) {
        this.context = context;
    }

    public void setCurrentSong(MediaPlayer currentSong) {
        this.currentSong = currentSong;
    }

    public void setCurrentSongPath(String currentSongPath) {
        this.currentSongPath = currentSongPath;
    }

    public MediaPlayer getCurrentSong() {
        return currentSong;
    }

    public String getCurrentSongPath() {
        return currentSongPath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
