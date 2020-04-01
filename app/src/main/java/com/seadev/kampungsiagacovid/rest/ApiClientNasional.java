package com.seadev.kampungsiagacovid.rest;

import com.seadev.kampungsiagacovid.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientNasional {
    public static Retrofit retrofit;

    public static Retrofit getClientNasional() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL_NASIONAL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
