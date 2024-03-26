package com.seadev.aksi.model.domain.model

import com.google.firebase.Timestamp
import com.seadev.aksi.model.data.remote.response.HistoryAssessmentResponse
import com.seadev.aksi.model.data.remote.response.toData

data class HistoryAssessment(
    val userId: String,
    val assessmentResult: String,
    val dateCreated: Timestamp
) {
    companion object {
        val Init = HistoryAssessmentResponse().toData()
    }
}
