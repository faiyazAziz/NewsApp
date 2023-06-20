package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {
    RecyclerView recyclerViewFavourite;
    ArrayList<NewsModel> newsArr;
    NewsRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        recyclerViewFavourite = findViewById(R.id.recycler_view_favourite);
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(this));
        FavouriteDatabaseHelper favouriteDatabaseHelper = FavouriteDatabaseHelper.getDB(this);

        ArrayList<Favourite> favArr = (ArrayList<Favourite>) favouriteDatabaseHelper.favouriteDao().getAllFavourite();
         newsArr = new ArrayList<>();
        int n = favArr.size();
        for(Favourite fav:favArr){
            newsArr.add(new NewsModel(fav.getId(),fav.getTitle(),fav.getImageUrl(),fav.getNewsUrl()));
        }

        adapter = new NewsRecyclerAdapter(this,newsArr);
        recyclerViewFavourite.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.delete_all,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int opt_id = item.getItemId();
        FavouriteDatabaseHelper favouriteDatabaseHelper = FavouriteDatabaseHelper.getDB(this);
        if(opt_id==R.id.opt_delete_all){
            favouriteDatabaseHelper.favouriteDao().deleteAll();
            newsArr.clear();
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}