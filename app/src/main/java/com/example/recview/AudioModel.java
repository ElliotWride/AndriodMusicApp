package com.example.recview;

import java.io.Serializable;

public class AudioModel implements Serializable { //class used to store song data as an object like a struct
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

}
