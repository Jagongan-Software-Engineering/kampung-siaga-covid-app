package com.seadev.aksi.model.di

import android.content.Context
import com.seadev.aksi.model.data.local.database.AddressDao
import com.seadev.aksi.model.data.local.database.MainRoomDatabase
import com.seadev.aksi.model.data.local.datasource.AddressLocalDataSourceImpl
import com.seadev.aksi.model.domain.abstraction.datasource.AddressLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideMainDataBase(
        @ApplicationContext context: Context
    ): MainRoomDatabase = MainRoomDatabase.getDatabase(context)

    @Provides
    fun provideAddressDao(
        mainRoomDatabase: MainRoomDatabase
    ) : AddressDao = mainRoomDatabase.addressDao()

    @Provides
    fun provinceAddressLocalDataSource(
        addressDao: AddressDao
    ): AddressLocalDataSource = AddressLocalDataSourceImpl(addressDao)
}