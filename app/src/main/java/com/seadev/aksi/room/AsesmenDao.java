package com.seadev.aksi.room;

import com.seadev.aksi.model.Asesmen;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AsesmenDao {
    @Query("SELECT * FROM Asesmen")
    List<Asesmen> getDataAsesmen();

    @Insert
    void InsertDataAsesmen(Asesmen asesmen);

    @Delete
    void DeleteDataAsesmen(Asesmen asesmen);
}
