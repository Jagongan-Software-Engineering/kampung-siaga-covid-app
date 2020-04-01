package com.seadev.kampungsiagacovid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDataHarian {
    @SerializedName("features")
    private List<DataHarian> dataHarianList;

    public ItemDataHarian(List<DataHarian> dataHarianList) {
        this.dataHarianList = dataHarianList;
    }

    public List<DataHarian> getDataHarianList() {
        return dataHarianList;
    }
}
