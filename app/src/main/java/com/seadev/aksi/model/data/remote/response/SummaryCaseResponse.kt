package com.seadev.aksi.model.data.remote.response

import com.seadev.aksi.model.domain.model.SummaryCase

data class SummaryCaseResponse(
    val positive: Int? = null,
    val inCare: Int? = null,
    val died: Int? = null,
    val recover: Int? = null
)

fun SummaryCaseResponse.toData() = SummaryCase(
    positive = this.positive?:0,
    inCare = this.inCare?:0,
    died = this.died?:0,
    recover = this.recover?:0
)