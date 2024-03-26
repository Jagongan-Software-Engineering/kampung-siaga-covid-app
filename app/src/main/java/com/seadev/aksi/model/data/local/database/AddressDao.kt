package com.seadev.aksi.model.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seadev.aksi.model.data.local.entity.CityEntity
import com.seadev.aksi.model.data.local.entity.DistrictEntity
import com.seadev.aksi.model.data.local.entity.ProvinceEntity
import com.seadev.aksi.model.data.local.entity.SubDistrictEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProvinces(province: List<ProvinceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCities(cities: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDistricts(districts: List<DistrictEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubDistricts(subDistricts: List<SubDistrictEntity>): List<Long>

    @Query("SELECT * FROM province ORDER BY name")
    fun getAllProvince(): Flow<List<ProvinceEntity>>

    @Query("SELECT * FROM province WHERE id = :provinceId LIMIT 1")
    fun getProvince(provinceId: String): Flow<ProvinceEntity>

    @Query("SELECT * FROM city ORDER BY name")
    fun getAllCities(): Flow<List<ProvinceEntity>>

    @Query("SELECT * FROM city WHERE province_id = :provinceId ORDER BY name")
    fun getAllCities(provinceId: String): Flow<List<CityEntity>>

    @Query("SELECT * FROM district ORDER BY name")
    fun getAllDistrict(): Flow<List<DistrictEntity>>

    @Query("SELECT * FROM district WHERE city_id = :cityId ORDER BY name")
    fun getAllDistrict(cityId: String): Flow<List<DistrictEntity>>

    @Query("SELECT * FROM sub_district ORDER BY name")
    fun getAllSubDistrict(): Flow<List<SubDistrictEntity>>

    @Query("SELECT * FROM sub_district WHERE district_id = :districtId ORDER BY name")
    fun getAllSubDistrict(districtId: String): Flow<List<SubDistrictEntity>>
}