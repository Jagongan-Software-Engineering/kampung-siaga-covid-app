package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.SubDistrictResponse
import com.seadev.aksi.model.data.remote.response.toData

data class SubDistrict(
    val name: String,
    val id: String,
    val districtId: String,
) {
    companion object {
        val Init = SubDistrictResponse().toData()
    }
}