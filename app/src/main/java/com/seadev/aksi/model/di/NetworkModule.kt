package com.seadev.aksi.model.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.seadev.aksi.model.data.remote.api.AddressApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(
        @ApplicationContext appContext: Context
    ): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ChuckerInterceptor(appContext))
            .build()
        return client
    }

    @Provides
    fun provideAddressApi(
        httpClient: OkHttpClient
    ): AddressApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://firebasestorage.googleapis.com/v0/b/kampung-siaga-covid-b0c8c.appspot.com/o/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AddressApi::class.java)
    }
}