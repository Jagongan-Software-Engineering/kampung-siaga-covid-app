package com.seadev.aksi.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.seadev.aksi.R
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
        holder.view.tvItemProfile.text = nameSetting
        if (position == (listSettigs.size - 1)) {
            Glide.with(context).load(R.drawable.ic_log_out).into(holder.view.ivItemProfile)
        }
        holder.view.layoutItemProfile.setOnClickListener {
            when (position) {
                0 -> {
                    context.startActivity(Intent(context, HistoryReportActivity::class.java))
                }
                1 -> {
                    val intent = Intent()
                    intent.setClassName(context, "com.seadev.aksirtrw.ui.VerificationActivity")
                    context.startActivity(intent)
                }
                2 -> {
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

    class ProfileViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}