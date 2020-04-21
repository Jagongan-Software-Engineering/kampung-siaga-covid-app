package com.seadev.aksi.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
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
) : Parcelable {
    open fun Asesmen() {}
}