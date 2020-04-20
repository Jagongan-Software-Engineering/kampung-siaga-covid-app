package com.seadev.aksi.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.seadev.aksi.R
import com.seadev.aksi.adapter.HistoryReportAdapter
import com.seadev.aksi.room.AsesmenContract.db
import kotlinx.android.synthetic.main.activity_history_report.*

class HistoryReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Riwayat Penilaian Diri"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_history_report)
        rvHistoryReport.setHasFixedSize(true)
        rvHistoryReport.itemAnimator = DefaultItemAnimator()
        rvHistoryReport.adapter = HistoryReportAdapter(this@HistoryReportActivity, db.asesmenDao().dataAsesmen)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
