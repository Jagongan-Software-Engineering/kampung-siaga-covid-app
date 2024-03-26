package com.seadev.aksi.model.domain.abstraction.datasource

import com.seadev.aksi.model.domain.model.City
import com.seadev.aksi.model.domain.model.District
import com.seadev.aksi.model.domain.model.Province
import com.seadev.aksi.model.domain.model.SubDistrict
import kotlinx.coroutines.flow.Flow

interface AddressLocalDataSource {
    suspend fun getProvince(): Flow<List<Province>>
    suspend fun getProvince(provinceId: String): Flow<Province>
    suspend fun getCity(provinceId: String) : Flow<List<City>>
    suspend fun getDistrict(cityId: String) : Flow<List<District>>
    suspend fun getSubDistrict(districtId: String) : Flow<List<SubDistrict>>

    suspend fun isProvinceEmpty() : Flow<Boolean>
    suspend fun isCityEmpty() : Flow<Boolean>
    suspend fun isDistrictEmpty() : Flow<Boolean>
    suspend fun isSubDistrictEmpty() : Flow<Boolean>

    suspend fun addAllProvince(listProvince: List<Province>)
    suspend fun addAllCity(listCity: List<City>)
    suspend fun addAllDistrict(listDistrict: List<District>)
    suspend fun addAllSubDistrict(listSubDistrict: List<SubDistrict>): List<Long>
}