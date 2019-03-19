package com.news.archangel.newsreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article> articlesList;

    public NewsAdapter(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView;
        protected TextView titleTextView;
        protected TextView descriptionTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            titleTextView=itemView.findViewById(R.id.title_textView);
            descriptionTextView=itemView.findViewById(R.id.description_textView);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate the cardView Layout
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card,parent,false);
        return new  NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article= articlesList.get(position);
        holder.imageView.setBackgroundColor(100);
        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return articlesList.size();
    }
}
