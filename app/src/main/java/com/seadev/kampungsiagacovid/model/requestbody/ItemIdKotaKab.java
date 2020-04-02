package com.seadev.kampungsiagacovid.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.kampungsiagacovid.model.dataapi.IdKotaKab;

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
