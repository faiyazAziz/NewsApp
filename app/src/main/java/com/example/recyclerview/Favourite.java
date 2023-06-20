package com.example.recyclerview;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite")
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="imageUrl")
    private String imageUrl;

    @ColumnInfo(name="newsUrl")
    private String newsUrl;

    Favourite(int id,String title,String imageUrl,String newsUrl){
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.newsUrl = newsUrl;
    }
    @Ignore
    Favourite(String title,String imageUrl,String newsUrl){
        this.title = title;
        this.imageUrl = imageUrl;
        this.newsUrl = newsUrl;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }


}
