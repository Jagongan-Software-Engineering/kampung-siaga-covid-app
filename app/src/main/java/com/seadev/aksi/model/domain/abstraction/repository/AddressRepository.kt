package com.seadev.aksi.model.domain.abstraction.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.City
import com.seadev.aksi.model.domain.model.District
import com.seadev.aksi.model.domain.model.Province
import com.seadev.aksi.model.domain.model.SubDistrict
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun getRemoteProvince()
    suspend fun getRemoteCity()
    suspend fun getRemoteDistrict()
    suspend fun getRemoteSubDistrict(): Flow<ResultState<List<Long>>>
    suspend fun getProvince(): Flow<ResultState<List<Province>>>
    suspend fun getProvince(provinceId: String): Flow<ResultState<Province>>
    suspend fun getCity(provinceId: String) : Flow<ResultState<List<City>>>
    suspend fun getDistrict(cityId: String) : Flow<ResultState<List<District>>>
    suspend fun getSubDistrict(districtId: String) : Flow<ResultState<List<SubDistrict>>>
}