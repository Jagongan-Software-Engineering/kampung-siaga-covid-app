package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.AksiUserResponse
import com.seadev.aksi.model.data.remote.response.toData

data class AksiUser(
    val uid: String,
    val nik: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val provinceId: String,
    val cityId: String,
    val districtId: String,
    val subDistrictId: String,
    val rtNumber: String,
    val rwNumber: String,
    val fullAddress: String
) {
    companion object {
        val Init = AksiUserResponse().toData()
    }
}
