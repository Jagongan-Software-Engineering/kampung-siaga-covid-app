package com.seadev.kampungsiagacovid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seadev.kampungsiagacovid.BuildConfig
import com.seadev.kampungsiagacovid.R
import com.seadev.kampungsiagacovid.model.dataapi.TopikPencegahan
import com.seadev.kampungsiagacovid.ui.PreventionStepActivity
import kotlinx.android.synthetic.main.item_prevention.view.*

class PreventionAdapter(
        val context: Context,
        val topikPencegahan: MutableList<TopikPencegahan>
) : RecyclerView.Adapter<PreventionAdapter.PreventionViewHolder>() {

    fun clear() {
        val size: Int = topikPencegahan.size
        topikPencegahan.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreventionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prevention, parent, false)
        return PreventionViewHolder(view)
    }

    override fun getItemCount(): Int = topikPencegahan.size

    override fun onBindViewHolder(holder: PreventionViewHolder, position: Int) {
        val mTopik = topikPencegahan.get(position)
        holder.bindPrevention(context, mTopik)
    }

    class PreventionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindPrevention(context: Context, topikPencegahan: TopikPencegahan) {
            itemView.contentPrevention.setOnClickListener {
                var intent = Intent(context, PreventionStepActivity::class.java)
                intent.putExtra(PreventionStepActivity.STEP_DATA_EXTRA, topikPencegahan)
                context.startActivity(intent)
            }
            itemView.tvItemPrevention.text = topikPencegahan.namaTopik
            Glide.with(context).load(BuildConfig.BASE_URL_LOKASI + topikPencegahan.imgTopik)
                    .into(itemView.ivItemPrevention)
        }
    }
}