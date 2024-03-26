package com.seadev.aksi.model.data.remote.api

import com.seadev.aksi.model.data.remote.response.AddressResponse
import com.seadev.aksi.model.data.remote.response.DistrictResponse
import com.seadev.aksi.model.data.remote.response.RegencyResponse
import com.seadev.aksi.model.data.remote.response.SubDistrictResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApi {

    @GET("regencies.json")
    suspend fun getCity(
        @Query("alt") alt: String = "media",
        @Query("token") token: String = "53623c4e-5cd3-4fb0-ae98-64c2095d42d2"
    ): AddressResponse<RegencyResponse>

    @GET("districts.json")
    suspend fun getDistrict(
        @Query("alt") alt: String = "media",
        @Query("token") token: String = "84dd113f-01e6-4871-adc8-6f1805d931b4"
    ): AddressResponse<DistrictResponse>

    @GET("villages.json")
    suspend fun getSubDistrict(
        @Query("alt") alt: String = "media",
        @Query("token") token: String = "eb474495-4f8b-4597-a64c-5d6856e90dc0"
    ) : AddressResponse<SubDistrictResponse>
}