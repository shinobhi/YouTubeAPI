package com.strobelight.abhi.youtubeapitest;

import android.os.AsyncTask;

/**
 * Created by Abhi on 1/19/2017.
 */

public class SearchTwo {
    private AsyncResponse callback;

    public SearchTwo(AsyncResponse resp) {
        this.callback = resp;
    }

    public class BackgroundSearch extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            return "firstURL";
        }

        @Override
        protected void onPostExecute(String s) {
            callback.processFinish(s);
        }
    }
}
