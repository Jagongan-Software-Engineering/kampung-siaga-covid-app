package com.seadev.aksi.model.data.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.datasource.DailyCaseRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.repository.DailyCaseRepository
import com.seadev.aksi.model.domain.model.DistributionCase
import com.seadev.aksi.model.domain.model.SummaryCase
import kotlinx.coroutines.flow.Flow

class DailyCaseRepositoryImpl(
    private val dailyCaseRemoteDataSource: DailyCaseRemoteDataSource
) : DailyCaseRepository {
    override suspend fun getSummary(): Flow<ResultState<SummaryCase>> =
        dailyCaseRemoteDataSource.getSummary()

    override suspend fun getDistribution(): Flow<ResultState<List<DistributionCase>>> =
        dailyCaseRemoteDataSource.getDistribution()
}