package com.seadev.aksi.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.seadev.aksi.R
import com.seadev.aksi.rtrw.ui.VerificationActivity
import com.seadev.aksi.ui.HistoryReportActivity
import com.seadev.aksi.ui.LoginActivity
import com.seadev.aksi.ui.MainActivity
import kotlinx.android.synthetic.main.item_profile.view.*


class ProfileAdapter(
        val context: Context,
        val listSettigs: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun getItemCount(): Int = listSettigs.size

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val nameSetting = listSettigs[position]

        val dataImg = listOf(
                R.drawable.ic_arrow_right,
                R.drawable.ic_arrow_right,
                R.drawable.ic_feed_back,
                R.drawable.ic_log_out
        )

        holder.view.tvItemProfile.text = nameSetting
        Glide.with(context).load(dataImg[position]).into(holder.view.ivItemProfile)

        holder.view.layoutItemProfile.setOnClickListener {
            when (position) {
                0 -> {
                    context.startActivity(Intent(context, HistoryReportActivity::class.java))
                }
                1 -> {
                    holder.move()
                }
                2 -> {
                    val mIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(context.getString(R.string.feed_back_link))
                    )
                    context.startActivity(mIntent)
                }
                3 -> {
                    FirebaseAuth.getInstance().signOut()
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    (context as Activity).finish()
                    MainActivity.activity.finish()
                    Toast.makeText(context, "Anda telah keluar", Toast.LENGTH_SHORT).show()
                }
                else -> Toast.makeText(context, "No selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ProfileViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var mySessionId: Int? = null

        fun move() {
            view.context.startActivity(Intent(view.context, VerificationActivity::class.java))
        }

    }
}