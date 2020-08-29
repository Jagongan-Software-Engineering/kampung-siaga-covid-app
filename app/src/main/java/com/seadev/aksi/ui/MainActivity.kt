package com.seadev.aksi.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import butterknife.ButterKnife
import coil.api.load
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.messaging.FirebaseMessaging
import com.seadev.aksi.BuildConfig
import com.seadev.aksi.R
import com.seadev.aksi.databinding.ActivityMainBinding
import com.seadev.aksi.model.dataapi.DataHarian
import com.seadev.aksi.model.requestbody.ItemDataHarian
import com.seadev.aksi.rest.ApiClientNasional
import com.seadev.aksi.rest.ApiInterfaceNasional
import com.seadev.aksi.room.AsesmenContract
import com.seadev.aksi.room.AssesmenDatabase
import com.seadev.aksi.ui.DetailReportActivity
import com.seadev.aksi.ui.DetailReportActivity.Companion.DATA_DETAIL_EXTRA
import com.seadev.aksi.util.DateFormater.Companion.getBulan
import com.seadev.aksi.util.DateFormater.Companion.getHari
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getColorReport
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getDescReport
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getImgReport
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getTitleReport
import com.seadev.aksi.util.UiHelper.getPercentage
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val apiServiceNasional = ApiClientNasional.getClientNasional().create(ApiInterfaceNasional::class.java)
    lateinit var binding: ActivityMainBinding
    private var dataHarianList: List<DataHarian>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ButterKnife.bind(this)
        activity = this
        initView()
        initFirebase()
        loadDataNasional()
        fechDatabaseAsesmen()
        binding.apply {
            refreshLayout.setOnRefreshListener {
                initView()
                initFirebase()
                loadDataNasional()
                fechDatabaseAsesmen()
                refreshLayout.isRefreshing = false
            }
            layoutCampaign.setOnClickListener {
                startActivity(Intent(this@MainActivity, SurveyActivity::class.java))
            }
            layoutHistoryReport.tvMoreReport.setOnClickListener {
                startActivity(Intent(this@MainActivity, HistoryReportActivity::class.java))
            }
            layoutNavigation.apply {
                btnDataCovid.setOnClickListener {
                    startActivity(Intent(this@MainActivity, DataProvinsiActivity::class.java))
                }
                btnLapor.setOnClickListener { startActivity(Intent(this@MainActivity, SurveyActivity::class.java)) }
                btnHotline.setOnClickListener { startActivity(Intent(this@MainActivity, HotlineActivity::class.java)) }
                btnPencegahan.setOnClickListener { startActivity(Intent(this@MainActivity, PreventionActivity::class.java)) }
            }

            layoutHistoryReport.layoutAssesment.apply {
                contentAsesmen.setOnClickListener {
                    val intent = Intent(this@MainActivity, DetailReportActivity::class.java)
                    intent.putExtra(DATA_DETAIL_EXTRA, tvTitleReport.text.toString().toLowerCase() + " home")
                    startActivity(intent)
                }
            }

            iconUser.setOnClickListener { startActivity(Intent(this@MainActivity, ProfileActivity::class.java)) }
        }


    }

    private fun initView() {
        binding.apply {
            layoutNavigation.ivIcDataCovid.load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_data_covid)) {
                crossfade(true)
            }
            layoutNavigation.ivIcPenilaianDiri.load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_assesment)) {
                crossfade(true)
            }
            layoutNavigation.ivIcHoltine.load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_hotline)) {
                crossfade(true)
            }
            layoutNavigation.ivIcPencegahan.load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_prevention)) {
                crossfade(true)
            }
            ivCampaign.load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_img_campaign)) {
                crossfade(true)
            }
        }
    }

    private fun initFirebase() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task: Task<InstanceIdResult> ->
            if (!task.isSuccessful) {
                Log.w("TAG", "getInstanceId failed", task.exception)
                return@addOnCompleteListener
            }
            // Get new Instance ID token
            val token = task.result!!.token
            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("TAG", msg)
        }
        FirebaseMessaging.getInstance().subscribeToTopic("daily")
                .addOnCompleteListener { task: Task<Void?> ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "subscribe daily failed", task.exception)
                    }
                    val msg = "subscribed daily"
                    Log.w("TAG", msg)
                }
        FirebaseMessaging.getInstance().subscribeToTopic("update")
                .addOnCompleteListener { task: Task<Void?> ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "subscribe update failed", task.exception)
                    }
                    val msg = "subscribed update"
                    Log.w("TAG", msg)
                }
    }

    private fun loadDataNasional() {
        val call = apiServiceNasional.dataHarian
        call.enqueue(object : Callback<ItemDataHarian?> {
            override fun onResponse(call: Call<ItemDataHarian?>, response: Response<ItemDataHarian?>) {
                val dataHarianList = response.body()!!.dataHarianList
                var index = 0
                for (dataHarian in dataHarianList) {
                    val date1 = Date(dataHarian.attributes.tgl)
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    val mDate = formatter.format(date1)
                    val now = formatter.format(Date())
                    if (now == mDate) {
                        Log.d("MainActivity", "mDate: $mDate")
                        if (dataHarian.attributes.kasusTotal != 0) {
                            index++
                        }
                        break
                    } else {
                        index++
                    }
                }
                val dataHarian = dataHarianList.get(index - 1)
                setDataHarian(dataHarian)
            }

            override fun onFailure(call: Call<ItemDataHarian?>, t: Throwable) {
                Log.d("MainActivity", "error: $t")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setDataHarian(dataHarian: DataHarian) {
        val pieEntries: MutableList<PieEntry> = ArrayList()
        val colors: MutableList<Int> = ArrayList()
        colors.add(resources.getColor(android.R.color.holo_green_light))
        colors.add(resources.getColor(android.R.color.holo_orange_light))
        colors.add(resources.getColor(android.R.color.holo_red_light))
        pieEntries.add(PieEntry(dataHarian.attributes.sembuh.toFloat(), "Sembuh"))
        pieEntries.add(PieEntry(dataHarian.attributes.dalamPerawatan.toFloat(), "Dirawat"))
        pieEntries.add(PieEntry(dataHarian.attributes.meninggal.toFloat(), "Meninggal"))
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.colors = colors
        pieDataSet.setDrawValues(false)
        val pieData = PieData(pieDataSet)

        binding.layoutOverview.apply {
            pieChart.apply {
                data = pieData
                description = null
                holeRadius = 70f
                setDrawEntryLabels(false)
                animateY(1500, Easing.EaseInOutQuart)
                invalidate()
            }

            layoutCase.visibility = View.VISIBLE
            tvKasus.text = dataHarian.attributes.kasusTotal.toString()
            tvPersenSembuh.text = buildString {
                append(getPercentage(dataHarian.attributes.persenSembuh.toInt()))
                append("%")
            }
            tvPersenMeninggal.text = buildString {
                append(getPercentage(dataHarian.attributes.persenMeninggal.toInt()))
                append("%")
            }
            tvPersenDirawat.text = buildString {
                append(getPercentage(dataHarian.attributes.persenPerawatan.toInt()))
                append("%")
            }
        }

        binding.layoutMainData.apply {
            tvConfirm.text = dataHarian.attributes.kasusTotal.toString()
            tvRecover.text = dataHarian.attributes.sembuh.toString()
            tvInCare.text = dataHarian.attributes.dalamPerawatan.toString()
            tvDied.text = dataHarian.attributes.meninggal.toString()

            tvNewConfirm.text = "(+" + dataHarian.attributes.kasusBaru + ")"
            tvNewRecover.text = "(+" + dataHarian.attributes.sembuhBaru + ")"
            tvNewInCare.text = "(+" + dataHarian.attributes.dirawatBaru + ")"
            tvNewDied.text = "(+" + dataHarian.attributes.meninggalBaru + ")"
        }
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    private fun fechDatabaseAsesmen() {
        AsesmenContract.db = Room.databaseBuilder(Objects.requireNonNull(this),
                AssesmenDatabase::class.java, "db_asesmen").allowMainThreadQueries().build()
        val asesmenList = AsesmenContract.db.asesmenDao().dataAsesmen
        binding.layoutHistoryReport.layoutAssesment.apply {
            if (!asesmenList.isEmpty()) {
                binding.layoutHistoryReport.contentMain.visibility = View.VISIBLE
                val (date1, _, _, _, risiko) = asesmenList[asesmenList.size - 1]
                val formatter = SimpleDateFormat("dd-MM-yyyy")
                val nowDate = formatter.format(Date())
                val dateTime: Date?
                try {
                    dateTime = formatter.parse(date1)
                    assert(dateTime != null)
                    val date = SimpleDateFormat("EEEE dd MMMM yyyy").format(dateTime)
                    val listDate = date.split(" ".toRegex()).toTypedArray()
                    val isToday = if (nowDate == date1) 1 else 0
                    tvDateReport.text = getHari(listDate[0], isToday) + ", " + listDate[1] + " " + getBulan(listDate[2]) + " " + listDate[3]
                } catch (e: ParseException) {
                    Log.d("MainActivity", "fechDatabaseAsesmen: $e")
                }
                tvTitleReport.text = getTitleReport(risiko!!)
                tvDescReport.text = getDescReport(risiko)
                imgReport.load(getImgReport(risiko)) {
                    crossfade(true)
                }
                imgReport.setBackgroundColor(resources.getColor(getColorReport(risiko)))
                if (nowDate == date1) {
                    layoutCampaign!!.visibility = View.GONE
                } else {
                    layoutCampaign!!.visibility = View.VISIBLE
                }
            } else {
                binding.layoutHistoryReport.contentMain.visibility = View.GONE
                Log.d("MainActivity", "room-size: " + asesmenList.size)
            }
        }
    }

    companion object {
        var activity: Activity? = null
    }
}