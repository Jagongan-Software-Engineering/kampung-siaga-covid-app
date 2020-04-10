package com.seadev.kampungsiagacovid.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.seadev.kampungsiagacovid.R
import com.seadev.kampungsiagacovid.adapter.PreventionAdapter
import com.seadev.kampungsiagacovid.model.dataapi.TopikPencegahan
import com.seadev.kampungsiagacovid.model.requestbody.ItemTopikPencegahan
import com.seadev.kampungsiagacovid.rest.ApiClientLokasi
import com.seadev.kampungsiagacovid.rest.ApiInterfaceLokasi
import kotlinx.android.synthetic.main.activity_prevention.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreventionActivity : AppCompatActivity() {

    private val apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceLokasi::class.java)
    private var preventionList: List<TopikPencegahan> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prevention)

        supportActionBar?.title = "Pencegahan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvPrevention.setHasFixedSize(true)
        rvPrevention.itemAnimator = DefaultItemAnimator()
        loadDataPrevention()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun loadDataPrevention() {
        apiServiceLokasi.dataPencegahan
                .enqueue(object : Callback<ItemTopikPencegahan> {
                    override fun onResponse(call: Call<ItemTopikPencegahan?>, response: Response<ItemTopikPencegahan?>) {
                        assert(response.body() != null)
                        preventionList = response.body()!!.pencegahanList
                        rvPrevention.adapter = PreventionAdapter(this@PreventionActivity, preventionList)
                    }

                    override fun onFailure(call: Call<ItemTopikPencegahan?>, t: Throwable) {
                        Log.d("PreventionActivity", "error: " + t.message)
                    }
                })
    }
}


