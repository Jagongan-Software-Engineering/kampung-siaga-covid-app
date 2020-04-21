package com.seadev.aksi.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.seadev.aksi.R
import com.seadev.aksi.adapter.ProfileAdapter
import com.seadev.aksi.model.Users
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"
    private lateinit var profileAdapter: ProfileAdapter
    private var dataSettings: MutableList<String> = mutableListOf()

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var mValueEventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        initView()
        initFirebase()
    }

    private fun initView() {
        dataSettings.addAll(resources.getStringArray(R.array.setting_list))
        profileAdapter = ProfileAdapter(this@ProfileActivity, dataSettings)
        rvProfile.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = profileAdapter
        }
    }

    private fun initFirebase() {
        val user = auth.currentUser
        var users: Users? = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    for (postSnapshot in dataSnapshot.children) {
                        users = postSnapshot.getValue(Users::class.java)
                    }
                    loadDataUser(users)
                }

            }
            database.orderByChild("phone").equalTo(user?.phoneNumber)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }

    private fun loadDataUser(users: Users?) {
        tvNameProfile.text = users?.nama
        tvPhoneProfile.text = users?.phone
        tvLocationProfile.text = users?.alamat
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}

