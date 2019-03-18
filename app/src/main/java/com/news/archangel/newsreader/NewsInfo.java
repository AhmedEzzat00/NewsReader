package com.news.archangel.newsreader;

public class NewsInfo {
    private String title;
    private String description;
    private String urlToNews;
    private String urlToImage;
    private String time;

    public NewsInfo(String title, String description, String urlToNews, String urlToImage, String time) {
        this.title = title;
        this.description = description;
        this.urlToNews = urlToNews;
        this.urlToImage = urlToImage;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToNews() {
        return urlToNews;
    }

    public void setUrlToNews(String urlToNews) {
        this.urlToNews = urlToNews;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
