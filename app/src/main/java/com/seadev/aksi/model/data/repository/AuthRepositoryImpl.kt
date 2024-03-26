package com.seadev.aksi.model.data.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.datasource.AuthRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.repository.AuthRepository
import com.seadev.aksi.model.domain.model.AksiUser
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override fun getCurrentUser(uId: String): Flow<ResultState<AksiUser>> =
        authRemoteDataSource.getCurrentUser(uId)

    override fun createNewUser(aksiUser: AksiUser): Flow<ResultState<String>> =
        authRemoteDataSource.createNewUser(aksiUser)
}