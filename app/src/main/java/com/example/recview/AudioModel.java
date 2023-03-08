package com.example.recview;

import java.io.Serializable;

public class AudioModel implements Serializable {
    String path;
    String name;
    String album;
    String artist;

    public AudioModel(String p, String n, String al, String ar){
        this.path=p;
        this.name=n;
        this.album = al;
        this.artist = ar;
    }

    public String getPath(){
        return path;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
