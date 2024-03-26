package com.seadev.aksi.model.ui.screen.assessment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seadev.aksi.model.data.ResultState
import com.seadev.aksi.model.domain.abstraction.repository.AssessmentRepository
import com.seadev.aksi.model.domain.model.HistoryAssessment
import com.seadev.aksi.model.utils.DataUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentViewModel @Inject constructor(
    private val assessmentRepository: AssessmentRepository
) : ViewModel() {

    fun addHistoryAssessment(
        assessmentResult: DataUtils.SelfAssessmentQuestion.AssessmentResult,
        isFinish: (isSuccess: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            assessmentRepository.addHistoryAssessment(
                HistoryAssessment(Firebase.auth.currentUser?.uid?:"", assessmentResult.name, Timestamp.now())
            ).collect {
                when (it) {
                    is ResultState.Loading -> {}
                    is ResultState.Success -> {
                        isFinish(true)
                    }

                    is ResultState.Failed -> {
                        isFinish(false)
                    }
                }
            }
        }
    }

    private var _listAssessment = MutableStateFlow(emptyList<HistoryAssessment>())
    val listAssessment get() = _listAssessment

    private var _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading

    fun getHistoryAssessment() {
        viewModelScope.launch {
            assessmentRepository.getHistoryAssessment(Firebase.auth.currentUser?.uid?:"").collect {
                when (it) {
                    is ResultState.Loading -> _isLoading.value = true
                    is ResultState.Success -> {
                        _listAssessment.value = it.data.sortedByDescending { a -> a.dateCreated }
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