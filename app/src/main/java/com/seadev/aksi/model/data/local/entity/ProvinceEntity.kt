package com.seadev.aksi.model.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seadev.aksi.model.domain.model.Province

@Entity(tableName = "province")
data class ProvinceEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("name")
    val name: String? = null
)

fun ProvinceEntity.toData() = Province(
    id = this.id,
    name = this.name ?: ""
)

fun Province.toEntity() = ProvinceEntity(
    id = this.id,
    name = this.name
)