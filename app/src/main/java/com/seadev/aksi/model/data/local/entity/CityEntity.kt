package com.seadev.aksi.model.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seadev.aksi.model.domain.model.City

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("province_id")
    val provinceId: String? = null,
)

fun CityEntity.toData() = City(
    id = this.id?:"",
    name = this.name?:"",
    provinceId = this.provinceId?:""
)

fun City.toEntity() = CityEntity(
    id = this.id,
    name = this.name,
    provinceId = this.provinceId
)