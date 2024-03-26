package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.DistributionCaseResponse
import com.seadev.aksi.model.data.remote.response.toData

data class DistributionCase(
    val province: String,
    val positive: Int,
    val recover: Int,
    val died: Int
) {
    companion object {
        val Init = DistributionCaseResponse().toData()
    }
}