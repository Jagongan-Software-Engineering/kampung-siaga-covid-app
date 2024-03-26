package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.DistrictResponse
import com.seadev.aksi.model.data.remote.response.toData

data class District(
    val name: String,
    val id: String,
    val cityId: String,
) {
    companion object {
        val Init = DistrictResponse().toData()
    }
}