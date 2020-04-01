package com.seadev.kampungsiagacovid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDataProvinsi {
    @SerializedName("features")
    private List<DataProvinsi> provinsiList;

    public ItemDataProvinsi(List<DataProvinsi> provinsiList) {
        this.provinsiList = provinsiList;
    }

    public List<DataProvinsi> getProvinsiList() {
        return provinsiList;
    }
}
