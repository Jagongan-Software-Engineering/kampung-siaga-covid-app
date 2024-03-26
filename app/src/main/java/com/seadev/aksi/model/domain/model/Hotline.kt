package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.HotlineResponse
import com.seadev.aksi.model.data.remote.response.toData

data class Hotline(
    val hospital: String,
    val phone: String,
    val provinceId: String,
) {
    companion object {
        val Init = HotlineResponse().toData()
        val Indonesia = Hotline(
            hospital = "Layanan Nasional Covid-19",
            provinceId = "0",
            phone = "119"
        )
    }
}