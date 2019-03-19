package com.news.archangel.newsreader;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;


public class NewsActivity extends AppCompatActivity {


    private static final String LOG_TAG = NewsActivity.class.getName();
    private static final String NEWS_API_REQUEST_URL =
            "https://newsapi.org/v2/everything?q=bitcoin";
    //This Key is obtained from the site by register on it
    private static final String API_KEY =
            "c3810091c7844f18bf2ee02a762d90a0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new NewsAysc().execute(NEWS_API_REQUEST_URL);
    }

    private class NewsAysc extends AsyncTask<String, Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(String... urls) {

            //Building the URL of the API
            Uri baseUri = Uri.parse(urls[0]);
            Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendQueryParameter("apiKey", API_KEY);

            return NewsUtilities.fetchNewsDataResponse(uriBuilder.toString());
        }

        @Override
        protected void onPostExecute(List<Article> articles) {

            TextView mTextView = (TextView) findViewById(R.id.test);
            mTextView.setText(articles.toString());
        }
    }
}



