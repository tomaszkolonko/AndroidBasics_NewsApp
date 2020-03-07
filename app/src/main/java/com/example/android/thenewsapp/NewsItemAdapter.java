package com.example.android.thenewsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

    // TODO add some variables that you later will insert into the views

    public NewsItemAdapter(Context context, ArrayList<NewsItem> newsItems) {
        super(context, 0, newsItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        NewsItem currentNewsItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_newsitem_element,
                    parent, false);
        }

        setOnclickListenerOnView(convertView);

        // TODO look up all views and populate them

        TextView newsItemDate = (TextView) convertView.findViewById(R.id.newsItemDate);
        newsItemDate.setText(currentNewsItem.getWebPubDate());

        TextView newsItemTitle = (TextView) convertView.findViewById(R.id.newsItemTitle);
        newsItemTitle.setText(currentNewsItem.getWebTitle());



        return convertView;
    }

    private void setOnclickListenerOnView(View convertView) {
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: add correct url
                Uri webpage = Uri.parse("www.google.ch");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });
    }
}