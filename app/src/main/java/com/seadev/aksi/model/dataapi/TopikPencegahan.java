package com.seadev.aksi.model.dataapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class TopikPencegahan implements Parcelable {
    public static final Parcelable.Creator<TopikPencegahan> CREATOR = new Parcelable.Creator<TopikPencegahan>() {
        @Override
        public TopikPencegahan createFromParcel(Parcel source) {
            return new TopikPencegahan(source);
        }

        @Override
        public TopikPencegahan[] newArray(int size) {
            return new TopikPencegahan[size];
        }
    };
    @SerializedName("imgTopik")
    private String imgTopik;
    @SerializedName("namaTopik")
    private String namaTopik;
    @SerializedName("langkah")
    private List<LangkahPencegahan> pencegahanList;

    public TopikPencegahan(String imgTopik, String namaTopik, List<LangkahPencegahan> pencegahanList) {
        this.imgTopik = imgTopik;
        this.namaTopik = namaTopik;
        this.pencegahanList = pencegahanList;
    }

    protected TopikPencegahan(Parcel in) {
        this.imgTopik = in.readString();
        this.namaTopik = in.readString();
        this.pencegahanList = new ArrayList<LangkahPencegahan>();
        in.readList(this.pencegahanList, LangkahPencegahan.class.getClassLoader());
    }

    public String getImgTopik() {
        return imgTopik;
    }

    public String getNamaTopik() {
        return namaTopik;
    }

    public List<LangkahPencegahan> getPencegahanList() {
        return pencegahanList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgTopik);
        dest.writeString(this.namaTopik);
        dest.writeList(this.pencegahanList);
    }
}
