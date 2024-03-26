package com.seadev.aksi.model.domain.abstraction.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.DistributionCase
import com.seadev.aksi.model.domain.model.SummaryCase
import kotlinx.coroutines.flow.Flow

interface DailyCaseRemoteDataSource {
    suspend fun getSummary(): Flow<ResultState<SummaryCase>>
    suspend fun getDistribution(): Flow<ResultState<List<DistributionCase>>>
}