package com.seadev.aksi.model.data.remote.response

import com.google.firebase.Timestamp
import com.seadev.aksi.model.domain.model.HistoryAssessment
import java.util.Date

data class HistoryAssessmentResponse(
    val userId: String? = null,
    val assessmentResult: String? = null,
    val dateCreated: Timestamp? = null,
)

fun HistoryAssessmentResponse.toData() = HistoryAssessment(
    userId = this.userId ?: "",
    assessmentResult = this.assessmentResult ?: "",
    dateCreated = this.dateCreated ?: Timestamp(Date(1586538000000))
)