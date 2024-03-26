package com.seadev.aksi.model.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    val Timestamp.formatTimeFull: String get() = SimpleDateFormat(
        "EEEE, dd MMMM yyyy, kk:mm", Locale("id", "id")
    ).format(this.toDate())

    val Timestamp.isToday : Boolean get() {
        val sdf = SimpleDateFormat(
            "dd MMMM yyyy", Locale("id", "id")
        )
        val today = sdf.format(Timestamp.now().toDate())
        val actual = sdf.format(this.toDate())
        return actual.equals(today)
    }
}