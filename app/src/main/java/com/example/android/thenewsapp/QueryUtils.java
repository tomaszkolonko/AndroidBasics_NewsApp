package com.example.android.thenewsapp;

import android.util.Log;

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

    public static List<NewsItem> extractNewsItemFeatures(String jsonStringResponse) {
        // TODO: get the needed values for the view and return the list
        return new ArrayList<NewsItem>();
    }
}
