package com.seadev.aksi.model.dataapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LangkahPencegahan implements Parcelable {

    public static final Parcelable.Creator<LangkahPencegahan> CREATOR = new Parcelable.Creator<LangkahPencegahan>() {
        @Override
        public LangkahPencegahan createFromParcel(Parcel source) {
            return new LangkahPencegahan(source);
        }

        @Override
        public LangkahPencegahan[] newArray(int size) {
            return new LangkahPencegahan[size];
        }
    };
    @SerializedName("imgLangkah")
    private String img;
    @SerializedName("judulLangkah")
    private String judul;
    @SerializedName("descLangkah")
    private String desc;

    public LangkahPencegahan(String img, String judul, String desc) {
        this.img = img;
        this.judul = judul;
        this.desc = desc;
    }

    protected LangkahPencegahan(Parcel in) {
        this.img = in.readString();
        this.judul = in.readString();
        this.desc = in.readString();
    }

    public String getImg() {
        return img;
    }

    public String getJudul() {
        return judul;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.judul);
        dest.writeString(this.desc);
    }
}
