package com.example.recyclerview;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("select * from favourite")
    List<Favourite> getAllFavourite();

    @Query("select exists(select * from favourite where title=:title )")
    int isExist(String title);

    @Query("delete from favourite")
    void deleteAll();

    @Insert
    void addFavourite(Favourite favourite);

    @Delete
    void deleteFavourite(Favourite favourite);

    @Query("delete from favourite where title=:title")
    void deleteNews(String title);
}
