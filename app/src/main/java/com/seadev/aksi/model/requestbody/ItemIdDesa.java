package com.seadev.aksi.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.aksi.model.dataapi.IdDesa;

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
