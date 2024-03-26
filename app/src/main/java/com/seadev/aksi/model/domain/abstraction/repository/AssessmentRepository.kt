package com.seadev.aksi.model.domain.abstraction.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.HistoryAssessment
import kotlinx.coroutines.flow.Flow

interface AssessmentRepository {
    suspend fun addHistoryAssessment(historyAssessment: HistoryAssessment): Flow<ResultState<Boolean>>
    suspend fun getHistoryAssessment(userId: String): Flow<ResultState<List<HistoryAssessment>>>
}