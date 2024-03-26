package com.seadev.aksi.model.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.repository.DailyCaseRepository
import com.seadev.aksi.model.domain.model.SummaryCase
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dailyCaseRepository: DailyCaseRepository
) : ViewModel() {

    private val _summaryCase = MutableStateFlow(SummaryCase.Init)
    val summaryCase get() = _summaryCase

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading

    fun getSummary() {
        viewModelScope.launch {
            dailyCaseRepository.getSummary().collect {
                when(it){
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        _summaryCase.value = it.data
                        _isLoading.value = false
                    }
                    is ResultState.Failed -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}