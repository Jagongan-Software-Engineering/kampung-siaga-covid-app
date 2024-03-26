package com.seadev.aksi.model.data.remote.response

import com.google.firebase.firestore.DocumentId
import com.seadev.aksi.model.domain.model.AksiUser

data class AksiUserResponse(
    @DocumentId
    val uid: String? = null,
    val nik: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val provinceId: String? = null,
    val cityId: String? = null,
    val districtId: String? = null,
    val subDistrictId: String? = null,
    val rtNumber: String? = null,
    val rwNumber: String? = null,
    val fullAddress: String? = null,
)

fun AksiUserResponse.toData() = AksiUser(
    uid = this.uid ?: "",
    nik = this.nik ?: "",
    fullName = this.fullName ?: "",
    email = this.email ?: "",
    phoneNumber = this.phoneNumber ?: "",
    provinceId = this.provinceId ?: "",
    cityId = this.cityId ?: "",
    districtId = this.districtId ?: "",
    subDistrictId = this.subDistrictId ?: "",
    rtNumber = this.rtNumber ?: "",
    rwNumber = this.rwNumber ?: "",
    fullAddress = this.fullAddress ?: "",
)

fun AksiUser.toResponse() = AksiUserResponse(
    uid = this.uid,
    nik = this.nik,
    fullName = this.fullName,
    email = this.email,
    phoneNumber = this.phoneNumber,
    provinceId = this.provinceId,
    cityId = this.cityId,
    districtId = this.districtId,
    subDistrictId = this.subDistrictId,
    rtNumber = this.rtNumber,
    rwNumber = this.rwNumber,
    fullAddress = this.fullAddress,
)