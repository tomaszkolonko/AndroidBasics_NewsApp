package com.example.android.thenewsapp;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving newsItems from the Guardian API.
 */
public class QueryUtils {

    /** LOG_TAG */
    private static final String LOG_TAG = "QueryUtils";

    /** Creating private constructor to override the public one.
     * This class is a utility class and should never be instantiated
     */
    private QueryUtils() {

    }

    /**
     * Excepts a string and turns it into an URL
     *
     * @param stringUrl
     * @return
     */
    public static URL getURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error occured creating URL" + exception);
        }
        return url;
    }

    /**
     * This method makes the HTTP request and checks for the responsecode
     * and other possible problems and handles them gracefully
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonStringResponse = "";

        // if the url is null or empty, then return early
        if(url == null || url.toString().isEmpty()) {
            return jsonStringResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonStringResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "urlResponseCode was: " + urlConnection.getResponseCode());
            }
        } catch (IOException exception) {
            Log.e(LOG_TAG, "IOException in makeHTTPRequest occured", exception);
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies that an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonStringResponse;
    }

    /**
     * Takes in an inputStream and parsed it with a bufferedReader into a StringBuilder
     * and return the String representation of it.
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return builder.toString();
    }

    /**
     * This method parses and extracts all the needed features from the json String
     * retrieved from the Guardian API and returns a list of NewsItem objects.
     *
     * @param jsonStringResponse
     * @return
     */
    public static List<NewsItem> extractNewsItemFeatures(String jsonStringResponse) {
        // If the JSON string is empty or null, then return early
        if(TextUtils.isEmpty(jsonStringResponse)) {
            return null;
        }

        // Create an empty List that we can start adding newsItems to it
        List<NewsItem> newsItemList = new ArrayList<NewsItem>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try{
            // Convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject rootObject = new JSONObject(jsonStringResponse);

            JSONArray resultsArray = rootObject.getJSONObject("response").getJSONArray("results");

            for(int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentNewsItem = resultsArray.getJSONObject(i);
                String type = currentNewsItem.getString("type");
                String sectionName = currentNewsItem.getString("sectionName");
                String publicationDate = currentNewsItem.getString("webPublicationDate");
                String webTitle = currentNewsItem.getString("webTitle");
                String webURL = currentNewsItem.getString("webUrl");

                newsItemList.add(new NewsItem(type, sectionName, publicationDate, webTitle, webURL));
            }
        } catch (JSONException exception) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the newsItems JSON results", exception);
        }
        return newsItemList;
    }
}
