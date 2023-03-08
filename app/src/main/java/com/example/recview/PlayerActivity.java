package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.testing.json.MockJsonFactory;
import com.google.api.services.customsearch.v1.CustomSearchAPI;
import com.google.api.services.customsearch.v1.model.Result;
import com.google.api.services.customsearch.v1.model.Search;


import java.io.Serializable;
import java.util.List;


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
        MainActivity.audioManager.setCurrentSongPath(currentSong);
        //Toast.makeText(this, currentSong, Toast.LENGTH_SHORT).show();
        MainActivity.audioManager.setContext(this);
        Bitmap pic = MainActivity.audioManager.getEmbeddedPicture();
        ImageView iv = (ImageView)findViewById(R.id.imageView);
        if (pic!=null){
            iv.setImageBitmap(pic);
        }else {
            Result result = search("album");
            result.get();

        }
    }

    private com.google.api.services.customsearch.v1.model.Result search(String keyword){
        CustomSearchAPI customsearch= null;


        try {
            customsearch = new CustomSearchAPI(new NetHttpTransport(), new MockJsonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) {
                    try {
                        // set connect and read timeouts
                        int HTTP_REQUEST_TIMEOUT = 1000;
                        httpRequest.setConnectTimeout(HTTP_REQUEST_TIMEOUT);
                        httpRequest.setReadTimeout(HTTP_REQUEST_TIMEOUT);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<com.google.api.services.customsearch.v1.model.Result> resultList=null;
        com.google.api.services.customsearch.v1.model.Result result = null;
        try {
            CustomSearchAPI.Cse.List list = customsearch.cse().list(keyword);
            list.setKey("AIzaSyBp6YlE_Z7O95oy20cL65VPFjOB-6zPmdk");
            list.setCx("f6276d7a5ac5c43c5");
            Search results=list.execute();
            result=results.getItems().get(0);
        }
        catch (  Exception e) {
            e.printStackTrace();
        }
        return result;
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