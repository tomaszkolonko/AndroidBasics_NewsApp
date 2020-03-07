package com.example.android.thenewsapp;

import android.content.Context;
import android.util.Log;
import android.content.AsyncTaskLoader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsItemLoader extends AsyncTaskLoader<List<NewsItem>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsItemLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public NewsItemLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response and extract a list of NewsItems
        URL url = QueryUtils.getURL(mUrl);
        String stringResponse = "";
        try {
            stringResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException exception) {
            Log.e(LOG_TAG, "HTTP Request failed: " + exception);
        }

        return QueryUtils.extractNewsItemFeatures(stringResponse);
    }
}
