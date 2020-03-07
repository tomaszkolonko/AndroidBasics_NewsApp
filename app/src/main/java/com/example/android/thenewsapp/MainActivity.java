package com.example.android.thenewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /** developer API_KEY issued to the user */
    private static final String API_KEY = "54100795-f441-4267-910b-139d4496d78d";

    /** Guardian query base */
    private static final String GENERAL_QUERY = "https://content.guardianapis.com/search";

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

        // TODO: create the inner AsyncTask class
        // TODO: call the background HTTP thread
        // TODO: get results and update the view
    }


}
