package com.example.recyclerview;

public class NewsModel {
    private int id = 0;
    private String mTitle;
    private String mImgUrl;
    private String mNewsUrl;

    public NewsModel(String title,String imgUrl,String newsUrl){
        mTitle = title;
        mImgUrl = imgUrl;
        mNewsUrl = newsUrl;
    }

    NewsModel(int id,String title,String imgUrl,String newsUrl){
        mTitle = title;
        mImgUrl = imgUrl;
        mNewsUrl = newsUrl;
        this.id = id;

    }

    public int getId(){return id;}
    public String getTitle(){return mTitle;}
    public String getmImgUrl(){return mImgUrl;}
    public String getmNewsUrl() {
        return mNewsUrl;
    }
}

