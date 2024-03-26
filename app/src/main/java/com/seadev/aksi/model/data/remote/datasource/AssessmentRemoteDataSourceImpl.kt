package com.seadev.aksi.model.data.remote.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.data.remote.api.AssessmentApi
import com.seadev.aksi.model.data.remote.response.HistoryAssessmentResponse
import com.seadev.aksi.model.data.remote.response.toData
import com.seadev.aksi.model.domain.abstraction.datasource.AssessmentRemoteDataSource
import com.seadev.aksi.model.domain.model.HistoryAssessment
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AssessmentRemoteDataSourceImpl(
    private val assessmentApi: AssessmentApi
) : AssessmentRemoteDataSource {
    override suspend fun addHistoryAssessment(historyAssessment: HistoryAssessment) = callbackFlow {
        trySend(ResultState.loading())
        assessmentApi.assessment.add(historyAssessment)
            .addOnSuccessListener {
                trySend(ResultState.success(true))
            }
            .addOnFailureListener {
                trySend(ResultState.failed(it.message ?: ""))
                this.close(it)
            }
        awaitClose { this.cancel() }
    }

    override suspend fun getHistoryAssessment(userId: String) = callbackFlow {
        assessmentApi.assessment.whereEqualTo("userId", userId).get()
            .addOnSuccessListener {
                it.documents.map { d ->
                    d.toObject(HistoryAssessmentResponse::class.java)?.toData()
                        ?: HistoryAssessment.Init
                }.let { result ->
                    trySend(ResultState.success(result))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.failed(it.message ?: ""))
                this.close(it)
            }
        awaitClose { this.cancel() }
    }
}