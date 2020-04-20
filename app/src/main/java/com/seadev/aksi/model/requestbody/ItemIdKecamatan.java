package com.seadev.aksi.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.aksi.model.dataapi.IdKecamatan;

import java.util.List;

public class ItemIdKecamatan {

    @SerializedName("RECORDS")
    private List<IdKecamatan> idKecamatanList;

    public ItemIdKecamatan(List<IdKecamatan> idKecamatanList) {
        this.idKecamatanList = idKecamatanList;
    }

    public List<IdKecamatan> getIdKecamatanList() {
        return idKecamatanList;
    }
}
