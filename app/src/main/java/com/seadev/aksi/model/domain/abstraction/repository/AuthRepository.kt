package com.seadev.aksi.model.domain.abstraction.repository

import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.model.AksiUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(uId: String): Flow<ResultState<AksiUser>>
    fun createNewUser(aksiUser: AksiUser): Flow<ResultState<String>>
}