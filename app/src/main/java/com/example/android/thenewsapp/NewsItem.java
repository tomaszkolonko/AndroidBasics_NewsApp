package com.example.android.thenewsapp;

public class NewsItem {

    /** Type of the newsItem */
    private String mType;

    /** Section name of the newsItem */
    private String mSectionName;

    /** Date of the web publication */
    private String mWebPubDate;

    /** Title of the newsItem */
    private String mWebTitle;

    /** web url of the full newsItem */
    private String mWebUrl;

    public NewsItem() {}

    public NewsItem(String type, String sectionName, String pubDate, String webTitle, String webUrl) {
        this.mType = type;
        this.mSectionName = sectionName;
        this.mWebPubDate = pubDate;
        this.mWebTitle = webTitle;
        this.mWebUrl = webUrl;
    }

    public String getType() { return mType; }

    public String getSectionName() { return mSectionName; }

    public String getWebPubDate() { return mWebPubDate; }

    public String getWebTitle() { return mWebTitle; }

    public String getWebUrl() { return mWebUrl; }

}
