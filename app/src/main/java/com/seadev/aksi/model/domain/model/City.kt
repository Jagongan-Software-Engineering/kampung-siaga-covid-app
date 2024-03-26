package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.RegencyResponse
import com.seadev.aksi.model.data.remote.response.toData

data class City(
    val name: String,
    val id: String,
    val provinceId: String,
) {
    companion object {
        val Init = RegencyResponse().toData()
    }
}