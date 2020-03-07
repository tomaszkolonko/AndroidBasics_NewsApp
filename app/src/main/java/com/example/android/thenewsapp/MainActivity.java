package com.example.android.thenewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** developer API_KEY issued to the user */
    private static final String API_KEY = "54100795-f441-4267-910b-139d4496d78d";

    /** Guardian query base */
    private static final String GENERAL_QUERY = "https://content.guardianapis.com/search?q=coronavirus";

    /** LOG_TAG */
    private static final String LOG_TAG = "MainActivity";

    private NewsItemAdapter mAdapter;

    // TODO add adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the ListView in the layout
        ListView newsItemListView = (ListView) findViewById(R.id.list);

        // Create a new ArrayAdapter for the newsItems
        mAdapter = new NewsItemAdapter(this, new ArrayList<NewsItem>());

        newsItemListView.setAdapter(mAdapter);

        // Initialize the AsyncTask Class and run it in order to fetch the data
        NewsItemAsyncTask asyncTask = new NewsItemAsyncTask();
        asyncTask.execute(GENERAL_QUERY + "&api-key=" + API_KEY);
    }

    private class NewsItemAsyncTask extends AsyncTask<String, Void, List<NewsItem>> {
        @Override
        protected List<NewsItem> doInBackground(String... strings) {
            if(strings.length < 1 || strings[0] == null) {
                return null;
            }

            // Create a valid URL from the strings array
            URL url = QueryUtils.getURL(strings[0]);
            String stringResponse = "";
            try {
                // Make the HTTP Request with the help of the QueryUtils class
                stringResponse = QueryUtils.makeHttpRequest(url);
            } catch (IOException exception) {
                Log.e(LOG_TAG, "HTTP Request failed: " + exception);
            }

            // Extract the needed features with the help of the QueryUtils class
            List<NewsItem> newsItems = QueryUtils.extractNewsItemFeatures(stringResponse);
            return newsItems;
        }

        /**
         * This method adds all the new NewsItems to the adapter, so that the adapter
         * is able to display them on the screen. The list of NewsItems is fetched from
         * the background thread within doInBackground()
         *
         * @param newsItems
         */
        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            // Clear the adapter of previous newsItem data
            mAdapter.clear();

            // If there is a valid list of newsItems, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if(newsItems != null && !newsItems.isEmpty()) {
                mAdapter.addAll(newsItems);
            }
        }
    }


}
