package com.seadev.aksi.model.data.local.datasource

import com.seadev.aksi.model.data.local.database.AddressDao
import com.seadev.aksi.model.data.local.entity.toData
import com.seadev.aksi.model.data.local.entity.toEntity
import com.seadev.aksi.model.domain.abstraction.datasource.AddressLocalDataSource
import com.seadev.aksi.model.domain.model.City
import com.seadev.aksi.model.domain.model.District
import com.seadev.aksi.model.domain.model.Province
import com.seadev.aksi.model.domain.model.SubDistrict
import kotlinx.coroutines.flow.flow

class AddressLocalDataSourceImpl(
    private val addressDao: AddressDao
) : AddressLocalDataSource {
    override suspend fun getProvince() = flow {
        addressDao.getAllProvince().collect {
            emit(it.map { d -> d.toData() })
        }
    }

    override suspend fun getProvince(provinceId: String) = flow<Province> {
        addressDao.getProvince(provinceId).collect {
            emit(it.toData())
        }
    }

    override suspend fun getCity(provinceId: String) = flow {
        addressDao.getAllCities(provinceId).collect {
            emit(it.map { d -> d.toData() })
        }
    }

    override suspend fun getDistrict(cityId: String) = flow {
        addressDao.getAllDistrict(cityId).collect {
            emit(it.map { d -> d.toData() })
        }
    }

    override suspend fun getSubDistrict(districtId: String) = flow {
        addressDao.getAllSubDistrict(districtId).collect {
            emit(it.map { d -> d.toData() })
        }
    }

    override suspend fun isProvinceEmpty() = flow {
        addressDao.getAllProvince().collect { emit(it.isEmpty()) }
    }

    override suspend fun isCityEmpty() = flow {
        addressDao.getAllCities().collect { emit(it.isEmpty()) }
    }

    override suspend fun isDistrictEmpty() = flow {
        addressDao.getAllDistrict().collect { emit(it.isEmpty()) }
    }

    override suspend fun isSubDistrictEmpty() = flow {
        addressDao.getAllSubDistrict().collect { emit(it.isEmpty()) }
    }


    override suspend fun addAllProvince(listProvince: List<Province>) {
        addressDao.addProvinces(listProvince.map { it.toEntity() })
    }

    override suspend fun addAllCity(listCity: List<City>) {
        addressDao.addCities(listCity.map { it.toEntity() })
    }

    override suspend fun addAllDistrict(listDistrict: List<District>) {
        addressDao.addDistricts(listDistrict.map { it.toEntity() })
    }

    override suspend fun addAllSubDistrict(listSubDistrict: List<SubDistrict>): List<Long> {
        return addressDao.addSubDistricts(listSubDistrict.map { it.toEntity() })
    }
}