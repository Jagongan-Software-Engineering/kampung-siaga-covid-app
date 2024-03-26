package com.seadev.aksi.model.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seadev.aksi.model.domain.model.SubDistrict

@Entity(tableName = "sub_district")
data class SubDistrictEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("district_id")
    val districtId: String? = null,
)

fun SubDistrictEntity.toData() = SubDistrict(
    id = this.id ?: "",
    name = this.name ?: "",
    districtId = this.districtId ?: ""
)

fun SubDistrict.toEntity() = SubDistrictEntity(
    id = this.id,
    name = this.name,
    districtId = this.districtId
)