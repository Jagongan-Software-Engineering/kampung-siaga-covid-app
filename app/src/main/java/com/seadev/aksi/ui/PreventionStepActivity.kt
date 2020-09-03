package com.seadev.aksi.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.islamkhsh.CardSliderIndicator
import com.github.islamkhsh.CardSliderViewPager
import com.seadev.aksi.R
import com.seadev.aksi.adapter.PreventionStepAdapter
import com.seadev.aksi.model.dataapi.TopikPencegahan
import kotlinx.android.synthetic.main.activity_prevention_step.*

class PreventionStepActivity : AppCompatActivity() {
    companion object {
        val STEP_DATA_EXTRA = "step_data_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prevention_step)

        val data = intent.getParcelableExtra<TopikPencegahan>(STEP_DATA_EXTRA)

        supportActionBar?.apply {
            title = data!!.namaTopik
            setDisplayHomeAsUpEnabled(true)
        }

        cardSliderViewPager.apply {
            adapter = PreventionStepAdapter(this@PreventionStepActivity, data!!)
            smallAlphaFactor = 0.9f
            smallAlphaFactor = 0.5f
            autoSlideTime = CardSliderViewPager.STOP_AUTO_SLIDING
        }
        cardSliderIndicator.indicatorsToShow = CardSliderIndicator.UNLIMITED_INDICATORS
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
