package com.example.android.thenewsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    /** developer API_KEY issued to the user */
    private static final String API_KEY = "54100795-f441-4267-910b-139d4496d78d";

    /** Guardian query base */
    private static final String GENERAL_QUERY = "https://content.guardianapis.com/search?q=coronavirus";

    /** LOG_TAG */
    private static final String LOG_TAG = "MainActivity";

    /** NewsItemAdapter */
    private NewsItemAdapter mAdapter;

    /**
     * Constant value for the newsItem loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWSITEM_LOADER_ID = 1;

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsItemLoader(this, (GENERAL_QUERY + "&api-key=" + API_KEY));
    }
    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> newsItems) {
        // Clear the adapter of previous newsItem data
        mAdapter.clear();

        // If there is a valid list of newsItems, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsItems != null && !newsItems.isEmpty()) {
            mAdapter.addAll(newsItems);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the ListView in the layout
        ListView newsItemListView = (ListView) findViewById(R.id.list);

        // Create a new ArrayAdapter for the newsItems
        mAdapter = new NewsItemAdapter(this, new ArrayList<NewsItem>());

        newsItemListView.setAdapter(mAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(NEWSITEM_LOADER_ID, null, this);
    }
}
