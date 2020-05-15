package com.seadev.aksi.rtrw.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.database.*
import com.seadev.aksi.R
import com.seadev.aksi.rtrw.adapter.ReportAdapter
import com.seadev.aksi.rtrw.model.KetuaRt
import kotlinx.android.synthetic.main.activity_report.*
import java.text.SimpleDateFormat
import java.util.*

class ReportActivity : AppCompatActivity() {

    companion object {
        const val REPORT_EXTRA = "report_extra"
        const val REPORT_USER_EXTRA = "report_user_extra"
    }

    private val TAG = "RtRw.ReportActivity"
    private var mValueEventListener: ValueEventListener? = null
    private lateinit var refAsesmen: DatabaseReference
    private var dateList: MutableList<String> = mutableListOf()
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var todayDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        val limit = intent.getIntExtra(REPORT_EXTRA, 0)
        val user = intent.getParcelableExtra<KetuaRt>(REPORT_USER_EXTRA)
        refAsesmen = FirebaseDatabase.getInstance().reference.child("asesmen")

        val today = Date()
        val format = SimpleDateFormat("dd-MM-yyyy")
        todayDate = format.format(today)

        if (limit == 7) {
            supportActionBar?.title = "Laporan Mingguan"
        } else {
            supportActionBar?.title = "Laporan Bulanan"
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadDataTotal(user, limit)
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        reportAdapter = ReportAdapter(this@ReportActivity, dateList)
        rvDateReport.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = reportAdapter
        }
    }

    private fun loadDataTotal(mUser: KetuaRt?, limit: Int?) {
        Log.d(TAG, "loadDataTotal limit: $limit")
        mValueEventListener = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    for (postSnapshot in dataSnapshot.children) {
                        val key = postSnapshot.key
                        if (key!!.split("-")[1] == todayDate.split("-")[1]) {
                            dateList.add(key)
                            reportAdapter.notifyDataSetChanged()
                        }
                    }

                }

            }
            if (limit == 7) {
                refAsesmen.child(mUser?.idDesa!!).limitToFirst(limit!!)
                        .addValueEventListener(mValueEventListener as ValueEventListener)
            } else {
                refAsesmen.child(mUser?.idDesa!!).limitToLast(limit!!)
                        .addValueEventListener(mValueEventListener as ValueEventListener)
            }
        }
    }
}
