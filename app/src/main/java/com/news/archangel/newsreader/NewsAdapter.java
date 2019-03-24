package com.news.archangel.newsreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private static final String LOG_TAG = NewsAdapter.class.getName();
    private List<Article> articlesList;

    NewsAdapter(List<Article> articlesList) {
        this.articlesList = articlesList;
    }


    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        NewsViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleTextView = itemView.findViewById(R.id.title_textView);
            descriptionTextView = itemView.findViewById(R.id.description_textView);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate the cardView Layout
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card, parent, false);

        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        final Article article = articlesList.get(position);

        Picasso.get().load(article.getUrlToImage()).into(holder.imageView);
        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri articleSite = Uri.parse(article.getUrlToNews());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, articleSite);
                v.getContext().startActivity(webIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return articlesList.size();
    }

}
