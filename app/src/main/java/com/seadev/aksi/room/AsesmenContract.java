package com.seadev.aksi.room;

import android.app.Application;

import androidx.room.Room;

public class AsesmenContract extends Application {
    public static final String DATABASE_NAME = "db_asesmen";
    public static AssesmenDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                AssesmenDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }
}
