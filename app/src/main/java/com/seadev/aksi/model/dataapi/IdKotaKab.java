package com.seadev.aksi.model.dataapi;

import com.google.gson.annotations.SerializedName;

public class IdKotaKab {
    @SerializedName("id")
    private String id;
    @SerializedName("province_id")
    private String idProvinsi;
    @SerializedName("name")
    private String name;

    public IdKotaKab() {
    }

    public IdKotaKab(String id, String idProvinsi, String name) {
        this.id = id;
        this.idProvinsi = idProvinsi;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getIdProvinsi() {
        return idProvinsi;
    }

    public String getName() {
        return name;
    }
}
