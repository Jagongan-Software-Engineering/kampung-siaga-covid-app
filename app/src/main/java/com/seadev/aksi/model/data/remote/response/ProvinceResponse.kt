package com.seadev.aksi.model.data.remote.response

import com.seadev.aksi.model.domain.model.Province

data class ProvinceResponse(
    val name: String? = null,
    val id: String? = null
)

fun ProvinceResponse.toData() = Province(
    name = this.name ?: "",
    id = this.id ?: ""
)