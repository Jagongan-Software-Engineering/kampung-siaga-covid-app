package com.seadev.kampungsiagacovid.util

class ReportHistoryFormater {
    companion object {
        fun getTitleReport(title: String): String {
            return when (title) {
                "rendah" -> "Risiko Rendah"
                "sedang" -> "Risiko Sedang"
                "tinggi" -> "Risiko Tinggi "
                else -> "NotValid"
            }
        }

        fun getDescReport(title: String): String {
            return when (title) {
                "rendah" -> "Anda tidak memiliki gejala maupun riwayat paparan terkait Covid-19"
                "sedang" -> "Anda berkemungkinan / berisiko terpapar Covid-19"
                "tinggi" -> "Anda memiliki gekala, riwayat paparan terkait Covid-19 "
                else -> "NotValid"
            }
        }

        fun getImgReport(title: String): Int {
            return when (title) {
                "rendah" -> android.R.color.holo_green_light
                "sedang" -> android.R.color.holo_orange_light
                "tinggi" -> android.R.color.holo_red_light
                else -> android.R.color.holo_purple
            }
        }
    }
}