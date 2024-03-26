package com.seadev.aksi.model.utils

import androidx.compose.runtime.Composable
import com.seadev.aksi.model.BuildConfig
import com.seadev.aksi.model.ui.theme.AksiColor

data class Prevention(
    val title: String,
    val desc: String,
    val image: String
)

object DataUtils {
    object Prevention {
        val Header = Prevention(
            "Mari Pelajari tentang Covid-19",
            "Kenali dan pelajari lebih jauh cara pencegahan mengenai Coronavirus dan Covid-19",
            BuildConfig.RESOURCE_URL + "1311c256-bb78-4e01-914a-633951e69aa1"
        )
        val mainPrevention = listOf(
            Prevention(
                "Tentang Covid-19", "",
                BuildConfig.RESOURCE_URL + "72d44f97-5520-463f-9743-957b51abf852"
            ),
            Prevention(
                "Gejala Covid-19", "",
                BuildConfig.RESOURCE_URL + "209d9873-f118-4392-a9e8-ca17e0c7404e"
            ),
            Prevention(
                "Mencegah Covid-19", "",
                BuildConfig.RESOURCE_URL + "2fbeb0f3-f626-4709-944a-25cf03577217"
            ),
            Prevention(
                "Hal yang tidak boleh dilakukan", "",
                BuildConfig.RESOURCE_URL + "ad805b97-72ad-48aa-b91d-1473daa1b3a4"
            ),
        )
    }

    object PreventionStep {
        val about = listOf(
            Prevention(
                "Apa itu Covid-19",
                "Virus corona paling terbaru yang ditemukan adalah virus corona COVID-19. Virus ini termasuk penyakit menular dan baru ditemukan di Wuhan, China pada Desember 2019 yang kemudian menjadi wabah.",
                BuildConfig.RESOURCE_URL + "272f0281-3d93-41fe-a62e-0c6c5b80013c"
            ),
            Prevention(
                "Apa itu Covid-19",
                "Coronavirus Disease 2019 atau COVID-19 adalah penyakit baru yang dapat menyebabkan gangguan pernapasan dan radang paru. Penyakit ini disebabkan oleh infeksi Severe Acute Respiratory Syndrome Coronavirus 2 (SARS-CoV-2). Gejala klinis yang muncul beragam, mulai dari seperti gejala flu biasa (batuk, pilek, nyeri tenggorok, nyeri otot, nyeri kepala) sampai yang berkomplikasi berat (pneumonia atau sepsis).",
                BuildConfig.RESOURCE_URL + "94af3709-4804-4858-b98e-d26d96e15774"
            ),
            Prevention(
                "Apa itu Covid-19",
                "Pada manusia, beberapa tipe coronavirus dapat menyebabkan infeksi pernapasan mulai dari flu hingga penyakit yang lebih berat, seperti MERS (Middle East Respiratory Syndrome) dan SARS (Severe Acute Respiratory Syndrome).",
                BuildConfig.RESOURCE_URL + "b5fdd107-c33e-4132-bddf-7d7886d6efc9"
            )
        )
        val symptom = listOf(
            Prevention(
                "Batuk dan Nyeri Tenggorokan",
                "Batuk yang dimaksudkan dalam kategori kemungkinan terinfeksi virus SARS-CoV-2 adalah batuk yang terlampau sering dalam satu jam. Umumnya batuk kering dan terjadi terus-menerus secara lebih dari 3 kali 24 jam.",
                BuildConfig.RESOURCE_URL + "0affccba-d79b-4546-98ef-2fa8f6a5fa4a"
            ),
            Prevention(
                "Demam suhu tinggi / Ada riwayat demam",
                "Menurut Pokja Infeksi Pengurus Pusat Perhimpunan Dokter Paru Indonesia, dr Erlina Burhan SpP(K) MSc PhD, sejak awal wabah Covid-19 ini melanda Wuhan, China gejala yang paling umum dan banyak dialami mencapai 98 persen oleh pasiennya adalah demam tinggi di atas 38,5 derajat Celsius.",
                BuildConfig.RESOURCE_URL + "9c002459-0162-4883-8b47-2906849ac33e"
            ),
            Prevention(
                "Sesak napas",
                "Karena sama-sama disebabkan oleh virus yang menyerang saluran pernapasan, gejala awal COVID-19 juga bisa mirip dengan flu biasa, yaitu pilek, hidung tersumbat, sakit kepala, dan sakit tenggorokan.",
                BuildConfig.RESOURCE_URL + "1bb40fda-761f-4607-9f49-e2063c84ef1b"
            ),
        )
        val prevent = listOf(
            Prevention(
                "#DiRumahAja",
                "Tetap tinggal di rumah. Bekerja dari rumah, belajar dari rumah, dan beribadah di rumah.",
                BuildConfig.RESOURCE_URL + "74f83e5b-a756-4cb6-a5ce-e04a1fe54f94"
            ),
            Prevention(
                "Jangan Dekat-dekat",
                "Jangan Berada dekat dengan orang yang sedang sakit, batuk atau bersin.",
                BuildConfig.RESOURCE_URL + "30e7e8eb-0a6e-4019-a911-1f7f4632d7fa"
            ),
            Prevention(
                "Jangan Asal Sentuh",
                "Jangan Menyentuh mata, hidung, atau mulut dengan telapak tangan, karena virus Covid-19 lebih mudah menular dengan kontak fisik.",
                BuildConfig.RESOURCE_URL + "9be33f12-dc88-4646-943c-e7d9323c7945"
            ),
            Prevention(
                "Jangan Menimbun masker",
                "Jangan bersikap egois dengan cara menimbun masker, karena kita semua membutuhkan masker agar tidak tertular virus Covid-19 serta sebagai cara pencegahan.",
                BuildConfig.RESOURCE_URL + "25a1bfa6-124d-45d3-b6a8-09084228bb22"
            ),
            Prevention(
                "Jangan Bepergian ke luar rumah saat sedang sakit",
                "Sebaiknya jangan berpergian dulu keluar rumah dalam keaadan sakit, untuk mengurangi potensi tertular dari virus Covid-19.",
                BuildConfig.RESOURCE_URL + "a02f33ed-738c-44f9-be7d-25c4156ecb64"
            ),
            Prevention(
                "Pola Hidup Sehat",
                "Terapkan pola hidup sehat dengan makanan bergizi dan olahraga.",
                BuildConfig.RESOURCE_URL + "be3925ad-ad96-4556-877a-bce0dd37691f"
            ),
        )
        val notAllowed = listOf(
            Prevention(
                "Jangan Dekat-dekat",
                "Jangan Berada dekat dengan orang yang sedang sakit, batuk atau bersin.",
                BuildConfig.RESOURCE_URL + "f04b11fc-7b83-46a1-8e51-19840ecf80a8"
            ),
            Prevention(
                "Jangan Asal Sentuh",
                "Jangan Menyentuh mata, hidung, atau mulut dengan telapak tangan, karena virus Covid-19 lebih mudah menular dengan kontak fisik.",
                BuildConfig.RESOURCE_URL + "be5bb5a0-0604-4f31-b014-d188b3bbfd4f"
            ),
            Prevention(
                "Jangan Menimbun masker",
                "Jangan bersikap egois dengan cara menimbun masker, karena kita semua membutuhkan masker agar tidak tertular virus Covid-19 serta sebagai cara pencegahan.",
                BuildConfig.RESOURCE_URL + "9ce00671-23c3-41e4-bf31-b30f5888bc20"
            ),
            Prevention(
                "Jangan Bepergian ke luar rumah saat sedang sakit",
                "Sebaiknya jangan berpergian dulu keluar rumah dalam keaadan sakit, untuk mengurangi potensi tertular dari virus Covid-19.",
                BuildConfig.RESOURCE_URL + "469ffe33-dac5-4b1e-995f-699cfa80edb7"
            )
        )
    }

    object ProfileMenu {
        val mainMenu = listOf(
            Pair("Riwayat Penilaian Diri", ImageUtils.NavProfileIcon.IconNext),
            Pair("Berikan Umpan Balik", ImageUtils.NavProfileIcon.IconFeedBack),
            Pair("Keluar", ImageUtils.NavProfileIcon.IconLogOut),
        )
    }

    object SelfAssessmentQuestion {
        val validRespond = listOf(1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1)
        val initialRespond = listOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)

        fun List<Int>.getAssessmentResult(): AssessmentResult {
            val result = this.filterIndexed { index, i -> i == validRespond[index] }.size
            return when (result) {
                in 0..7 -> AssessmentResult.LOW
                in 8..14 -> AssessmentResult.MID
                in 15..21 -> AssessmentResult.HIGH
                else -> AssessmentResult.NONE
            }
        }

        enum class AssessmentResult {
            LOW, MID, HIGH, NONE
        }

        val AssessmentResult.imageAssessmentResult get() = when(this){
            AssessmentResult.LOW -> ImageUtils.AssessmentResult.IconLowRisk
            AssessmentResult.MID -> ImageUtils.AssessmentResult.IconMidRisk
            AssessmentResult.HIGH -> ImageUtils.AssessmentResult.IconHighRisk
            else -> ""
        }


        val AssessmentResult.colorAssessmentResult @Composable get() = when (this) {
            AssessmentResult.LOW -> AksiColor.Green
            AssessmentResult.MID -> AksiColor.Yellow
            AssessmentResult.HIGH -> AksiColor.Red
            else  -> AksiColor.Text
        }

        val AssessmentResult.resultTitle get() = when (this) {
            AssessmentResult.LOW -> "Risiko Rendah"
            AssessmentResult.MID -> "Risiko Sedang"
            AssessmentResult.HIGH -> "Risiko Tinggi"
            else  -> ""
        }

        val AssessmentResult.resultStatusDesc get() = when (this) {
            AssessmentResult.LOW -> "Anda tidak memiliki gejala maupun riwayat paparan terkait Covid-19"
            AssessmentResult.MID -> "Anda berkemungkinan / berisiko terpapar Covid-19"
            AssessmentResult.HIGH -> "Ada memiliki gejala dan riwayat paparan terkait Covid-19"
            else  -> ""
        }

        val AssessmentResult.resultStepInstruction get() = when (this) {
            AssessmentResult.LOW -> "Walaupun berisiko rendah, ada beberapa hal yang harus anda perhatikan :"
            AssessmentResult.MID -> "Beberapa hal yang harus Anda perhatikan adalah:"
            AssessmentResult.HIGH -> "Jangan panik dan segera hubungi dokter / hotline Covid-19 untuk megkonfirmasi, selain itu ada beberapa hal yang harus Anda perhatikan:"
            else  -> ""
        }

        val AssessmentResult.resultListInstruction get() = when (this) {
            AssessmentResult.LOW -> listOf(
                "Lakukan tindakan pencegahan",
                "Hindari bepergian, Di rumah saja",
                "Pahami lebih lanjut melalui fitur pencegahan"
            )
            AssessmentResult.MID -> listOf(
                "Lakukan tindakan pencegahan penyebaran di mana saja",
                "#DiRumahAja minimal 14 hari",
                "Lakukan pengisian penilaian diri secara rutin pada aplikasi ini",
                "Gunakan masker apabila batuk / bersin",
                "Apabila mengalami gejala-gejala berikut (demam, batuk, nyeri tenggorokan / nafas pendek, sulit bernfas) segera hubungi dokter / hotline"
            )
            AssessmentResult.HIGH -> listOf(
                "Jangan bepergian keluar rumah hingga arahan lebih lanjut dari petugas kesehatan",
                "Selalu lakukan tindakan pencegahan penyebaran",
                "Hindari kontak fisik dengan orang lain",
                "Gunakan masker apabila batuk / bersin",
                "Gunakan masker di mana saja"
            )
            else  -> listOf()
        }

        data class Question(
            val title: String,
            val desc: String,
        )

        fun getStep(step: Int) = listOf(
            Question("Langkah $step", "Pertanyaan potensi tertular di luar rumah"),
            Question("Langkah $step", "Pertanyaan potensi tertular di dalam rumah"),
            Question("Langkah $step", "Pertanyaan daya tahan tubuh")
        )[step - 1]

        val step1 = listOf(
            getStep(1), // 0
            Question("Apakah Anda pergi keluar rumah hari ini?", ""),
            Question(
                "Apakah Anda menggunakan transportasi umum seperti transportasi online, angkot, bus, taksi, kereta api?",
                ""
            ),
            Question("Apakah Anda memakai masker pada saat berkumpul dengan orang lain?", ""),
            Question("Apakah Anda berjabat tangan dengan orang lain?", ""),
            Question(
                "Apakah Anda membersihkan tangan dengan hand sanitizer atau tissue basah sebelum berkendara?",
                ""
            ),
            Question("Apakah Anda menyentuh benda yang juga disentuh orang lain seperti uang?", ""),
            Question(
                "Apakah Anda menjaga jarak 1,5 meter dengan orang lain ketika belanja, bekerja, belajar, dan beribadah?",
                ""
            ),
            Question("Apakah Anda makan di luar rumah seperti di warung atau restoran?", ""),
            Question(
                "Apakah Anda mebersihkan tangan dengan hand sanitizer, tissue basah atau sabun setelah tiba di tujuan?",
                ""
            ),
            Question("Apakah Anda berada di wilayah kelurahan tempat pasien tertular?", ""), // 10
        )
        val step2 = listOf(
            getStep(2), // 11
            Question(
                "Apakah Anda pasang hand sanitizer di depan pintu masuk, untuk bersihkan tangan sebelum pegang gagang (handle) pintu masuk rumah?",
                ""
            ),
            Question("Apakah Anda mencuci tangan dengan sabun setelah tiba di rumah?", ""),
            Question(
                "Apakah Anda menyediakan tissue basah, antiseptic, masker, dan sabun antiseptic bagi keluarga di rumah?",
                ""
            ),
            Question(
                "Apakah Anda segera merendam baju dan celana bekas pakai di luar rumah ke dalam air panas atau sabun?",
                ""
            ),
            Question("Apakah Anda segera mandi keramas setelah Anda tiba di rumah?", ""),
            Question(
                "Apakah Anda mensosialisasikan check list penilaian resiko pribadi ini kepada keluarga di rumah?",
                ""
            ), // 17
        )
        val step3 = listOf(
            getStep(3), // 18
            Question("Apakah Anda dalam sehari terkena cahaya matahari minimal 15 menit?", ""),
            Question("Apakah Anda jalan kaki atau berolahraga minimal 30 menit setiap hari?", ""),
            Question("Apakah Anda minum vitamin C dan E, dan cukup tidur?", ""),
            Question("Apakah Anda berusia di atas 60 tahun?", ""),
            Question(
                "Apakah Anda mempunyai penyakit jantung, diabetes atau gangguan pernafasan kronik?",
                ""
            ), // 23
        )
    }
}