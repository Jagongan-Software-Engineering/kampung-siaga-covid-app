package com.seadev.aksirtrw.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.seadev.aksi.model.Users
import com.seadev.aksirtrw.R
import com.seadev.aksirtrw.model.KetuaRt
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.item_upload_verification.*
import kotlinx.android.synthetic.main.item_verification.*
import com.seadev.aksi.R as aksiR


class VerificationActivity : AppCompatActivity() {

    companion object {
        val RC_PHOTO_PICKER = 2
        val KEY_IS_VERIFY = "key_verif"
    }

    private val TAG = "VerificationActivity"
    private var isBeenVerify = false
    private lateinit var database: DatabaseReference
    private lateinit var refUserRt: DatabaseReference
    private lateinit var refVerificatoin: StorageReference
    private lateinit var auth: FirebaseAuth
    private var mValueEventListener: ValueEventListener? = null
    private lateinit var selectedImg: Uri

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")
        refUserRt = FirebaseDatabase.getInstance().reference.child("ketuart")
        refVerificatoin = FirebaseStorage.getInstance().reference.child("doc_verif")

        Log.d(TAG, "isBeenVerify in onCreate: $isBeenVerify")

        if (isBeenVerify) {
            Log.d(TAG, "isBeenVerify: $isBeenVerify")
            loadLayoutBeenVerify()
        } else {
            Log.d(TAG, "isBeenVerify: $isBeenVerify")
            val sprite = ThreeBounce()
            sprite.color = resources.getColor(aksiR.color.colorPrimary)
            pbVerifRt.setIndeterminateDrawableTiled(sprite)
            pbVerifRt.visibility = View.VISIBLE
            initFirebase()
        }

        initView()

        cbVerification.setOnCheckedChangeListener { buttonView, isChecked ->
            stateBtnVerif(isChecked)
        }

        btnNextVerify.setOnClickListener {
            isBeenVerify = true
            val sharedPref = this@VerificationActivity.getPreferences(Context.MODE_PRIVATE)
                    ?: return@setOnClickListener
            with(sharedPref.edit()) {
                putBoolean(KEY_IS_VERIFY, isBeenVerify)
                commit()
            }
            Log.d(TAG, "isBeenVerify in btnNextVerify: $isBeenVerify")
            loadLayoutBeenVerify()
        }

        btnBrowseFoto.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "img/jpeg"
                putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            }
            startActivityForResult(Intent.createChooser(mIntent, "Lanjutkan menggunakan"), RC_PHOTO_PICKER)
        }

        btnUploadVerif.setOnClickListener {
            btnUploadVerif.isEnabled = false
            btnUploadVerif.setCardBackgroundColor(getColor(aksiR.color.skeleton))
            tvBtnUploadVerif.text = "Sedang Menggunggah bukti ..."
            loadDataUser()
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun initFirebase() {
        val user = auth.currentUser
        mValueEventListener = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    if (dataSnapshot.exists() && !isBeenVerify) {
                        Log.d(TAG, "intent in initFirebase :success")
                        startActivity(Intent(this@VerificationActivity, MainActivity::class.java))
                        finish()
                    } else {
                        layoutVerificationModul.visibility = View.VISIBLE
                        layoutLoadingVerifRt.visibility = View.GONE
                    }
                }

            }
            refUserRt.orderByChild("userId").equalTo(user?.uid)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }


    private fun loadDataUser() {
        val user = auth.currentUser
        mValueEventListener = null
        var users: Users? = null
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    for (postSnapshot in dataSnapshot.children) {
                        users = postSnapshot.getValue(Users::class.java)
                    }
                    createNewUser(users)
                }

            }
            database.orderByChild("phone").equalTo(user?.phoneNumber)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }

    private fun createNewUser(users: Users?) {
        val dataRef = refVerificatoin.child(users?.idUser!!)
        dataRef.putFile(this.selectedImg)
                .addOnSuccessListener { taskSnapshot ->
                    Log.d(TAG, "putFile:success")
                    dataRef.downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "downloadUrl:success")
                        Log.d(TAG, "Uri: $it")
                        val akunRt = KetuaRt(
                                users.idUser, users.nama, users.rtrw, users.idDesa,
                                users.idKacamatan, users.idKotaKab, users.idProvinsi,
                                0, it.toString(), 0, 0
                        )
                        refUserRt.child(akunRt.userId!!).setValue(akunRt)
                                .addOnSuccessListener {
                                    Log.d(TAG, "setValue:success")
                                    Log.d(TAG, "intent in createNewUser :success")
                                    startActivity(Intent(this@VerificationActivity, MainActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener {
                                    Log.d(TAG, "setValue:fail")
                                }
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "putFile:fail $it")
                }
    }

    private fun loadLayoutBeenVerify() {
        Log.d(TAG, "isBeenVerify in loadLayoutBeenVerify: $isBeenVerify")
        layoutVerificationModul.visibility = View.GONE
        layoutUpload.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            this.selectedImg = data?.data!!
            setImgUpload(selectedImg)
            val sharedPref = this@VerificationActivity.getPreferences(Context.MODE_PRIVATE)
                    ?: return
            isBeenVerify = sharedPref.getBoolean(KEY_IS_VERIFY, false)
            Log.d(TAG, "isBeenVerify in onActivityResult: $isBeenVerify")
            loadLayoutBeenVerify()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setImgUpload(selectedImg: Uri) {
        Glide.with(this)
                .load(selectedImg)
                .into(ivUploaded)
        btnUploadVerif.isEnabled = true
        btnUploadVerif.setCardBackgroundColor(getColor(aksiR.color.colorPrimary))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        btnNextVerify.isEnabled = false
        btnNextVerify.setCardBackgroundColor(getColor(aksiR.color.skeleton))
        btnUploadVerif.isEnabled = false
        btnUploadVerif.setCardBackgroundColor(getColor(aksiR.color.skeleton))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun stateBtnVerif(state: Boolean?) {
        if (state!!) btnNextVerify.setCardBackgroundColor(getColor(aksiR.color.colorPrimary))
        else btnNextVerify.setCardBackgroundColor(getColor(aksiR.color.skeleton))
        btnNextVerify.isEnabled = state
    }
}
