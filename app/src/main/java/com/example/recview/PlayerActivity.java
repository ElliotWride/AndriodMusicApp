package com.example.recview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.v1.CustomSearchAPI;
import com.google.api.services.customsearch.v1.CustomSearchAPIRequestInitializer;
import com.google.api.services.customsearch.v1.model.Search;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

public class PlayerActivity extends AppCompatActivity implements Serializable { // Music player activity - controls pause/play/stop actions
    String currentSong;

    class AlbumCoverFetcher extends AsyncTask<String, Void, String> { // Async task to handle getting from the internet - cannot be done on amin thread
        private Drawable drawable = null;
        private final String query; // search term
        public AlbumCoverFetcher(String q){ // Const instantiates the album to search for
            query = q;
        }

        protected String doInBackground(String... strings) { // Runs in the background on different thread while main thread happens
            Search results = null;
            try {
                // Use google's custom search engine to return hyperlink for album cover
                // Build CustomSearchApi object with api key
                CustomSearchAPI cs = new CustomSearchAPI.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setApplicationName("recview")
                        .setGoogleClientRequestInitializer(new CustomSearchAPIRequestInitializer("AIzaSyBp6YlE_Z7O95oy20cL65VPFjOB-6zPmdk"))
                        .build();
                // Specify search terms and search type
                CustomSearchAPI.Cse.List list = cs.cse().list().setQ(query).setCx("f6276d7a5ac5c43c5");
                list.setSearchType("image");
                // Execute search and return json of results
                results = list.execute();
            } catch (Exception e) {
                // In case of search fail - no internet
                e.printStackTrace();
            }
            try {
                // Convert hyperlink to image to drawable image
                assert results != null;
                assert results != null;
                String urlString = results.getItems().get(0).getImage().getThumbnailLink();
                InputStream is = (InputStream) new URL(urlString).getContent();
                this.drawable = Drawable.createFromStream(is, "src name");
            } catch (Exception e) {
                // In case of search fail
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) { // When image has finished importing this runs
            super.onPostExecute(s);
            if (drawable != null){
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageDrawable(drawable); //sets the image in player to the album cover
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //set song in player to song selected
        Bundle bundle = getIntent().getExtras();
        currentSong = bundle.getString("path");
        MainActivity.audioManager.setAlbum(bundle.getString("album"));
        MainActivity.audioManager.setName(bundle.getString("name"));
        MainActivity.audioManager.setCurrentSongPath(currentSong);
        MainActivity.audioManager.setContext(this);

        // Set picture
        Bitmap pic = null;
        try { // If album cover is stored on device then set pic to it.
             pic = MainActivity.audioManager.getEmbeddedPicture();
        }catch (Exception e){ // In case album cant be found
            e.printStackTrace();
        }
        ImageView iv = (ImageView) findViewById(R.id.imageView); // Get ImageView from player
        if (pic!=null){  // If picture stored on device
            iv.setImageBitmap(pic);
        } else {
            // Get picture from the internet
            AlbumCoverFetcher albumCoverFetcher = new AlbumCoverFetcher(bundle.getString("album"));
            albumCoverFetcher.execute();
        }
        // set name of song to current song
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(MainActivity.audioManager.getName());
    }


    // Playing the music
    public void musicplay()
    {
        MainActivity.audioManager.playSong(currentSong);
    }

    // Pausing the music
    public void musicpause()
    {
        MainActivity.audioManager.pauseSong();
    }

    // Stopping the music
    public void musicstop()
    {
        MainActivity.audioManager.stopSong();
    }
}