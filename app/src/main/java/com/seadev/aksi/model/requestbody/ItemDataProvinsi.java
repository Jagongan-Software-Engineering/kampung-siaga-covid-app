package com.seadev.aksi.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.aksi.model.dataapi.DataProvinsi;

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
