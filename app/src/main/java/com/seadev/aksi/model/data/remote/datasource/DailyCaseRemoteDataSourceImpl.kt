package com.seadev.aksi.model.data.remote.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.data.remote.api.DailyCaseApi
import com.seadev.aksi.model.data.remote.response.DistributionCaseResponse
import com.seadev.aksi.model.data.remote.response.SummaryCaseResponse
import com.seadev.aksi.model.data.remote.response.toData
import com.seadev.aksi.model.domain.abstraction.datasource.DailyCaseRemoteDataSource
import com.seadev.aksi.model.domain.model.DistributionCase
import com.seadev.aksi.model.domain.model.SummaryCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class DailyCaseRemoteDataSourceImpl(
    private val dailyCaseApi: DailyCaseApi
) : DailyCaseRemoteDataSource {

    override suspend fun getSummary() = callbackFlow {
        trySend(ResultState.loading())
        dailyCaseApi.summary.get()
            .addOnSuccessListener {
                val result = ResultState.success(
                    it.toObject(SummaryCaseResponse::class.java)?.toData()
                        ?: SummaryCase.Init
                )
                trySend(result)
            }
            .addOnFailureListener {
                trySend(ResultState.failed(it.message ?: ""))
                this.close(it)
            }
        awaitClose { this.cancel() }
    }

    override suspend fun getDistribution() = callbackFlow {
        trySend(ResultState.loading())
        dailyCaseApi.distribution.get()
            .addOnSuccessListener {
                it.documents.map { docs ->
                    docs.toObject(DistributionCaseResponse::class.java)?.toData()
                        ?: DistributionCase.Init
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