package com.seadev.kampungsiagacovid.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.kampungsiagacovid.model.dataapi.IdProvinsi;

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
