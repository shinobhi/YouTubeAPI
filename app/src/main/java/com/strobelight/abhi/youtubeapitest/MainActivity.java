package com.strobelight.abhi.youtubeapitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.util.Joiner;

//these are the ones giving me grief
//import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.GeoPoint;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class MainActivity extends YouTubeBaseActivity implements AsyncResponse{

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    private static String url = "https://www.googleapis.com/youtube/v3/search";
    private static String videoURL = "dQw4w9WgXcQ";
    private static String queryText = "Rick Roll";

    private Search search = new Search();

    private static final String apiKey = "AIzaSyCmL8ycwQoL1UDUz9EWpWHTq3hy3e7r2ck";

    public void processFinish(String output){
        Log.i("test", output);
    }

    public void setVideoURL (String video) {
        videoURL = video;
    }

    public String getVideoURL () {
        return videoURL;
    }

    public void setQueryText (String query) {
        queryText = query;
    }

    public String getQueryText () {
        return queryText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        search.delegate = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final EditText editText = (EditText) findViewById(R.id.searchField);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoPlayer);
        onInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b){
                youTubePlayer.loadVideo(getVideoURL());
            }

            @Override
            public void onInitializationFailure (YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setQueryText(editText.getText().toString());

                search.execute(getQueryText());
                Snackbar.make(view, "Search Text Entered: \"" + getQueryText() + "\", Video URL: \"" + getVideoURL() + "\"", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                chooseVideo();
                //search.execute(getQueryText());

                chooseVideo();
                youTubePlayerView.initialize(apiKey, onInitializedListener);
            }
        });
    }

    private void chooseVideo () {
        String query = getQueryText();
        search.execute(query);

        //return search.getFirstURL(query);
    }

    /*public void searchYT (View view) {
        Intent intent = new Intent(this, /*display class);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
