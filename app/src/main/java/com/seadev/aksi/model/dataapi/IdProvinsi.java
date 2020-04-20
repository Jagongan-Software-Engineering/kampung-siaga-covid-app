package com.seadev.aksi.model.dataapi;

import com.google.gson.annotations.SerializedName;

public class IdProvinsi {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public IdProvinsi() {
    }

    public IdProvinsi(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
