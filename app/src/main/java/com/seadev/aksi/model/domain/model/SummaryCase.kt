package com.seadev.aksi.model.domain.model

import com.seadev.aksi.model.data.remote.response.SummaryCaseResponse
import com.seadev.aksi.model.data.remote.response.toData

data class SummaryCase(
    val positive: Int,
    val inCare: Int,
    val died: Int,
    val recover: Int
){
    companion object{
        val Init = SummaryCaseResponse().toData()
    }
}
