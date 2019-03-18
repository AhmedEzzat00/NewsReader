package com.news.archangel.newsreader;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsUtilities {
    private static final String LOG_TAG = NewsUtilities.class.getName();

    /**
     * This Class is meant to hold static variables and methods.
     * so there is no need to create an instance of the NewsUtilities
     */
    private NewsUtilities() {
    }

    public static String fetchNewsDataResponse(String stringURL) {
        URL url = createURL(stringURL);
        String jsonResponse = "";

        try {
            jsonResponse=makeHTTPRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Error while creating URL ",e);
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) return jsonResponse;

        InputStream inputStream = null;
        HttpURLConnection urlConnection=null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse =readFromInputStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error in Making a Connection ");
        }finally {
            if (inputStream!=null)
                inputStream.close();
            if (urlConnection!=null)
                urlConnection.disconnect();
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream){
        StringBuilder stringBuilder=new StringBuilder();
        if (inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            try {
                String line=bufferedReader.readLine();
                while (line!=null)
                {
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG,"Error while reading from InputStream ",e);
            }
        }
        return stringBuilder.toString();
    }
}
