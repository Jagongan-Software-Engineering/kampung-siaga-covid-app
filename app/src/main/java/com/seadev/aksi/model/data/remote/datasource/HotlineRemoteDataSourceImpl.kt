package com.seadev.aksi.model.data.remote.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.data.remote.api.HotlineApi
import com.seadev.aksi.model.data.remote.response.HotlineResponse
import com.seadev.aksi.model.data.remote.response.toData
import com.seadev.aksi.model.domain.abstraction.datasource.HotlineRemoteDataSource
import com.seadev.aksi.model.domain.model.Hotline
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class HotlineRemoteDataSourceImpl(
    private val hotlineApi: HotlineApi
) : HotlineRemoteDataSource {
    override suspend fun getListHotline() = callbackFlow {
        trySend(ResultState.loading())
        hotlineApi.hotline.get()
            .addOnSuccessListener {
                it.documents.map { d ->
                    d.toObject(HotlineResponse::class.java)?.toData() ?: Hotline.Init
                }.let { result ->
                    trySend(ResultState.success(result))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.failed(it.message?:""))
                this.close(it)
            }
        awaitClose { this.cancel() }
    }

}