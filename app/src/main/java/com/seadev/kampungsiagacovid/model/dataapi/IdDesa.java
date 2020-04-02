package com.seadev.kampungsiagacovid.model.dataapi;

import com.google.gson.annotations.SerializedName;

public class IdDesa {
    @SerializedName("id")
    private String id;
    @SerializedName("district_id")
    private String idKecamatan;
    @SerializedName("name")
    private String name;

    public IdDesa() {
    }

    public IdDesa(String id, String idKecamatan, String name) {
        this.id = id;
        this.idKecamatan = idKecamatan;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getIdKecamatan() {
        return idKecamatan;
    }

    public String getName() {
        return name;
    }
}
