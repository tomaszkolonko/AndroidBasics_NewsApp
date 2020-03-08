package com.example.android.thenewsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

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

        setOnclickListenerOnView(convertView, currentNewsItem);

        TextView newsItemTitle = convertView.findViewById(R.id.newsItemTitle);
        newsItemTitle.setText(currentNewsItem.getWebTitle());

        TextView newsItemDate = convertView.findViewById(R.id.newsItemDate);
        String dateAndTimeString = createDateAndTimeString(currentNewsItem.getWebPubDate());
        newsItemDate.setText(dateAndTimeString);

        TextView newsItemDetails = convertView.findViewById(R.id.newsItemDetails);
        String detailsString = "Written by: " + currentNewsItem.getAuthor() + " in "
                + currentNewsItem.getSectionName();
        newsItemDetails.setText(detailsString);

        return convertView;
    }

    /**
     * Formats the retrieved time in a human readable format.
     *
     * @param dateString
     * @return
     */
    private String createDateAndTimeString(String dateString) {
        String[] dateParts = dateString.split("T");
        String dateAndTimeString = "Published on " + dateParts[0] + " at "
                + dateParts[1].substring(0, dateParts[1].length()-4);
        return dateAndTimeString;
    }

    private void setOnclickListenerOnView(View convertView, final NewsItem currentNewsItem) {
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(currentNewsItem.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });
    }
}
