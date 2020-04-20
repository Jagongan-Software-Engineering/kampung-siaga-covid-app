package com.seadev.aksi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asesmen(
        @PrimaryKey
        var date: String,
        @ColumnInfo(name = "iduser")
        var idUser: String? = "",
        @ColumnInfo(name = "data")
        var mData: String? = "",
        @ColumnInfo(name = "total")
        var mTotal: Int? = 0,
        @ColumnInfo(name = "risiko")
        var risiko: String? = "",
        @ColumnInfo(name = "rtrw")
        var rtrw: String? = ""
)