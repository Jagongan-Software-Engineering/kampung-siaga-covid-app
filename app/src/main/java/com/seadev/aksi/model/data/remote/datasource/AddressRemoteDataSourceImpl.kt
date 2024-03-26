package com.seadev.aksi.model.data.remote.datasource

import android.util.Log
import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.data.remote.api.AddressApi
import com.seadev.aksi.model.data.remote.api.ProvinceApi
import com.seadev.aksi.model.data.remote.response.ProvinceResponse
import com.seadev.aksi.model.data.remote.response.toData
import com.seadev.aksi.model.domain.abstraction.datasource.AddressRemoteDataSource
import com.seadev.aksi.model.domain.model.Province
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class AddressRemoteDataSourceImpl(
    private val provinceApi: ProvinceApi,
    private val addressApi: AddressApi
) : AddressRemoteDataSource {
    override suspend fun getProvince() = callbackFlow {
        trySend(ResultState.loading())
        provinceApi.province.get()
            .addOnSuccessListener { query ->
                query.documents.map { d ->
                    d.toObject(ProvinceResponse::class.java)?.toData() ?: Province.Init
                }.let { result ->
                    trySend(ResultState.success(result))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.failed(it.message ?: ""))
                this.close(it)
            }
        awaitClose { this.cancel() }
    }

    override suspend fun getCity() = flow {
        emit(ResultState.loading())
        val response = addressApi.getCity()
        Log.d("TAG", "getCity: response = ${response.data}")
        if (response.data != null) {
            emit(ResultState.success(response.data.map { d -> d.toData() }))
        } else emit(ResultState.failed("Failed get City Data"))
    }

    override suspend fun getDistrict() = flow {
        emit(ResultState.loading())
        val response = addressApi.getDistrict()
        Log.d("TAG", "getDistrict: response = ${response.data}")
        if (response.data != null) {
            emit(ResultState.success(response.data.map { d -> d.toData() }))
        } else emit(ResultState.failed("Failed get District Data"))
    }

    override suspend fun getSubDistrict() = flow {
        emit(ResultState.loading())
        val response = addressApi.getSubDistrict()
        Log.d("TAG", "getSubDistrict: response = ${response.data}")
        if (response.data != null) {
            emit(ResultState.success(response.data.map { d -> d.toData() }))
        } else emit(ResultState.failed("Failed get SubDistrict Data"))
    }
}