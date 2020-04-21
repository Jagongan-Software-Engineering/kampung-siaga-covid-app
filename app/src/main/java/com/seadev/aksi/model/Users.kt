package com.seadev.aksi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
        var idUser: String? = "",
        var phone: String? = "",
        var nama: String? = "",
        var alamat: String? = "",
        var idProvinsi: String? = "",
        var idKotaKab: String? = "",
        var idKacamatan: String? = "",
        var idDesa: String? = "",
        var rtrw: String? = "",
        var nik: String? = ""
) : Parcelable