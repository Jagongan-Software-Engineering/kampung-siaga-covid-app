package com.seadev.kampungsiagacovid.model.dataapi;

import com.google.gson.annotations.SerializedName;

public class Hotline {
    @SerializedName("namRS")
    private String namaRs;
    @SerializedName("hotline")
    private String hotline;
    @SerializedName("idProvinsi")
    private String idProvinsi;

    public Hotline() {
    }

    public Hotline(String namaRs, String hotline, String idProvinsi) {
        this.namaRs = namaRs;
        this.hotline = hotline;
        this.idProvinsi = idProvinsi;
    }

    public String getNamaRs() {
        return namaRs;
    }

    public String getHotline() {
        return hotline;
    }

    public String getIdProvinsi() {
        return idProvinsi;
    }
}
