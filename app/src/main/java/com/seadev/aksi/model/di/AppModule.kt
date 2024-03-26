package com.seadev.aksi.model.di

import com.seadev.aksi.model.data.remote.api.AddressApi
import com.seadev.aksi.model.data.remote.api.AssessmentApi
import com.seadev.aksi.model.data.remote.api.AuthApi
import com.seadev.aksi.model.data.remote.api.DailyCaseApi
import com.seadev.aksi.model.data.remote.api.HotlineApi
import com.seadev.aksi.model.data.remote.api.ProvinceApi
import com.seadev.aksi.model.data.remote.datasource.AddressRemoteDataSourceImpl
import com.seadev.aksi.model.data.remote.datasource.AssessmentRemoteDataSourceImpl
import com.seadev.aksi.model.data.remote.datasource.AuthRemoteDataSourceImpl
import com.seadev.aksi.model.data.remote.datasource.DailyCaseRemoteDataSourceImpl
import com.seadev.aksi.model.data.remote.datasource.HotlineRemoteDataSourceImpl
import com.seadev.aksi.model.data.repository.AddressRepositoryImpl
import com.seadev.aksi.model.data.repository.AssessmentRepositoryImpl
import com.seadev.aksi.model.data.repository.AuthRepositoryImpl
import com.seadev.aksi.model.data.repository.DailyCaseRepositoryImpl
import com.seadev.aksi.model.data.repository.HotlineRepositoryImpl
import com.seadev.aksi.model.domain.abstraction.datasource.AddressLocalDataSource
import com.seadev.aksi.model.domain.abstraction.datasource.AddressRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.datasource.AssessmentRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.datasource.AuthRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.datasource.DailyCaseRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.datasource.HotlineRemoteDataSource
import com.seadev.aksi.model.domain.abstraction.repository.AddressRepository
import com.seadev.aksi.model.domain.abstraction.repository.AssessmentRepository
import com.seadev.aksi.model.domain.abstraction.repository.AuthRepository
import com.seadev.aksi.model.domain.abstraction.repository.DailyCaseRepository
import com.seadev.aksi.model.domain.abstraction.repository.HotlineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDailyCaseApi() = DailyCaseApi()

    @Provides
    fun provideHotlineApi() = HotlineApi()

    @Provides
    fun provideProvinceApi() = ProvinceApi()

    @Provides
    fun provideAssessmentApi() = AssessmentApi()

    @Provides
    fun provideAuthApi() = AuthApi()

    @Provides
    fun provideDailyCaseRemoteDataSource(
        dailyCaseApi: DailyCaseApi
    ): DailyCaseRemoteDataSource = DailyCaseRemoteDataSourceImpl(dailyCaseApi)

    @Provides
    fun provideHotlineRemoteDataSource(
        hotlineApi: HotlineApi
    ): HotlineRemoteDataSource = HotlineRemoteDataSourceImpl(hotlineApi)

    @Provides
    fun provideProvinceRemoteDataSource(
        provinceApi: ProvinceApi,
        addressApi: AddressApi,
    ): AddressRemoteDataSource = AddressRemoteDataSourceImpl(provinceApi, addressApi)

    @Provides
    fun provideAssessmentRemoteDataSource(
        assessmentApi: AssessmentApi
    ): AssessmentRemoteDataSource = AssessmentRemoteDataSourceImpl(assessmentApi)

    @Provides
    fun provideAuthRemoteDataSource(
        authApi: AuthApi
    ): AuthRemoteDataSource = AuthRemoteDataSourceImpl(authApi)

    @Provides
    fun provideDailyCaseRepository(
        dailyCaseRemoteDataSource: DailyCaseRemoteDataSource
    ): DailyCaseRepository = DailyCaseRepositoryImpl(dailyCaseRemoteDataSource)

    @Provides
    fun provideHotlineRepository(
        hotlineRemoteDataSource: HotlineRemoteDataSource
    ): HotlineRepository = HotlineRepositoryImpl(hotlineRemoteDataSource)

    @Provides
    fun provideAddressRepository(
        addressRemoteDataSource: AddressRemoteDataSource,
        addressLocalDataSource: AddressLocalDataSource,
    ): AddressRepository = AddressRepositoryImpl(addressRemoteDataSource, addressLocalDataSource)

    @Provides
    fun provideAssessmentRepository(
        assessmentRemoteDataSource: AssessmentRemoteDataSource
    ): AssessmentRepository = AssessmentRepositoryImpl(assessmentRemoteDataSource)

    @Provides
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSource)
}