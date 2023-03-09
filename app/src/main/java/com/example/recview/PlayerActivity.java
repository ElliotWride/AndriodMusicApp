package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.json.MockJsonFactory;
import com.google.api.services.customsearch.v1.CustomSearchAPI;
import com.google.api.services.customsearch.v1.CustomSearchAPIRequestInitializer;
import com.google.api.services.customsearch.v1.model.Search;


import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;




public class PlayerActivity extends AppCompatActivity implements Serializable {


    // Instantiating the MediaPlayer class
    MediaPlayer music;
    String currentSong;
    String oldSong;
    String albumURL = null;
    //SharedPreferences pref = this.getSharedPreferences("com.example.recview", Context.MODE_PRIVATE);

    class AlbumCoverFetcher extends AsyncTask<String, Void, String> {
        private Context parent;
        private Drawable drawable = null;
        private String query;
        public AlbumCoverFetcher(String q){
            query = q;
        }

        protected String doInBackground(String... strings) {
            com.google.api.services.customsearch.v1.model.Result result = null;
            Search results = null;
            try {
                CustomSearchAPI cs = new CustomSearchAPI.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setApplicationName("recview")
                        .setGoogleClientRequestInitializer(new CustomSearchAPIRequestInitializer("AIzaSyBp6YlE_Z7O95oy20cL65VPFjOB-6zPmdk"))
                        .build();
                CustomSearchAPI.Cse.List list = cs.cse().list().setQ(query).setCx("f6276d7a5ac5c43c5");
                list.setSearchType("image");
                System.out.println(list);
                //Toast.makeText(this, list.toString(), Toast.LENGTH_SHORT).show();
                results = list.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String urlString = results.getItems().get(0).getImage().getThumbnailLink();
                InputStream is = (InputStream) new URL(urlString).getContent();
                this.drawable = Drawable.createFromStream(is, "src name");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "test";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (drawable != null){
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageDrawable(drawable);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Bundle bundle = getIntent().getExtras();
        //String song =  "res/raw/";
        //song += bundle.getString("name")+ ".mp3";
        //Toast.makeText(this, song, Toast.LENGTH_SHORT).show();
        // Adding the music file to our
        // newly created object music
        currentSong = bundle.getString("path");
        MainActivity.audioManager.setAlbum(bundle.getString("album"));
        MainActivity.audioManager.setName(bundle.getString("name"));
        MainActivity.audioManager.setCurrentSongPath(currentSong);
        //Toast.makeText(this, currentSong, Toast.LENGTH_SHORT).show();
        MainActivity.audioManager.setContext(this);
        Bitmap pic = MainActivity.audioManager.getEmbeddedPicture();
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        if (pic!=null){
            iv.setImageBitmap(pic);
        } else {
            AlbumCoverFetcher albumCoverFetcher = new AlbumCoverFetcher(bundle.getString("album"));
            albumCoverFetcher.execute();
        }
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(MainActivity.audioManager.getName());

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

    public void setAlbumURL(String albumURL) {
        this.albumURL = albumURL;
    }
}