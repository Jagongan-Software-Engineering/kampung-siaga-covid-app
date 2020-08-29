package com.seadev.aksi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seadev.aksi.R
import com.seadev.aksi.model.Asesmen
import com.seadev.aksi.ui.DetailReportActivity
import com.seadev.aksi.ui.DetailReportActivity.Companion.DATA_DETAIL_EXTRA
import com.seadev.aksi.util.DateFormater
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getColorReport
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getDescReport
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getImgReport
import com.seadev.aksi.util.ReportHistoryFormater.Companion.getTitleReport
import kotlinx.android.synthetic.main.item_asesmen.view.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryReportAdapter(
        val context: Context,
        var listAsesmenReport: List<Asesmen>
) : RecyclerView.Adapter<HistoryReportAdapter.ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asesmen, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int = listAsesmenReport.size


    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bindView(context, listAsesmenReport[position])
    }

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(context: Context, asesmen: Asesmen) {

            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val nowDate = formatter.format(Date())
            val dateTime = formatter.parse(asesmen.date)
            val date = SimpleDateFormat("EEEE dd MMMM yyyy").format(dateTime)
            val listDate = date.split(" ").toTypedArray()
            val isToday = if (nowDate == asesmen.date) 1 else 0
            itemView.tvDateReport.text = "${DateFormater.getHari(listDate[0], isToday)}, ${listDate[1]} ${DateFormater.getBulan(listDate[2])} ${listDate[3]}"

            itemView.tvTitleReport.text = getTitleReport(asesmen.risiko!!)
            itemView.tvDescReport.text = getDescReport(asesmen.risiko!!)
            Glide.with(context)
                    .load(getImgReport(asesmen.risiko!!))
                    .into(itemView.imgReport)
            itemView.imgReport.setBackgroundColor(context.getColor(getColorReport(asesmen.risiko!!)))
            itemView.contentAsesmen.setOnClickListener {
                val intent = Intent(context, DetailReportActivity::class.java)
                intent.putExtra(DATA_DETAIL_EXTRA, getTitleReport(asesmen.risiko!!).toLowerCase() + " home")
                context.startActivity(intent)
            }
        }
    }
}