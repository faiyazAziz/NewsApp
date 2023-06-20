package com.example.recyclerview;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Favourite.class,exportSchema = false,version = 1)
public abstract class FavouriteDatabaseHelper extends RoomDatabase {

    private static final String DB_NAME = "favouriteDB";
    private static FavouriteDatabaseHelper instance;

    public static synchronized FavouriteDatabaseHelper getDB(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,FavouriteDatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract FavouriteDao favouriteDao();
}
