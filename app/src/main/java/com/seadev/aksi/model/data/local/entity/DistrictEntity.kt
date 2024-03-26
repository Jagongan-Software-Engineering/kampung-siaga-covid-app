package com.seadev.aksi.model.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seadev.aksi.model.domain.model.District

@Entity(tableName = "district")
data class DistrictEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("city_id")
    val cityId: String? = null,
)

fun DistrictEntity.toData() = District(
    id = this.id ?: "",
    name = this.name ?: "",
    cityId = this.cityId ?: ""
)

fun District.toEntity() = DistrictEntity(
    id = this.id,
    name = this.name,
    cityId = this.cityId
)