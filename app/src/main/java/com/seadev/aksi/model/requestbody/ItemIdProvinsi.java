package com.seadev.aksi.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.aksi.model.dataapi.IdProvinsi;

import java.util.List;

public class ItemIdProvinsi {
    @SerializedName("RECORDS")
    private List<IdProvinsi> provinsiList;

    public ItemIdProvinsi(List<IdProvinsi> provinsiList) {
        this.provinsiList = provinsiList;
    }

    public List<IdProvinsi> getProvinsiList() {
        return provinsiList;
    }
}
