package com.seadev.aksi.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.aksi.model.dataapi.IdKotaKab;

import java.util.List;

public class ItemIdKotaKab {

    @SerializedName("RECORDS")
    private List<IdKotaKab> idKotaKabList;

    public ItemIdKotaKab(List<IdKotaKab> idKotaKabList) {
        this.idKotaKabList = idKotaKabList;
    }

    public List<IdKotaKab> getIdKotaKabList() {
        return idKotaKabList;
    }
}
