package com.seadev.aksi.model.domain.abstraction.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.DistributionCase
import com.seadev.aksi.model.domain.model.Hotline
import com.seadev.aksi.model.domain.model.SummaryCase
import kotlinx.coroutines.flow.Flow

interface HotlineRemoteDataSource {
    suspend fun getListHotline(): Flow<ResultState<List<Hotline>>>
}