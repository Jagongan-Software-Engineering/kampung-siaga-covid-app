package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.ProvinceResponse
import com.seadev.aksi.model.data.remote.response.toData

data class Province(
    val name: String,
    val id: String
) {
    companion object {
        val Init = ProvinceResponse().toData()
        val Indonesia = Province("INDONESIA", "0")
    }
}