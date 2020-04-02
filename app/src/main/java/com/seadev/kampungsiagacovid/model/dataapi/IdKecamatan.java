package com.seadev.kampungsiagacovid.model.dataapi;

import com.google.gson.annotations.SerializedName;

public class IdKecamatan {
    @SerializedName("id")
    private String id;
    @SerializedName("regency_id")
    private String idKotaKab;
    @SerializedName("name")
    private String name;

    public IdKecamatan() {
    }

    public IdKecamatan(String id, String idKotaKab, String name) {
        this.id = id;
        this.idKotaKab = idKotaKab;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getIdKotaKab() {
        return idKotaKab;
    }

    public String getName() {
        return name;
    }
}
