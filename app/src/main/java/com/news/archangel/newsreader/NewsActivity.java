package com.news.archangel.newsreader;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity {


    private static final String LOG_TAG = NewsActivity.class.getName();
    private static final String NEWS_API_REQUEST_URL =
            "https://newsapi.org/v2/everything?q=sources";
    //This Key is obtained from the site by register on it
    private static final String API_KEY =
            "c3810091c7844f18bf2ee02a762d90a0";
    private  NewsAdapter adapter;
    private  RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the RecyclerView
        recyclerList=findViewById(R.id.recycler_view);
        recyclerList.setHasFixedSize(true);
        LinearLayoutManager llm =new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(llm);

        adapter=new NewsAdapter(new ArrayList<Article>());
        recyclerList.setAdapter(adapter);
        //Building the URL of the API
        Uri baseUri = Uri.parse(NEWS_API_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();


        Intent intent = getIntent();
        String query = "";
        if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
            query = intent.getStringExtra(SearchManager.QUERY);
            if (!TextUtils.isEmpty(query)) {
                uriBuilder.clearQuery();
                uriBuilder.appendQueryParameter("q", query);
            }
        }

        uriBuilder.appendQueryParameter("apiKey", API_KEY);
        Log.i(LOG_TAG, query);
        new NewsAysc().execute(uriBuilder.toString());
    }

    private class NewsAysc extends AsyncTask<String, Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(String... urls) {
            return NewsUtilities.fetchNewsDataResponse(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            adapter= new NewsAdapter(articles);
            recyclerList.setAdapter(adapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}



