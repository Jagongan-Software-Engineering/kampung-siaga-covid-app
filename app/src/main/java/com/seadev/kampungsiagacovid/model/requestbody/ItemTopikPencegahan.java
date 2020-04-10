package com.seadev.kampungsiagacovid.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.kampungsiagacovid.model.dataapi.TopikPencegahan;

import java.util.List;

public class ItemTopikPencegahan {
    @SerializedName("topikResponse")
    List<TopikPencegahan> pencegahanList;

    public ItemTopikPencegahan(List<TopikPencegahan> pencegahanList) {
        this.pencegahanList = pencegahanList;
    }

    public List<TopikPencegahan> getPencegahanList() {
        return pencegahanList;
    }
}
