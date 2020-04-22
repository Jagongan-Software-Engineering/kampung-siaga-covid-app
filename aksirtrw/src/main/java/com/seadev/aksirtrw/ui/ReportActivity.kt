package com.seadev.aksirtrw.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.database.*
import com.seadev.aksirtrw.R
import com.seadev.aksirtrw.adapter.ReportAdapter
import com.seadev.aksirtrw.model.KetuaRt
import kotlinx.android.synthetic.main.activity_report.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        val limit = intent.getIntExtra(REPORT_EXTRA, 0)
        val user = intent.getParcelableExtra<KetuaRt>(REPORT_USER_EXTRA)
        refAsesmen = FirebaseDatabase.getInstance().reference.child("asesmen")

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
                        dateList.add(key!!)
                        reportAdapter.notifyDataSetChanged()
                    }

                }

            }
            refAsesmen.child(mUser?.idDesa!!).limitToLast(limit!!)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }
}
