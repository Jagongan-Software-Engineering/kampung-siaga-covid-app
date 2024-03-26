package com.seadev.aksi.model.data.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.datasource.HotlineRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.repository.HotlineRepository
import com.seadev.aksi.model.domain.model.Hotline
import kotlinx.coroutines.flow.Flow

class HotlineRepositoryImpl(
    private val hotlineRemoteDataSource: HotlineRemoteDataSource
): HotlineRepository {
    override suspend fun getListHotline(): Flow<ResultState<List<Hotline>>> =
        hotlineRemoteDataSource.getListHotline()
}