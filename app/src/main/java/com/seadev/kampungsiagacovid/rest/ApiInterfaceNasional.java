package com.seadev.kampungsiagacovid.rest;

import com.seadev.kampungsiagacovid.model.requestbody.ItemDataHarian;
import com.seadev.kampungsiagacovid.model.requestbody.ItemDataProvinsi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceNasional {
    @GET("Statistik_Perkembangan_COVID19_Indonesia/FeatureServer/0/query?where=1%3D1&outFields=Hari_ke,Tanggal,Jumlah_Kasus_Baru_per_Hari,Jumlah_Kasus_Kumulatif,Jumlah_pasien_dalam_perawatan,Persentase_Pasien_dalam_Perawatan,Jumlah_Pasien_Sembuh,Persentase_Pasien_Sembuh,Jumlah_Pasien_Meninggal,Persentase_Pasien_Meninggal,Jumlah_Kasus_Sembuh_per_Hari,Jumlah_Kasus_Meninggal_per_Hari,Jumlah_Kasus_Dirawat_per_Hari&outSR=4326&f=json")
    Call<ItemDataHarian> getDataHarian();

    @GET("COVID19_Indonesia_per_Provinsi/FeatureServer/0/query?where=1%3D1&outFields=Kode_Provi,Provinsi,Kasus_Posi,Kasus_Semb,Kasus_Meni&outSR=4326&f=json")
    Call<ItemDataProvinsi> getDataProvinsi();
}
