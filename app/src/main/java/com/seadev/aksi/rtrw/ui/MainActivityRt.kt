package com.seadev.aksi.rtrw.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seadev.aksi.R
import com.seadev.aksi.model.dataapi.IdDesa
import com.seadev.aksi.model.dataapi.IdKecamatan
import com.seadev.aksi.model.dataapi.IdKotaKab
import com.seadev.aksi.model.dataapi.IdProvinsi
import com.seadev.aksi.model.requestbody.ItemIdDesa
import com.seadev.aksi.model.requestbody.ItemIdKecamatan
import com.seadev.aksi.model.requestbody.ItemIdKotaKab
import com.seadev.aksi.model.requestbody.ItemIdProvinsi
import com.seadev.aksi.rest.ApiClientLokasi
import com.seadev.aksi.rest.ApiInterfaceFirebase
import com.seadev.aksi.rtrw.adapter.DailyReportAdapter
import com.seadev.aksi.rtrw.model.Assessment
import com.seadev.aksi.rtrw.model.KetuaRt
import com.seadev.aksi.util.DateFormater
import com.seadev.aksi.util.toTitleCase
import kotlinx.android.synthetic.main.activity_main_rt.*
import kotlinx.android.synthetic.main.item_data_range.*
import kotlinx.android.synthetic.main.item_report.*
import kotlinx.android.synthetic.main.items_status.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivityRt : AppCompatActivity() {

    companion object {
        val KEY_LOCATION = "key_location_main_rt"
    }

    private val TAG = "RtRw.MainActivity"

    private lateinit var database: DatabaseReference
    private lateinit var refUser: DatabaseReference
    private lateinit var refAsesmen: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var mValueEventListener: ValueEventListener? = null

    private lateinit var dailyAdapter: DailyReportAdapter
    private lateinit var mUser: KetuaRt
    private var todayAssessmentReport: MutableList<Assessment> = mutableListOf()

    private val apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceFirebase::class.java)
    private var provinsiList: MutableList<IdProvinsi>? = mutableListOf()
    private var kotaKabList: MutableList<IdKotaKab>? = mutableListOf()
    private var kecamatanList: MutableList<IdKecamatan>? = mutableListOf()
    private var desaList: MutableList<IdDesa>? = mutableListOf()

    private var lokasi: String = ""
    private var rendah = 0
    private var sedang = 0
    private var tinggi = 0
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_rt)

        supportActionBar?.title = "Dashboard Ketua Rt"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("ketuart")
        refUser = FirebaseDatabase.getInstance().reference.child("users")
        refAsesmen = FirebaseDatabase.getInstance().reference.child("asesmen")

        initView()
        initFirebase()

        btnLaporanMingguan.setOnClickListener {
            val intent = Intent(this@MainActivityRt, ReportActivity::class.java)
            intent.putExtra(ReportActivity.REPORT_EXTRA, 7)
            intent.putExtra(ReportActivity.REPORT_USER_EXTRA, mUser)
            startActivity(intent)
        }

        btnLaporanBulanan.setOnClickListener {
            val intent = Intent(this@MainActivityRt, ReportActivity::class.java)
            intent.putExtra(ReportActivity.REPORT_EXTRA, 30)
            intent.putExtra(ReportActivity.REPORT_USER_EXTRA, mUser)
            startActivity(intent)
        }
    }

    private fun initView() {
        dailyAdapter = DailyReportAdapter(this@MainActivityRt, todayAssessmentReport)
        rvDailyReport.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = dailyAdapter
        }
    }

    private fun initFirebase() {
        val user = auth.currentUser
        var users: KetuaRt? = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    for (postSnapshot in dataSnapshot.children) {
                        users = postSnapshot.getValue(KetuaRt::class.java)
                    }
                    loadDataUser(users)
                }

            }
            database.orderByChild("userId").equalTo(user?.uid)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadDataUser(users: KetuaRt?) {
        if (users != null) {
            this.mUser = users

            tvNamaRt.text = mUser.nama
            tvRTRW.text = "RT ${mUser.rtrw!!.split("/")[0]} RW ${mUser.rtrw!!.split("/")[1]}"

            val sharedPref = this@MainActivityRt.getPreferences(Context.MODE_PRIVATE) ?: return
            val dataShareLoc = sharedPref.getString(KEY_LOCATION, null)
            Log.d(TAG, "dataShareLoc: no data")
            if (dataShareLoc == null) loadDataDesa(mUser) else tvLocationRt.text = dataShareLoc
            Glide.with(this).load(R.drawable.ic_location).into(ivICLocationRt)

            when (mUser.status) {
                0 -> {
                    tvStatusRt.apply {
                        text = "Di Proses"
                        setTextColor(this.resources.getColor(android.R.color.holo_orange_dark))
                        backgroundTintList = this.resources.getColorStateList(R.color.colorInProress)
                        layoutDataPakRT.visibility = View.GONE
                        layoutReport.visibility = View.GONE
                        layoutDataRanged.visibility = View.GONE
                        layoutDataRanged.visibility = View.GONE
                        tvLaporanHariIni.visibility = View.GONE
                        rvDailyReport.visibility = View.GONE
                        layoutInProgress.visibility = View.VISIBLE
                    }
                }
                1 -> {
                    tvStatusRt.apply {
                        text = "Valid"
                        setTextColor(resources.getColor(android.R.color.holo_green_dark))
                        backgroundTintList = resources.getColorStateList(R.color.colorValid)
                    }

                    val formatter = SimpleDateFormat("dd-MM-yyyy")
                    val nowDate = formatter.format(Date())

                    loadTodayReport(mUser.idDesa, nowDate)
                    loadDataWarga(mUser.idDesa)
                }
                else -> {
                }
            }


        } else {
            Log.d(TAG, "Users: no data")
        }
    }

    private fun loadTodayReport(idDesa: String?, todayDate: String?) {
        mValueEventListener = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    for (postSnapshot in dataSnapshot.children) {
                        Log.d(TAG, "dataSnapshot.children.count: ${dataSnapshot.children.count()}")
                        val asesment = postSnapshot.getValue(Assessment::class.java)
                        updateDataRanged(asesment)
                        todayAssessmentReport.add(asesment!!)
                        dailyAdapter.notifyDataSetChanged()
                        if (total == dataSnapshot.children.count()) initDataRanged()
                    }

                }

            }
            refAsesmen.child(idDesa!!).child(todayDate!!)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }

    private fun updateDataRanged(asesment: Assessment?) {
        when (asesment?.risiko) {
            "rendah" -> rendah++
            "sedang" -> sedang++
            "tinggi" -> tinggi++
            else -> -1
        }
        total++
    }

    private fun loadDataWarga(idDesa: String?) {
        mValueEventListener = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    tvTotalPerson.text = "${dataSnapshot.children.count()} Warga"
                }

            }
            refUser.orderByChild("idDesa").equalTo(idDesa)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }

    private fun initDataRanged() {
        var pieEntry = listOf(
                PieEntry(rendah.toFloat(), "Rendah"),
                PieEntry(sedang.toFloat(), "Sedang"),
                PieEntry(tinggi.toFloat(), "Tinggi")
        )

        val colorList = listOf(
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light),
                resources.getColor(android.R.color.holo_red_light)
        )

        val pieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.apply {
            colors = colorList
            setDrawValues(false)
        }

        val pieData = PieData(pieDataSet)
        pieCartRanged.apply {
            data = pieData
            description = null
            holeRadius = 70f
            setDrawEntryLabels(false)
            animateY(1500, Easing.EaseInOutQuart)
            invalidate()
        }

        tvRangeRisikoRendah.text = rendah.toString()
        tvRangeRisikoSedang.text = sedang.toString()
        tvRangeRisikoTinggi.text = tinggi.toString()

        layoutCasesRanged.visibility = View.VISIBLE
        tvRangeWarga.text = total.toString()

        val formatter = SimpleDateFormat("EEEE dd MMMM yyyy")
        val nowDate = formatter.format(Date())
        val listDate = nowDate.split(" ")
        val date = "${DateFormater.getHari(listDate[0], 1)}, ${listDate[1]} ${DateFormater.getBulan(listDate[2])} ${listDate[3]}"

        tvTodayDateReport.text = date
    }

    private fun loadDataProvinsi(idProvinsi: KetuaRt) {
        provinsiList = ArrayList()
        val call = apiServiceLokasi.dataProvinsi
        call.enqueue(object : Callback<ItemIdProvinsi?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<ItemIdProvinsi?>, response: Response<ItemIdProvinsi?>) {
                assert(response.body() != null)
                provinsiList = response.body()!!.provinsiList
                provinsiList?.forEach {
                    if (idProvinsi.idProvinsi == it.id) {
                        lokasi += ", ${toTitleCase(it.name)}"
                        tvLocationRt.text = lokasi
                        val sharedPref = this@MainActivityRt.getPreferences(Context.MODE_PRIVATE)
                                ?: return
                        with(sharedPref.edit()) {
                            putString(KEY_LOCATION, lokasi)
                            commit()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ItemIdProvinsi?>, t: Throwable) {
                Log.d("Register Activity", "error: " + t.message)
            }
        })
    }

    private fun loadDataKotaKab(users: KetuaRt) {
        kotaKabList = ArrayList()
        val call = apiServiceLokasi.dataKotaKab
        call.enqueue(object : Callback<ItemIdKotaKab?> {
            override fun onResponse(call: Call<ItemIdKotaKab?>, response: Response<ItemIdKotaKab?>) {
                assert(response.body() != null)
                kotaKabList = response.body()!!.idKotaKabList
                kotaKabList?.forEach {
                    if (users.idProvinsi == it.idProvinsi) {
                        if (users.idKotaKab == it.id) {
                            lokasi += "\n${toTitleCase(it.name)}"
                            Log.d(TAG, "LokasiRT: $lokasi")
                            loadDataProvinsi(users)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ItemIdKotaKab?>, t: Throwable) {}
        })
    }

    private fun loadDataKecamatan(users: KetuaRt) {
        kecamatanList = ArrayList()
        val call = apiServiceLokasi.dataKecamatan
        call.enqueue(object : Callback<ItemIdKecamatan?> {
            override fun onResponse(call: Call<ItemIdKecamatan?>, response: Response<ItemIdKecamatan?>) {
                assert(response.body() != null)
                kecamatanList = response.body()!!.idKecamatanList
                kecamatanList?.forEach {
                    if (users.idKotaKab == it.idKotaKab) {
                        if (users.idKacamatan == it.id) {
                            lokasi += ", ${toTitleCase(it.name)}"
                            Log.d(TAG, "LokasiRT: $lokasi")
                            loadDataKotaKab(users)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ItemIdKecamatan?>, t: Throwable) {}
        })
    }

    private fun loadDataDesa(users: KetuaRt) {
        desaList = ArrayList()
        val call = apiServiceLokasi.dataDesa
        call.enqueue(object : Callback<ItemIdDesa?> {
            override fun onResponse(call: Call<ItemIdDesa?>, response: Response<ItemIdDesa?>) {
                assert(response.body() != null)
                desaList = response.body()!!.idDesaList
                desaList?.forEach {
                    if (users.idKacamatan == it.idKecamatan) {
                        if (users.idDesa == it.id) {
                            lokasi += "${toTitleCase(it.name)}"
                            Log.d(TAG, "LokasiRT: $lokasi")
                            loadDataKecamatan(users)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ItemIdDesa?>, t: Throwable) {}
        })
    }
}
