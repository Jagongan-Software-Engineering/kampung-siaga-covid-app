package com.seadev.kampungsiagacovid.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seadev.kampungsiagacovid.R
import com.seadev.kampungsiagacovid.model.Asesmen
import com.seadev.kampungsiagacovid.ui.DetailReportActivity
import com.seadev.kampungsiagacovid.ui.DetailReportActivity.Companion.DATA_DETAIL_EXTRA
import com.seadev.kampungsiagacovid.util.DateFormater
import com.seadev.kampungsiagacovid.util.ReportHistoryFormater.Companion.getColorReport
import com.seadev.kampungsiagacovid.util.ReportHistoryFormater.Companion.getDescReport
import com.seadev.kampungsiagacovid.util.ReportHistoryFormater.Companion.getImgReport
import com.seadev.kampungsiagacovid.util.ReportHistoryFormater.Companion.getTitleReport
import kotlinx.android.synthetic.main.item_asesmen.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryReportAdapter(
        val context: Context,
        var listAsesmenReport: List<Asesmen>
) : RecyclerView.Adapter<HistoryReportAdapter.ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asesmen, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int = listAsesmenReport.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bindView(context, listAsesmenReport[position])
    }

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindView(context: Context, asesmen: Asesmen) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val nowDate = LocalDateTime.now().format(formatter)
                val dateTime = LocalDate.parse(asesmen.date, formatter)
                val date = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy").format(dateTime)
                val listDate = date.split(" ").toTypedArray()
                val isToday = if (nowDate == asesmen.date) 1 else 0
                itemView.tv_date_report.text = "${DateFormater.getHari(listDate[0], isToday)}, ${listDate[1]} ${DateFormater.getBulan(listDate[2])} ${listDate[3]}"
            }
            itemView.tv_title_report.text = getTitleReport(asesmen.risiko!!)
            itemView.tv_desc_report.text = getDescReport(asesmen.risiko!!)
            Glide.with(context)
                    .load(getImgReport(asesmen.risiko!!))
                    .into(itemView.iv_history_report)
            itemView.iv_history_report.setBackgroundColor(context.getColor(getColorReport(asesmen.risiko!!)))
            itemView.cv_content_asesment.setOnClickListener {
                val intent = Intent(context, DetailReportActivity::class.java)
                intent.putExtra(DATA_DETAIL_EXTRA, getTitleReport(asesmen.risiko!!).toLowerCase() + " home")
                context.startActivity(intent)
            }
        }
    }
}