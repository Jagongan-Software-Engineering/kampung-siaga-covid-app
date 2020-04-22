package com.seadev.aksirtrw.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.seadev.aksi.util.DateFormater
import com.seadev.aksirtrw.R
import com.seadev.aksirtrw.ui.DetailReportActivity
import kotlinx.android.synthetic.main.item_tanggal.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReportAdapter(
        val context: Context,
        val dateList: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tanggal, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int = dateList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dateTime = LocalDate.parse(dateList[position], formatter)
        val formatter2 = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")
        val finalDate = dateTime.format(formatter2)
        val listDate = finalDate.split(" ")

        val nowDate = LocalDateTime.now().format(formatter)
        val isToday = if (nowDate == dateList[position]) 1 else 0

        val date = "${DateFormater.getHari(listDate[0], isToday)}\n${listDate[1]} ${DateFormater.getBulan(listDate[2])} ${listDate[3]}"
        holder.view.tvDateReport.text = date

        holder.view.contentReport.setOnClickListener {
            val mIntent = Intent(context, DetailReportActivity::class.java)
            mIntent.putExtra(DetailReportActivity.DETAIL_REPORT_EXTRA, dateList[position])
            mIntent.putExtra(DetailReportActivity.DETAIL_REPORT_TITLE_EXTRA, date)
            context.startActivity(mIntent)
        }
    }

    class ReportViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}