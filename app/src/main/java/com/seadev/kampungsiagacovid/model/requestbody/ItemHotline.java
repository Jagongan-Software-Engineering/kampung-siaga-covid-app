package com.seadev.kampungsiagacovid.model.requestbody;

import com.google.gson.annotations.SerializedName;
import com.seadev.kampungsiagacovid.model.dataapi.Hotline;

import java.util.List;

public class ItemHotline {
    @SerializedName("hotlineResponse")
    private List<Hotline> hotlineList;

    public ItemHotline(List<Hotline> hotlineList) {
        this.hotlineList = hotlineList;
    }

    public List<Hotline> getHotlineList() {
        return hotlineList;
    }
}
