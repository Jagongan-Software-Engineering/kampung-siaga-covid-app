package com.seadev.aksi.model.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.repository.AddressRepository
import com.seadev.aksi.model.domain.model.City
import com.seadev.aksi.model.domain.model.District
import com.seadev.aksi.model.domain.model.Province
import com.seadev.aksi.model.domain.model.SubDistrict
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {
    private val _provinces = MutableStateFlow(emptyList<Province>())
    val provinces get() = _provinces

    private val _cities = MutableStateFlow(emptyList<City>())
    val cities get() = _cities

    private val _districts = MutableStateFlow(emptyList<District>())
    val districts get() = _districts

    private val _subDistricts = MutableStateFlow(emptyList<SubDistrict>())
    val subDistricts get() = _subDistricts

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading

    fun getAllData() {
        _isLoading.value = true
        viewModelScope.launch { addressRepository.getRemoteProvince() }
        viewModelScope.launch { addressRepository.getRemoteCity() }
        viewModelScope.launch { addressRepository.getRemoteDistrict() }
        viewModelScope.launch {
            addressRepository.getRemoteSubDistrict().collect {
                if (it is ResultState.Success) _isLoading.value = false
            }
        }
    }


    fun getListProvince() {
        viewModelScope.launch {
            addressRepository.getProvince().collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        _provinces.value = it.data.sortedBy { p -> p.name }
                        _isLoading.value = false
                    }

                    is ResultState.Failed -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getListCity(provinceId: String) {
        viewModelScope.launch {
            addressRepository.getCity(provinceId).collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        _cities.value = it.data.sortedBy { p -> p.name }
                        _isLoading.value = false
                    }

                    is ResultState.Failed -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getDistrict(cityId: String) {
        viewModelScope.launch {
            addressRepository.getDistrict(cityId).collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        _districts.value = it.data.sortedBy { p -> p.name }
                        _isLoading.value = false
                    }

                    is ResultState.Failed -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getSubDistrict(districtId: String) {
        viewModelScope.launch {
            addressRepository.getSubDistrict(districtId).collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        _subDistricts.value = it.data.sortedBy { p -> p.name }
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