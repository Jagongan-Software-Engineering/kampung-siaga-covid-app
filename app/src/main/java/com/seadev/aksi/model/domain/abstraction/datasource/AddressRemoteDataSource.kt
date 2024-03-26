package com.seadev.aksi.model.domain.abstraction.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.City
import com.seadev.aksi.model.domain.model.District
import com.seadev.aksi.model.domain.model.Province
import com.seadev.aksi.model.domain.model.SubDistrict
import kotlinx.coroutines.flow.Flow

interface AddressRemoteDataSource {
    suspend fun getProvince(): Flow<ResultState<List<Province>>>
    suspend fun getCity() : Flow<ResultState<List<City>>>
    suspend fun getDistrict() : Flow<ResultState<List<District>>>
    suspend fun getSubDistrict() : Flow<ResultState<List<SubDistrict>>>
}