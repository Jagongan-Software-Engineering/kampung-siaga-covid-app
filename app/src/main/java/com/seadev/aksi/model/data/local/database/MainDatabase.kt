package com.seadev.aksi.model.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seadev.aksi.model.data.local.entity.CityEntity
import com.seadev.aksi.model.data.local.entity.DistrictEntity
import com.seadev.aksi.model.data.local.entity.ProvinceEntity
import com.seadev.aksi.model.data.local.entity.SubDistrictEntity

@Database(
    entities = [ProvinceEntity::class, CityEntity::class, DistrictEntity::class, SubDistrictEntity::class],
    version = 1
)
abstract class MainRoomDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao

    companion object {
        @Volatile
        private var INSTANCE: MainRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MainRoomDatabase {
            if (INSTANCE == null) synchronized(MainRoomDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MainRoomDatabase::class.java,
                    "main_database"
                ).build()
            }
            return INSTANCE as MainRoomDatabase
        }
    }
}