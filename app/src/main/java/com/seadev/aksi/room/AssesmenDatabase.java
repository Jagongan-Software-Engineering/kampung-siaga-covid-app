package com.seadev.aksi.room;

import com.seadev.aksi.model.Asesmen;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Asesmen.class}, version = 1, exportSchema = false)
public abstract class AssesmenDatabase extends RoomDatabase {
    public abstract AsesmenDao asesmenDao();
}
