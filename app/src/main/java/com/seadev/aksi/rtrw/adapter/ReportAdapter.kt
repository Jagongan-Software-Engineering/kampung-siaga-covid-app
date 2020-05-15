package com.seadev.aksi.rtrw.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seadev.aksi.R
import com.seadev.aksi.rtrw.ui.DetailReportActivityRt
import com.seadev.aksi.util.DateFormater
import kotlinx.android.synthetic.main.item_tanggal.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReportAdapter(
        val context: Context,
        val dateList: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tanggal, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int = dateList.size

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val dateTime = formatter.parse(dateList[position])
        val formatter2 = SimpleDateFormat("EEEE dd MMMM yyyy")
        val finalDate = formatter2.format(dateTime)
        val listDate = finalDate.split(" ")

        val nowDate = formatter.format(Date())
        val isToday = if (nowDate == dateList[position]) 1 else 0

        val date = "${DateFormater.getHari(listDate[0], isToday)}\n${listDate[1]} ${DateFormater.getBulan(listDate[2])} ${listDate[3]}"
        holder.view.tvDateReport.text = date

        holder.view.contentReport.setOnClickListener {
            val mIntent = Intent(context, DetailReportActivityRt::class.java)
            mIntent.putExtra(DetailReportActivityRt.DETAIL_REPORT_EXTRA, dateList[position])
            mIntent.putExtra(DetailReportActivityRt.DETAIL_REPORT_TITLE_EXTRA, date)
            context.startActivity(mIntent)
        }
    }

    class ReportViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}