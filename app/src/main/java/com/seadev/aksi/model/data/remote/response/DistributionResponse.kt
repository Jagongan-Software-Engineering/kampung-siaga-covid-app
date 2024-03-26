package com.seadev.aksi.model.data.remote.response

import com.seadev.aksi.model.domain.model.DistributionCase

data class DistributionCaseResponse(
    val province: String? = null,
    val positive: Int? = null,
    val recover: Int? = null,
    val died: Int? = null
)

fun DistributionCaseResponse.toData() = DistributionCase(
    province = this.province?:"",
    positive = this.positive?:0,
    recover = this.recover?:0,
    died = this.died?:0
)
