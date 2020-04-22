package com.seadev.aksi.ui

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import com.seadev.aksi.R
import com.seadev.aksi.model.Users
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var mValueEventListener: ValueEventListener? = null
    private var isValidationComplete: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        validation(false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")
        val sprite = ThreeBounce()
        sprite.color = resources.getColor(R.color.colorPrimary);
        pbCodeSent.indeterminateDrawable = sprite
        pbVerifProgress.indeterminateDrawable = sprite

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(TAG, "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                layoutLogin.visibility = View.GONE
                layoutVerification.visibility = View.VISIBLE
            }
        }

        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val valid = !etPhoneNumber.text.isEmpty()
                btnLogin.isEnabled = valid
                validation(valid)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("LoginActivity", "text beforeTextChanged: $s")
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("LoginActivity", "text afterTextChanged: $s")
            }
        })

        btnLogin.setOnClickListener {
            hideKeyboard(this@LoginActivity)

            btnLogin.visibility = View.GONE
            layoutCodeSentProgress.visibility = View.VISIBLE
            pbCodeSent.visibility = View.VISIBLE

            val textPhone: String
            textPhone = if (etPhoneNumber.text[0].toString() != "0") {
                etPhoneNumber.text.toString()
            } else {
                val sb = StringBuilder(etPhoneNumber.text.toString())
                sb.deleteCharAt(0).toString()
            }
            val phoneNumber = "+62$textPhone"
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber, 60, TimeUnit.SECONDS, this, callbacks)
        }

        btnVerification.setOnClickListener {
            hideKeyboard(this@LoginActivity)

            Log.d(TAG, "entry code: ${entryCode.text}")
        }

        entryCode.setOnPinEnteredListener {
            verifyPhoneNumberWithCode(storedVerificationId, it.toString())
        }
    }

    private fun hideKeyboard(loginActivity: LoginActivity) {
        val imm: InputMethodManager = loginActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = loginActivity.currentFocus
        if (view == null) {
            view = View(loginActivity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun validation(isVald: Boolean) {
        if (!isVald) btnLogin.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.color_text_3))
        else btnLogin.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = task.result?.user
                        val mUser = Users(user?.uid, user?.phoneNumber)
                        Log.d(TAG, "user: \n" +
                                "phone: ${user?.phoneNumber}\n" +
                                "id: ${user?.uid}")
                        loadDataUser(mUser.phone!!)

                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    private fun loadDataUser(phonenumber: String) {
        Log.d(TAG, "loadDataUser:success")
        Log.d(TAG, "phone : $phonenumber")
        var isExist = false
        var users: Users? = null
        Log.d(TAG, "isExist First: $isExist")
        if (mValueEventListener == null) {
            mValueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, "loadPost:onCancelled:success ${databaseError.toException()}")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        Log.d(TAG, "onChildAdded:dataSnapshot noData")
                        validationUser(users)
                    } else {
                        Log.d(TAG, "onChildAdded:success")
                        for (postSnapshot in dataSnapshot.children) {
                            users = postSnapshot.getValue(Users::class.java)
                        }
                        isExist = true
                        Log.d(TAG, "isExist Middle: $isExist")
                        validationUser(users)
                    }
                }

            }
            database.orderByChild("phone").equalTo(phonenumber).addValueEventListener(mValueEventListener as ValueEventListener)
            Log.d(TAG, "isExist Last: $isExist")
            mValueEventListener = null
        }
    }

    private fun createNewUser() {
        Log.d(TAG, "createNewUser:success")
        val user = auth.currentUser
        val mUser = Users(user?.uid, user?.phoneNumber)
        database.child(user?.uid!!).setValue(mUser)
                .addOnSuccessListener {
                    Log.d(TAG, "addOnSuccessListener:success")
                    startActivity(Intent(this, RegisterActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Log.d(TAG, "addOnFailureListener: $it")
                }
    }

    private fun validationUser(mUser: Users?) {
        if (!isValidationComplete) {
            if (mUser == null) {
                Log.d(TAG, "validationUser:null")
                tvVerificationCode.text = "Membuat akun baru ..."
                createNewUser()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        isValidationComplete = true
    }
}
