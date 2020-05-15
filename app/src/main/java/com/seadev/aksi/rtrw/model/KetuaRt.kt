package com.seadev.aksi.rtrw.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KetuaRt(
        var userId: String? = "",
        val nama: String? = "",
        var rtrw: String? = "",
        var idDesa: String? = "",
        var idKacamatan: String? = "",
        var idKotaKab: String? = "",
        var idProvinsi: String? = "",
        var warga: Int? = 0,
        var urlBukti: String? = "",
        var status: Int? = 0,
        var active: Int? = 0
) : Parcelable