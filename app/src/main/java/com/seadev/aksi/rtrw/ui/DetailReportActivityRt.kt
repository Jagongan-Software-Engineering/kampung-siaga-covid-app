package com.seadev.aksi.rtrw.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seadev.aksi.R
import com.seadev.aksi.model.Users
import com.seadev.aksi.rtrw.adapter.DailyReportAdapter
import com.seadev.aksi.rtrw.model.Assessment
import kotlinx.android.synthetic.main.activity_detail_report_rt.*

class DetailReportActivityRt : AppCompatActivity() {

    companion object {
        val DETAIL_REPORT_TITLE_EXTRA = "detail_report_title_extra"
        val DETAIL_REPORT_EXTRA = "detail_report_extra"
    }

    private val TAG = "DetailReportActivity"
    private lateinit var database: DatabaseReference
    private lateinit var refAsesmen: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var mValueEventListener: ValueEventListener? = null
    private lateinit var nowDate: String
    private lateinit var dailyAdapter: DailyReportAdapter
    private var todayAssessmentReport: MutableList<Assessment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report_rt)

        supportActionBar?.title = intent.getStringExtra(DETAIL_REPORT_TITLE_EXTRA)!!
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nowDate = intent.getStringExtra(DETAIL_REPORT_EXTRA)!!
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("ketuart")
        refAsesmen = FirebaseDatabase.getInstance().reference.child("asesmen")

        initView()
        initFirebase()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        dailyAdapter = DailyReportAdapter(this@DetailReportActivityRt, todayAssessmentReport)
        rvDetailReport.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = dailyAdapter
        }
    }

    private fun initFirebase() {
        val user = auth.currentUser
        var users: Users? = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    for (postSnapshot in dataSnapshot.children) {
                        users = postSnapshot.getValue(Users::class.java)
                    }
                    loadDataUser(users)
                }

            }
            database.orderByChild("userId").equalTo(user?.uid)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }

    private fun loadDataUser(users: Users?) {
        if (users != null) {
            loadTodayReport(users.idDesa!!, nowDate)
        } else {
            Log.d(TAG, "Users: no data")
        }
    }

    private fun loadTodayReport(idDesa: String?, nowDate: String?) {
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
                        todayAssessmentReport.add(asesment!!)
                        dailyAdapter.notifyDataSetChanged()
                    }

                }

            }
            refAsesmen.child(idDesa!!).child(nowDate!!)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }
}
