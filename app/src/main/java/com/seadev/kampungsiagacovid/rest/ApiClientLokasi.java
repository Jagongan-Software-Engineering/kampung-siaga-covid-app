package com.seadev.kampungsiagacovid.rest;

import com.seadev.kampungsiagacovid.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientLokasi {
    public static Retrofit retrofit;

    public static Retrofit getClientLokasi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL_LOKASI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
