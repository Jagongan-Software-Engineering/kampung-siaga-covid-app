package com.seadev.kampungsiagacovid.rest;

import com.seadev.kampungsiagacovid.model.requestbody.ItemHotline;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdDesa;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdKecamatan;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdKotaKab;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdProvinsi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceLokasi {
    @GET("provinces.json?alt=media&token=4c5d0741-8042-494b-8a6e-e14bc3260330")
    Call<ItemIdProvinsi> getDataProvinsi();

    @GET("regencies.json?alt=media&token=53623c4e-5cd3-4fb0-ae98-64c2095d42d2")
    Call<ItemIdKotaKab> getDataKotaKab();

    @GET("districts.json?alt=media&token=84dd113f-01e6-4871-adc8-6f1805d931b4")
    Call<ItemIdKecamatan> getDataKecamatan();

    @GET("villages.json?alt=media&token=9ebc1e4a-3804-4031-bc50-c7dd7316a95c")
    Call<ItemIdDesa> getDataDesa();

    @GET("hotline.json?alt=media&token=71649a27-7f75-4266-8575-1075e2bb9cdf")
    Call<ItemHotline> getDataHotline();
}
