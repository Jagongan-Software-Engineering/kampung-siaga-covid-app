package com.seadev.kampungsiagacovid.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.seadev.kampungsiagacovid.BuildConfig
import com.seadev.kampungsiagacovid.R
import com.seadev.kampungsiagacovid.adapter.PreventionAdapter
import com.seadev.kampungsiagacovid.model.dataapi.TopikPencegahan
import com.seadev.kampungsiagacovid.model.requestbody.ItemTopikPencegahan
import com.seadev.kampungsiagacovid.rest.ApiClientLokasi
import com.seadev.kampungsiagacovid.rest.ApiInterfaceFirebase
import kotlinx.android.synthetic.main.activity_prevention.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreventionActivity : AppCompatActivity() {

    private val apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceFirebase::class.java)
    private var preventionList: MutableList<TopikPencegahan> = mutableListOf()
    private lateinit var adapter: PreventionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prevention)

        supportActionBar?.title = "Pencegahan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sprite = ThreeBounce()
        sprite.color = resources.getColor(R.color.colorPrimary)
        pb_prevention.setIndeterminateDrawableTiled(sprite)

        val imgSprite = ThreeBounce()
        imgSprite.color = resources.getColor(R.color.colorPrimary)
        iv_icon_prevention.setImageDrawable(imgSprite)

        rvPrevention.setHasFixedSize(true)
        rvPrevention.itemAnimator = DefaultItemAnimator()
        loadDataPrevention()

        refreshLayoutPrevention.setOnRefreshListener {
            adapter.clear()
            rvPrevention.setHasFixedSize(true)
            rvPrevention.itemAnimator = DefaultItemAnimator()
            loadDataPrevention()
            refreshLayoutPrevention.isRefreshing = false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun loadDataPrevention() {
        showProgressbar(true)
        Glide.with(this).load("${BuildConfig.BASE_URL_LOKASI}res%2Fpelajari.png?alt=media&token=65fcd079-c891-47fd-95af-c6960d644c04")
                .into(iv_icon_prevention)
        apiServiceLokasi.dataPencegahan
                .enqueue(object : Callback<ItemTopikPencegahan> {
                    override fun onResponse(call: Call<ItemTopikPencegahan?>, response: Response<ItemTopikPencegahan?>) {
                        assert(response.body() != null)
                        preventionList = response.body()!!.pencegahanList
                        adapter = PreventionAdapter(this@PreventionActivity, preventionList)
                        rvPrevention.adapter = adapter
                        showProgressbar(false)
                    }

                    override fun onFailure(call: Call<ItemTopikPencegahan?>, t: Throwable) {
                        Log.d("PreventionActivity", "error: " + t.message)
                    }
                })
    }

    private fun showProgressbar(state: Boolean) {
        if (state) {
            pb_prevention.setVisibility(View.VISIBLE)
        } else {
            pb_prevention.setVisibility(View.GONE)
        }
    }
}


