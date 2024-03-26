package com.seadev.aksi.model.ui.screen.distribution

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.repository.DailyCaseRepository
import com.seadev.aksi.model.domain.model.DistributionCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistributionViewModel @Inject constructor(
    private val dailyCaseRepository: DailyCaseRepository
) : ViewModel() {

    private val _provinceDistribution = MutableStateFlow(emptyList<DistributionCase>())
    private var listDistribution = mutableListOf<DistributionCase>()
    val provinceDistribution get() = _provinceDistribution

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading

    fun getProvinceDistribution() {
        viewModelScope.launch {
            dailyCaseRepository.getDistribution().collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        listDistribution = it.data.sortedBy { d -> d.province }.toMutableList()
                        _provinceDistribution.value = listDistribution
                        _isLoading.value = false
                    }

                    is ResultState.Failed -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun resetList() {
        _provinceDistribution.value = listDistribution
    }

    fun search(query: String) {
        _isLoading.value = true
        listDistribution.filter {
            it.province.lowercase().contains(query.lowercase())
        }.let { result ->
            _provinceDistribution.value = result
            _isLoading.value = false
        }
    }
}