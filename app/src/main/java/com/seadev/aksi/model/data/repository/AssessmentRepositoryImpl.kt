package com.seadev.aksi.model.data.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.datasource.AssessmentRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.repository.AssessmentRepository
import com.seadev.aksi.model.domain.model.HistoryAssessment
import kotlinx.coroutines.flow.Flow

class AssessmentRepositoryImpl(
    private val assessmentRemoteDataSource: AssessmentRemoteDataSource
): AssessmentRepository {
    override suspend fun addHistoryAssessment(historyAssessment: HistoryAssessment): Flow<ResultState<Boolean>> =
        assessmentRemoteDataSource.addHistoryAssessment(historyAssessment)

    override suspend fun getHistoryAssessment(userId: String): Flow<ResultState<List<HistoryAssessment>>> =
        assessmentRemoteDataSource.getHistoryAssessment(userId)
}