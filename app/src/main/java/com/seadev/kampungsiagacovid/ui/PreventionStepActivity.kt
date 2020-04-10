package com.seadev.kampungsiagacovid.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.islamkhsh.CardSliderIndicator
import com.github.islamkhsh.CardSliderViewPager
import com.seadev.kampungsiagacovid.R
import com.seadev.kampungsiagacovid.adapter.PreventionStepAdapter
import com.seadev.kampungsiagacovid.model.dataapi.TopikPencegahan
import kotlinx.android.synthetic.main.activity_prevention_step.*

class PreventionStepActivity : AppCompatActivity() {
    companion object {
        val STEP_DATA_EXTRA = "step_data_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prevention_step)

        val data = intent.getParcelableExtra<TopikPencegahan>(STEP_DATA_EXTRA)

        supportActionBar?.title = data.namaTopik
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardSliderViewPager.adapter = PreventionStepAdapter(this, data)
        cardSliderViewPager.smallAlphaFactor = 0.9f
        cardSliderViewPager.smallAlphaFactor = 0.5f
        cardSliderViewPager.autoSlideTime = CardSliderViewPager.STOP_AUTO_SLIDING
        cardSliderIndicator.indicatorsToShow = CardSliderIndicator.UNLIMITED_INDICATORS
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
