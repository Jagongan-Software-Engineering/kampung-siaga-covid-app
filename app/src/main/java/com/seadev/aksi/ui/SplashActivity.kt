package com.seadev.aksi.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.seadev.aksi.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sprite: Sprite = ThreeBounce()
        sprite.color = resources.getColor(R.color.colorPrimary)
        pbSplash.indeterminateDrawable = sprite
        pbSplash.visibility = View.VISIBLE

        Handler().postDelayed({
            auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            Log.d(TAG, "userId: ${user?.uid}")
            initialUI(user)
            finish()
        }, 2000)

    }

    private fun initialUI(user: FirebaseUser?) {
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
