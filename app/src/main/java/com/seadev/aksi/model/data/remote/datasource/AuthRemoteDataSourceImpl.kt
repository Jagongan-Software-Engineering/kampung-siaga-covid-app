package com.seadev.aksi.model.data.remote.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.data.remote.api.AuthApi
import com.seadev.aksi.model.data.remote.response.AksiUserResponse
import com.seadev.aksi.model.data.remote.response.toData
import com.seadev.aksi.model.data.remote.response.toResponse
import com.seadev.aksi.model.domain.abstraction.datasource.AuthRemoteDataSource
import com.seadev.aksi.model.domain.model.AksiUser
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AuthRemoteDataSourceImpl(
    private val authApi: AuthApi
) : AuthRemoteDataSource {

    override fun getCurrentUser(uId: String) = callbackFlow {
        trySend(ResultState.loading())
        authApi.users.document(uId)
            .get()
            .addOnSuccessListener {
                trySend(
                    ResultState.success(
                        it.toObject(AksiUserResponse::class.java)?.toData() ?: AksiUser.Init
                    )
                )
            }
            .addOnFailureListener {
                trySend(ResultState.failed("Gagal mendapatkan data user"))
                this.close(it)
            }
        awaitClose { this.close() }
    }

    override fun createNewUser(aksiUser: AksiUser) = callbackFlow {
        trySend(ResultState.loading())
        authApi.users.document(aksiUser.uid)
            .set(aksiUser.toResponse().copy(uid = null))
            .addOnSuccessListener {
                trySend(ResultState.success("Sukses membuat akun baru"))
            }
            .addOnFailureListener {
                trySend(ResultState.failed("Gagal membuat akun baru"))
                this.close(it)
            }
        awaitClose { this.cancel() }
    }
}