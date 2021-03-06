package com.seadev.aksi.rest;

import com.seadev.aksi.model.requestbody.ItemHotline;
import com.seadev.aksi.model.requestbody.ItemIdDesa;
import com.seadev.aksi.model.requestbody.ItemIdKecamatan;
import com.seadev.aksi.model.requestbody.ItemIdKotaKab;
import com.seadev.aksi.model.requestbody.ItemIdProvinsi;
import com.seadev.aksi.model.requestbody.ItemTopikPencegahan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceFirebase {
    @GET("provinces.json?alt=media&token=4c5d0741-8042-494b-8a6e-e14bc3260330")
    Call<ItemIdProvinsi> getDataProvinsi();

    @GET("regencies.json?alt=media&token=53623c4e-5cd3-4fb0-ae98-64c2095d42d2")
    Call<ItemIdKotaKab> getDataKotaKab();

    @GET("districts.json?alt=media&token=84dd113f-01e6-4871-adc8-6f1805d931b4")
    Call<ItemIdKecamatan> getDataKecamatan();

    @GET("villages.json?alt=media&token=eb474495-4f8b-4597-a64c-5d6856e90dc0")
    Call<ItemIdDesa> getDataDesa();

    @GET("hotline.json?alt=media&token=5b30aaeb-74a8-46be-b055-2f382792b89c")
    Call<ItemHotline> getDataHotline();

    @GET("topic.json?alt=media&token=01129e3d-2ec1-4a25-aa33-e3f14fe0836f")
    Call<ItemTopikPencegahan> getDataPencegahan();
}
