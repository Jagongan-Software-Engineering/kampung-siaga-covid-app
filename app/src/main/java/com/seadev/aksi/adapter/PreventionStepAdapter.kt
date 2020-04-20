package com.seadev.aksi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import com.seadev.aksi.BuildConfig
import com.seadev.aksi.R
import com.seadev.aksi.model.dataapi.LangkahPencegahan
import com.seadev.aksi.model.dataapi.TopikPencegahan
import kotlinx.android.synthetic.main.item_step_prevention.view.*

class PreventionStepAdapter(
        private val context: Context, val langkah: TopikPencegahan
) : CardSliderAdapter<PreventionStepAdapter.StepViewHolder>() {

    override fun bindVH(holder: StepViewHolder, position: Int) {
        val item = langkah.pencegahanList[position]
        holder.itemBind(context, item)
    }

    override fun getItemCount(): Int = langkah.pencegahanList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_step_prevention, parent, false)
        return StepViewHolder(view)
    }

    class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun itemBind(context: Context, step: LangkahPencegahan) {
            itemView.tvTitleStepPrevention.text = step.judul
            itemView.tvDescStepPrevention.text = step.desc
            Glide.with(context)
                    .load(BuildConfig.BASE_URL_LOKASI + step.img)
                    .into(itemView.ivImgStepPrevention)
        }
    }

}