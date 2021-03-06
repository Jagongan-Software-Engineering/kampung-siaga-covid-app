package com.seadev.aksirtrw.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seadev.aksi.model.Users
import com.seadev.aksi.util.DateFormater
import com.seadev.aksi.util.ReportHistoryFormater
import com.seadev.aksirtrw.R
import com.seadev.aksirtrw.model.Assessment
import kotlinx.android.synthetic.main.item_list_warga.view.*

class DailyReportAdapter(
        val context: Context,
        val listWarga: MutableList<Assessment> = mutableListOf()
) : RecyclerView.Adapter<DailyReportAdapter.DailyReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_warga, parent, false)
        return DailyReportViewHolder(view)
    }

    override fun getItemCount(): Int = listWarga.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DailyReportViewHolder, position: Int) {
        val dataAssessment = listWarga[position]
        val listDate = dataAssessment.date?.split(", ")
        val date = "${DateFormater.getHari(listDate!![0], 0)}, ${listDate[1]}, ${listDate[2]}"
        holder.view.tvDateWarga.text = date
        holder.view.tvStatusWarga.text = ReportHistoryFormater.getTitleReport(dataAssessment.risiko!!)
        holder.view.layoutStatusWarga.setCardBackgroundColor(
                context.resources.getColor(ReportHistoryFormater.getColorReport(dataAssessment.risiko!!))
        )
        holder.loadUser()
    }

    class DailyReportViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var database: DatabaseReference
        private lateinit var auth: FirebaseAuth
        private var mValueEventListener: ValueEventListener? = null

        fun loadUser() {
            auth = FirebaseAuth.getInstance()
            database = FirebaseDatabase.getInstance().reference.child("users")
            val user = auth.currentUser
            var users: Users? = null
            if (mValueEventListener == null) {
                mValueEventListener = object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.d("DailyReportAdapter", "loadPost:onCancelled:success ${databaseError.toException()}")
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Log.d("DailyReportAdapter", "onChildAdded:success")
                        for (postSnapshot in dataSnapshot.children) {
                            users = postSnapshot.getValue(Users::class.java)
                            view.tvListNamaWarga.text = users?.nama
                        }
                    }

                }
                database.orderByChild("phone").equalTo(user?.phoneNumber)
                        .addValueEventListener(mValueEventListener as ValueEventListener)
            }
        }
    }
}