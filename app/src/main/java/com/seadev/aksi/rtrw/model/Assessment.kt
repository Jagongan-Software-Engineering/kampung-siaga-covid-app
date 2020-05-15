package com.seadev.aksi.rtrw.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Assessment(
        var date: String? = "",
        var idUser: String? = "",
        var mData: String? = "",
        var mTotal: Int? = 0,
        var risiko: String? = "",
        var rtrw: String? = ""
) : Parcelable