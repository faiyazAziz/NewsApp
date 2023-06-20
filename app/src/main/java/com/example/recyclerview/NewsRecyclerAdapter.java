package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    Context mContext;
    ArrayList<NewsModel> mArr;

    NewsRecyclerAdapter(Context context, ArrayList<NewsModel> arr){
        mArr = arr;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewsModel currentNews = mArr.get(position);
        holder.newsTitle.setText(currentNews.getTitle());
        Picasso.get().load(currentNews.getmImgUrl()).into(holder.newsImage);

        holder.newsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nUrl = currentNews.getmNewsUrl();
                Intent intent = new Intent(mContext,WebViewActivity.class);
                intent.putExtra("url",nUrl);
                mContext.startActivity(intent);
            }
        });

        holder.shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check Out This News\n"+currentNews.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT,currentNews.getmNewsUrl());
                mContext.startActivity(Intent.createChooser(intent,"Share Via"));
            }
        });

        FavouriteDatabaseHelper favouriteDatabaseHelper = FavouriteDatabaseHelper.getDB(mContext);

        if(favouriteDatabaseHelper.favouriteDao().isExist(currentNews.getTitle())==0){
            holder.favButton.setBackgroundResource(R.drawable.fav_icon_border);
        }else{
            holder.favButton.setBackgroundResource(R.drawable.fav_icon);
        }



        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favouriteDatabaseHelper.favouriteDao().isExist(currentNews.getTitle())==0) {
                        favouriteDatabaseHelper.favouriteDao().addFavourite(
                                new Favourite(currentNews.getTitle(), currentNews.getmImgUrl(), currentNews.getmNewsUrl())
                        );
                        //holder.favButton.setVisibility(View.GONE);
                        holder.favButton.setBackgroundResource(R.drawable.fav_icon);
                }else{
                    favouriteDatabaseHelper.favouriteDao().deleteNews(currentNews.getTitle());
                    holder.favButton.setBackgroundResource(R.drawable.fav_icon_border);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle;
        ImageView newsImage;
        LinearLayout newsContainer;
        ImageView shareImg;
        ImageButton favButton;
        public ViewHolder(View itemView){
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsImage = itemView.findViewById(R.id.news_img);
            newsContainer = itemView.findViewById(R.id.news_container);
            shareImg = itemView.findViewById(R.id.share_button);
            favButton = itemView.findViewById(R.id.fav_btn);
        }
    }


}
