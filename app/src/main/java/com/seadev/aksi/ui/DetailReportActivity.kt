package com.seadev.aksi.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.BulletSpan
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.seadev.aksi.R
import com.seadev.aksi.util.ReportHistoryFormater
import kotlinx.android.synthetic.main.activity_detail_report.*


class DetailReportActivity : AppCompatActivity() {

    companion object {
        var DATA_DETAIL_EXTRA: String = "data_detail_extra"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report)
        supportActionBar?.title = "Kondisi Anda"

        val intent = intent.getStringExtra(DATA_DETAIL_EXTRA)!!.split(" ")

        tvDetailKondisi.text = ReportHistoryFormater.getTitleReport(intent[1])
        tvDetailKondisi.setTextColor(getColor(ReportHistoryFormater.getColorReport(intent[1])))
        contentDetailReport.setCardBackgroundColor(getColor(ReportHistoryFormater.getColorReport(intent[1])))
        Glide.with(this)
                .load(ReportHistoryFormater.getImgReport(intent[1]))
                .into(ivDetailReport)
        tvDetailTitle.text = ReportHistoryFormater.getDescReport(intent[1])
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val texHeader = ReportHistoryFormater.getTitleStep(intent[1])
            val mDataStep: MutableList<String> = ReportHistoryFormater.getDetailStep(intent[1])
            var mSpandable = mutableListOf<SpannableString>()
            mDataStep.forEach {
                val spannable = SpannableString(it)
                spannable.setSpan(
                        BulletSpan(20),
                        /* start index */ 1, /* end index */ spannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                mSpandable.add(spannable)
            }
            if (mDataStep.size == 5) {
                tvDetailStep.text = TextUtils.concat(mSpandable[0], mSpandable[1], mSpandable[2], mSpandable[3], mSpandable[4])
            } else {
                tvDetailStep.text = TextUtils.concat(mSpandable[0], mSpandable[1], mSpandable[2])
            }
            tvHeaderStep.text = texHeader
        }

        when (intent[1]) {
            "rendah" -> {
                btnHub119.visibility = View.GONE
                btnDaftarHotline.visibility = View.GONE
                if (intent[2] == "result") {
                    btnSelesai.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                } else {
                    btnSelesai.visibility = View.GONE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
            "sedang", "tinggi" -> {
                btnHub119.visibility = View.VISIBLE
                btnDaftarHotline.visibility = View.VISIBLE
                btnSelesai.visibility = View.GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        btnDaftarHotline.setOnClickListener {
            startActivity(Intent(this, HotlineActivity::class.java))
            finish()
        }

        btnHub119.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:119")))
        }

        btnSelesai.setOnClickListener {
            finish()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
