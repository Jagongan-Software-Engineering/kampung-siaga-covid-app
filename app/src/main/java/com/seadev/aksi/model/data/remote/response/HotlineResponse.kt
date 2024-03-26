package com.seadev.aksi.model.data.remote.response

import com.seadev.aksi.model.domain.model.Hotline

data class HotlineResponse(
    val hospital: String? = null,
    val phone: String? = null,
    val provinceId: String? = null,
)

fun HotlineResponse.toData() = Hotline(
    hospital = this.hospital ?: "",
    phone = this.phone ?: "",
    provinceId = this.provinceId ?: ""
)
