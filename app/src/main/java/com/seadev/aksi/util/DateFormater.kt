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
                    "Senin" -> "Senin"
                    "Selasa" -> "Selasa"
                    "Rabu" -> "Rabu"
                    "Kamis" -> "Kamis"
                    "Jumat" -> "Jumat"
                    "Sabtu" -> "Sabtu"
                    "Minggu" -> "Minggu"
                    else -> "NotValid"
                }
            }
        }

        fun getBulan(mMonth: String): String {
            return when (mMonth) {
                "Januari" -> "Januari"
                "Fabruari" -> "Fabruari"
                "Maret" -> "Maret"
                "April" -> "April"
                "Mei" -> "Mei"
                "Juni" -> "Juni"
                "Juli" -> "Juli"
                "Agustus" -> "Agustus"
                "September" -> "September"
                "Oktober" -> "Oktober"
                "November" -> "November"
                "Desember" -> "Desember"
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