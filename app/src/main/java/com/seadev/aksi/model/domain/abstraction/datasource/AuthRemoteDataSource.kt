package com.seadev.aksi.model.domain.abstraction.datasource

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.AksiUser
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    fun getCurrentUser(uId: String): Flow<ResultState<AksiUser>>
    fun createNewUser(aksiUser: AksiUser): Flow<ResultState<String>>
}