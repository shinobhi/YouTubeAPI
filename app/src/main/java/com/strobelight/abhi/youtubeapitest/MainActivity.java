package com.strobelight.abhi.youtubeapitest;

import android.os.AsyncTask;
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


public class MainActivity extends YouTubeBaseActivity implements SearchTwo.AsyncResponse{

    private YouTubePlayer actualPlayer;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener;

    private static String url = "https://www.googleapis.com/youtube/v3/search";
    private static String videoURL = "dQw4w9WgXcQ";
    private static String queryText = "Rick Roll";

    private static final String apiKey = "AIzaSyCmL8ycwQoL1UDUz9EWpWHTq3hy3e7r2ck";

    public void processFinish(String output){
        Log.i("callback::processFinish", output);
//        setVideoURL(output);
        actualPlayer.loadVideo(output);
    }

    public static void setVideoURL (String video) {
        videoURL = video;
    }

    public String getVideoURL () {
        return videoURL;
    }

    public String getQueryText () {
        return queryText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final EditText editText = (EditText) findViewById(R.id.searchField);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoPlayer);
        playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {
                Log.i("YouTubeStateChange", "onLoading");
            }

            @Override
            public void onLoaded(String s) {

                Log.i("YouTubeStateChange", "onLoaded");
            }

            @Override
            public void onAdStarted() {
                Log.i("YouTubeStateChange", "onAdStarted");
            }

            @Override
            public void onVideoStarted() {
                Log.i("YouTubeStateChange", "onVideoStarted");
            }

            @Override
            public void onVideoEnded() {
                Log.i("YouTubeStateChange", "onVideoEnded");
                // Play next video??
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {
                Log.e("YouTubeStateChange", "onError");
            }
        };
        onInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored){
                if (actualPlayer == null) {
                    actualPlayer = youTubePlayer;
                }
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                youTubePlayer.loadVideo(getVideoURL());
            }

            @Override
            public void onInitializationFailure (YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize(apiKey, onInitializedListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newSearch(editText.getText().toString())) {
                    Log.i("onClick", "Query input was zero");
                }
                Snackbar.make(view, "Search Text Entered: \"" + getQueryText() + "\", Video URL: \"" + getVideoURL() + "\"", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    protected boolean newSearch(String query) {
        if (query.length() == 0) {
            return false;
        }
        new SearchTwo(this, query);
        return true;
    }

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
