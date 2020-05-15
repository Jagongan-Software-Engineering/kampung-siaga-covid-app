package com.seadev.aksi.rtrw.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.seadev.aksi.R
import com.seadev.aksi.model.Users
import com.seadev.aksi.rtrw.model.KetuaRt
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.dialog_progress_upload.view.*
import kotlinx.android.synthetic.main.item_upload_verification.*
import kotlinx.android.synthetic.main.item_verification.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class VerificationActivity : AppCompatActivity() {

    companion object {
        val RC_PHOTO_PICKER = 2
        val RC_PHOTO_TAKER = 1
        val KEY_IS_VERIFY = "key_verif"
    }

    private val TAG = "VerificationActivity"
    private var isBeenVerify = false
    private lateinit var database: DatabaseReference
    private lateinit var refUserRt: DatabaseReference
    private lateinit var refVerificatoin: StorageReference
    private lateinit var auth: FirebaseAuth
    private var mValueEventListener: ValueEventListener? = null
    private lateinit var selectedImgUri: Uri
    lateinit var currentPhotoPath: String

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
            sprite.color = resources.getColor(R.color.colorPrimary)
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
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()

            view.btnSelectPict.setOnClickListener {
                val getIntent = Intent(Intent.ACTION_GET_CONTENT)
                getIntent.type = "image/*"

                val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickIntent.type = "image/*"

                val chooserIntent = Intent.createChooser(getIntent, "Select Image")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

                startActivityForResult(chooserIntent, RC_PHOTO_PICKER)
                dialog.dismiss()
            }

            view.btnTakePict.setOnClickListener {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        // Create the File where the photo should go
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            Log.d(TAG, "IOException: $ex")
                            null
                        }
                        // Continue only if the File was successfully created
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                    this,
                                    "com.seadev.aksi.fileprovider",
                                    it
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, RC_PHOTO_TAKER)
                        }
                    }
                }
                dialog.dismiss()
            }
        }

        btnUploadVerif.setOnClickListener {
            btnUploadVerif.isEnabled = false
            btnUploadVerif.setCardBackgroundColor(resources.getColor(R.color.skeleton))
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

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    if (dataSnapshot.exists() && !isBeenVerify) {
                        Log.d(TAG, "intent in initFirebase :success")
                        startActivity(Intent(this@VerificationActivity, MainActivityRt::class.java))
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
                    Toast.makeText(this@VerificationActivity, "Pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                    btnUploadVerif.isEnabled = true
                    btnUploadVerif.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
                    tvBtnUploadVerif.text = "Unggah Bukti Verifikasi"
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "onChildAdded:success")
                    for (postSnapshot in dataSnapshot.children) {
                        users = postSnapshot.getValue(Users::class.java)
                    }
                    createNewUser(users)
                }

            }
            database.orderByChild("idUser").equalTo(user?.uid)
                    .addValueEventListener(mValueEventListener as ValueEventListener)
        }
    }

    private fun createNewUser(users: Users?) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_progress_upload, null)
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view).setTitle("Mengunggah berkas")
            builder.setCancelable(false)
            builder.create()
        }
        alertDialog?.show()

        val dataRef = refVerificatoin.child(users?.idUser!!)
        dataRef.putFile(this.selectedImgUri)
                .addOnSuccessListener { taskSnapshot ->
                    alertDialog?.dismiss()
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
                                    startActivity(Intent(this@VerificationActivity, MainActivityRt::class.java))
                                    finish()
                                }
                                .addOnFailureListener {
                                    Log.d(TAG, "setValue:fail")
                                }
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "putFile:fail $it")
                    Toast.makeText(this@VerificationActivity, "Gagal Mengunggah", Toast.LENGTH_SHORT).show()
                    btnUploadVerif.isEnabled = true
                    btnUploadVerif.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
                    tvBtnUploadVerif.text = "Unggah Bukti Verifikasi"
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                    view.tvProgressUpload.text = "${progress.toInt()}%"
                    view.pbProgressUpload.progress = progress.toInt()
                    Log.d(TAG, "Prgoress upload : $progress")
                }
    }

    private fun loadLayoutBeenVerify() {
        Log.d(TAG, "isBeenVerify in loadLayoutBeenVerify: $isBeenVerify")
        layoutVerificationModul.visibility = View.GONE
        layoutUpload.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_PHOTO_TAKER -> {
                if (resultCode == Activity.RESULT_OK) {
                    val file = File(currentPhotoPath)
                    this.selectedImgUri = Uri.fromFile(file)
                    Log.d(TAG, "uriImage: ${this.selectedImgUri}")
                    setImgUpload(selectedImgUri)
                }

            }
            RC_PHOTO_PICKER -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    this.selectedImgUri = data.data!!
                    Log.d(TAG, "selectedImgUri: ${this.selectedImgUri}")
                    setImgUpload(selectedImgUri)
                }
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            val sharedPref = this@VerificationActivity.getPreferences(Context.MODE_PRIVATE)
                    ?: return
            isBeenVerify = sharedPref.getBoolean(KEY_IS_VERIFY, false)
            Log.d(TAG, "isBeenVerify in onActivityResult: $isBeenVerify")
            loadLayoutBeenVerify()
        }
    }

    private fun setImgUpload(selectedImgUri: Uri) {
        Glide.with(this)
                .load(selectedImgUri)
                .into(ivUploaded)
        btnUploadVerif.isEnabled = true
        btnUploadVerif.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
    }

    private fun initView() {
        btnNextVerify.isEnabled = false
        btnNextVerify.setCardBackgroundColor(resources.getColor(R.color.skeleton))
        btnUploadVerif.isEnabled = false
        btnUploadVerif.setCardBackgroundColor(resources.getColor(R.color.skeleton))
    }

    private fun stateBtnVerif(state: Boolean?) {
        if (state!!) btnNextVerify.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
        else btnNextVerify.setCardBackgroundColor(resources.getColor(R.color.skeleton))
        btnNextVerify.isEnabled = state
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.d(TAG, "currentPhotoPath: $currentPhotoPath")
        }

    }

}
