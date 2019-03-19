package com.news.archangel.newsreader;

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

    public static List<Article> fetchNewsDataResponse(String stringURL) {
        URL url = createURL(stringURL);
        String jsonResponse = "";

        try {
            jsonResponse=makeHTTPRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractArticleFromJSON(jsonResponse);
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
            //Adding Timeout Conditions
            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Check if the Response is OK
            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
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
    
    private static List<Article> extractArticleFromJSON(String jsonString)
    {
        List<Article> articlesList=new ArrayList<>();

        //Extracting Data
        try {
            JSONObject root=new JSONObject(jsonString);
            JSONArray articles=root.getJSONArray("articles");
            for (int i=0;i<articles.length();i++)
            {
                JSONObject currentArticle=articles.getJSONObject(i);

                //Article data
                String title=currentArticle.optString("title");
                String description=currentArticle.optString("description");
                String siteULR=currentArticle.optString("url");
                String imageURL= currentArticle.optString("urlToImage");
                String time = currentArticle.optString("publishedAt");

                //Constructing article
                articlesList.add(new Article(title,description,siteULR,imageURL,time));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG,"JSON Handling ERROR");
        }
        return articlesList;
    }
}
