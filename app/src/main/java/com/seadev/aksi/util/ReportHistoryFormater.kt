package com.seadev.aksi.util

import com.seadev.aksi.BuildConfig

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
                "tinggi" -> "Anda memiliki gejala dan riwayat paparan terkait Covid-19 "
                else -> "NotValid"
            }
        }

        fun getImgReport(title: String): String {
            return when (title) {
                "rendah" -> "${BuildConfig.BASE_URL_LOKASI}res%2F021-virus.png?alt=media&token=58458995-308e-480b-aa8c-ab546b90cc42"
                "sedang" -> "${BuildConfig.BASE_URL_LOKASI}res%2F018-isolation.png?alt=media&token=3d68c898-8342-436d-8f9e-6ba7c938f5fc"
                "tinggi" -> "${BuildConfig.BASE_URL_LOKASI}res%2F035-virus.png?alt=media&token=6824d607-6835-4e94-8d97-9e45f37d7784"
                else -> ""
            }
        }

        fun getColorReport(title: String): Int {
            return when (title) {
                "rendah" -> android.R.color.holo_green_light
                "sedang" -> android.R.color.holo_orange_light
                "tinggi" -> android.R.color.holo_red_light
                else -> android.R.color.holo_purple
            }
        }

        fun getTitleStep(title: String): String {
            return when (title) {
                "rendah" -> "Walaupun berisiko rendah, ada beberapa hal yang harus anda perhatikan : "
                "sedang" -> "Beberapa hal yang harus anda perhatikan adalah :"
                "tinggi" -> "Jangan Panik segera hubungi dokter/ hotline Covid-19 untuk mengkonfirmasi, selain itu ada beberapa hal yang harus anda perhatikan :"
                else -> "NotValid"
            }
        }

        fun getDetailStep(title: String): MutableList<String> {
            return when (title) {
                "rendah" -> {
                    mutableListOf(
                            "\nLakukan tindakan pencegahan",
                            "\nHindari berpergian, Di rumah saja",
                            "\nPahami lebih lanjut melalui fitur pencegahan"
                    )
                }
                "sedang" -> {
                    mutableListOf(
                            "\nLakukan tindakan pencegahan penyebaran di mana saja",
                            "\n#DiRumahAja minimal 14 hari",
                            "\nLakukan pengisian penilaian diri secara rutin pada aplikasi ini",
                            "\nGunakan masker apabila batuk / bersin",
                            "\nApabila mengalami gejala - gejala berikut (demam, batuk, nyeri, tenggorokan " +
                                    "/ nafas pendek, pendek / sulit bernapas) segera hubungi dokter / hotline"
                    )
                }
                "tinggi" -> {
                    mutableListOf(
                            "\nJangan berpergian keluar rumah hingga arahan lebih lanjut dari petugas kesehatan",
                            "\nSelalu lakukan tindakan pencegahan penyeberan",
                            "\nHindari kontak fisik dengan orang lain",
                            "\nGunakan masker apabila batuk/bersin",
                            "\nGunakan masker di mana saja"
                    )
                }
                else -> {
                    mutableListOf(
                            "\nNot Valid"
                    )
                }
            }
        }
    }
}