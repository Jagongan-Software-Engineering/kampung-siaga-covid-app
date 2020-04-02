package com.seadev.kampungsiagacovid.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.kampungsiagacovid.model.dataapi.IdKecamatan;

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
