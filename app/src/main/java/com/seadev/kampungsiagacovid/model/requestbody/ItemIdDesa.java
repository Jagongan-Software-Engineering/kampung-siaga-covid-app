package com.seadev.kampungsiagacovid.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.kampungsiagacovid.model.dataapi.IdDesa;

import java.util.List;

public class ItemIdDesa {

    @SerializedName("RECORDS")
    private List<IdDesa> idDesaList;

    public ItemIdDesa(List<IdDesa> idDesaList) {
        this.idDesaList = idDesaList;
    }

    public List<IdDesa> getIdDesaList() {
        return idDesaList;
    }
}
