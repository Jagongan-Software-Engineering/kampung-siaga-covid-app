package com.seadev.aksi.model.domain.abstraction.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.DistributionCase
import com.seadev.aksi.model.domain.model.SummaryCase
import kotlinx.coroutines.flow.Flow

interface DailyCaseRepository {
    suspend fun getSummary(): Flow<ResultState<SummaryCase>>
    suspend fun getDistribution(): Flow<ResultState<List<DistributionCase>>>
}