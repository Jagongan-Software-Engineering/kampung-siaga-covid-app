package com.seadev.aksi.model.data.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.datasource.AddressLocalDataSource
import com.seadev.aksi.model.domain.abstraction.datasource.AddressRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.repository.AddressRepository
import com.seadev.aksi.model.domain.model.City
import com.seadev.aksi.model.domain.model.District
import com.seadev.aksi.model.domain.model.Province
import com.seadev.aksi.model.domain.model.SubDistrict
import kotlinx.coroutines.flow.flow

class AddressRepositoryImpl(
    private val addressRemoteDataSource: AddressRemoteDataSource,
    private val addressLocalDataSource: AddressLocalDataSource
) : AddressRepository {
    override suspend fun getRemoteProvince() {
        addressLocalDataSource.isProvinceEmpty().collect { isEmpty ->
            if (isEmpty) addressRemoteDataSource.getProvince().collect {
                if (it is ResultState.Success) {
                    addressLocalDataSource.addAllProvince(it.data)
                }
            }
        }
    }

    override suspend fun getRemoteCity() {
        addressLocalDataSource.isCityEmpty().collect { isEmpty ->
            if (isEmpty) addressRemoteDataSource.getCity().collect {
                if (it is ResultState.Success) {
                    addressLocalDataSource.addAllCity(it.data)
                }
            }
        }
    }

    override suspend fun getRemoteDistrict() {
        addressLocalDataSource.isDistrictEmpty().collect { isEmpty ->
            if (isEmpty) addressRemoteDataSource.getDistrict().collect {
                if (it is ResultState.Success) {
                    addressLocalDataSource.addAllDistrict(it.data)
                }
            }
        }
    }

    override suspend fun getRemoteSubDistrict() = flow<ResultState<List<Long>>> {
        emit(ResultState.loading())
        addressLocalDataSource.isSubDistrictEmpty().collect { isEmpty ->
            if (isEmpty) addressRemoteDataSource.getSubDistrict().collect {
                when (it) {
                    is ResultState.Loading -> {}
                    is ResultState.Success -> {
                        emit(ResultState.success(addressLocalDataSource.addAllSubDistrict(it.data)))
                    }

                    is ResultState.Failed -> {
                        emit(ResultState.failed(it.message))
                    }
                }
            } else emit(ResultState.success(listOf()))
        }
    }

    override suspend fun getProvince() = flow<ResultState<List<Province>>> {
        emit(ResultState.loading())
        addressLocalDataSource.getProvince().collect {
            if (it.isNotEmpty()) emit(ResultState.success(it))
            else emit(ResultState.failed("Data Province is Empty"))
        }
    }

    override suspend fun getProvince(provinceId: String) = flow<ResultState<Province>> {
        emit(ResultState.loading())
        addressLocalDataSource.getProvince(provinceId).collect {
            if (it != Province.Init) emit(ResultState.success(it))
            else emit(ResultState.failed("Data Province is Empty"))
        }
    }

    override suspend fun getCity(provinceId: String) = flow<ResultState<List<City>>> {
        emit(ResultState.loading())
        addressLocalDataSource.getCity(provinceId).collect {
            if (it.isNotEmpty()) emit(ResultState.success(it))
            else emit(ResultState.failed("Data City is Empty"))
        }
    }

    override suspend fun getDistrict(cityId: String) = flow<ResultState<List<District>>> {
        emit(ResultState.loading())
        addressLocalDataSource.getDistrict(cityId).collect {
            if (it.isNotEmpty()) emit(ResultState.success(it))
            else emit(ResultState.failed("Data District is Empty"))
        }
    }

    override suspend fun getSubDistrict(districtId: String) = flow<ResultState<List<SubDistrict>>> {
        emit(ResultState.loading())
        addressLocalDataSource.getSubDistrict(districtId).collect {
            if (it.isNotEmpty()) emit(ResultState.success(it))
            else emit(ResultState.failed("Data Sub District is Empty"))
        }
    }
}