package com.seadev.aksi.model.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.repository.AuthRepository
import com.seadev.aksi.model.domain.model.AksiUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _currentUser = MutableStateFlow(AksiUser.Init)
    val currentUser get() = _currentUser

    private var _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading

    fun getUser(uid: String, onFinish: (isUserFound: Boolean) -> Unit) {
        viewModelScope.launch {
            authRepository.getCurrentUser(uid).collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        Log.d("TAG", "getUser: true")
                        Log.d("TAG", "getUser: ${it.data}")
                        _currentUser.value = it.data
                        onFinish.invoke(it.data != AksiUser.Init)
                        _isLoading.value = false
                    }

                    is ResultState.Failed -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private var _message = MutableStateFlow("")
    val message get() = _message

    fun createNewUser(aksiUser: AksiUser, isSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            authRepository.createNewUser(aksiUser).collect {
                when(it){
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        _message.value = it.data
                        _isLoading.value = false
                        isSuccess(true)
                    }
                    is ResultState.Failed -> {
                        _message.value = it.message
                        _isLoading.value = false
                        isSuccess(false)
                    }
                }
            }
        }
    }
}