package com.seadev.aksi.model.ui.screen.hotline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.repository.AddressRepository
import com.seadev.aksi.model.domain.abstraction.repository.HotlineRepository
import com.seadev.aksi.model.domain.model.Hotline
import com.seadev.aksi.model.domain.model.Province
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotlineViewModel @Inject constructor(
    private val hotlineRepository: HotlineRepository,
    private val addressRepository: AddressRepository
) : ViewModel() {

    private val _provinces = MutableStateFlow(emptyList<Province>())
    private var listProvinces = mutableListOf<Province>()
    val provinces get() = _provinces

    private val _hotlines = MutableStateFlow(emptyList<Hotline>())
    val hotlines get() = _hotlines

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading

    fun getListProvince() {
        viewModelScope.launch {
            addressRepository.getProvince().collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        listProvinces = mutableListOf(Province.Indonesia)
                            .apply { addAll(it.data.sortedBy { p -> p.name }) }
                        _provinces.value = listProvinces
                        _isLoading.value = false
                    }

                    is ResultState.Failed -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getListHotline() {
        viewModelScope.launch {
            hotlineRepository.getListHotline().collect {
                when (it) {
                    is ResultState.Loading -> {}
                    is ResultState.Success -> {
                        _hotlines.value = mutableListOf(Hotline.Indonesia)
                            .apply { addAll(it.data) }
                    }

                    is ResultState.Failed -> {}
                }
            }
        }
    }

    fun search(query: String) {
        _isLoading.value = true
        listProvinces.filter { it.name.lowercase().contains(query.lowercase()) }.let {
            _provinces.value = it
            _isLoading.value = false
        }
    }

    fun resetData() {
        _provinces.value = listProvinces
    }
}