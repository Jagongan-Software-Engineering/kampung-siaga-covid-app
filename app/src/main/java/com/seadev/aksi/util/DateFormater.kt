package com.seadev.aksi.util

class DateFormater {
    companion object {
        fun getHari(mDay: String, isToday: Int): String {
            if (isToday == 1) {
                return "Hari ini"
            } else {
                return when (mDay) {
                    "Monday" -> "Senin"
                    "Tuesday" -> "Selasa"
                    "Wednesday" -> "Rabu"
                    "Thursday" -> "Kamis"
                    "Friday" -> "Jumat"
                    "Saturday" -> "Sabtu"
                    "Sunday" -> "Minggu"
                    else -> "NotValid"
                }
            }
        }

        fun getBulan(mMonth: String): String {
            return when (mMonth) {
                "January" -> "Januari"
                "February" -> "Fabruari"
                "March" -> "Maret"
                "April" -> "April"
                "May" -> "Mei"
                "June" -> "Juni"
                "July" -> "Juli"
                "August" -> "Agustus"
                "September" -> "September"
                "October" -> "Oktober"
                "November" -> "November"
                "December" -> "Desember"
                else -> "NotValid"
            }
        }
    }
}