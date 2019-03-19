package com.news.archangel.newsreader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static android.content.ContentValues.TAG;

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
        new ImageLoadAsync(holder.imageView).execute(article.getUrlToImage());
        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());
    }


    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    class ImageLoadAsync extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public ImageLoadAsync(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // Log.e("X3","Image Loaded");
            imageView.setImageBitmap(bitmap);
        }
    }


    public static Bitmap loadBitmap(String url) throws IOException {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream());

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }

        return bitmap;
    }
}
