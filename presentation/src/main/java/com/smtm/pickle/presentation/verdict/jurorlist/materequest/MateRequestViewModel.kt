package com.smtm.pickle.presentation.verdict.jurorlist.materequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.mate.MateId
import com.smtm.pickle.domain.model.mate.MateStatus
import com.smtm.pickle.domain.usecase.mate.GetReceivedMateRequestsUseCase
import com.smtm.pickle.domain.usecase.mate.UpdateMateRequestStatusUseCase
import com.smtm.pickle.presentation.verdict.model.MateRequestUiModel
import com.smtm.pickle.presentation.verdict.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MateRequestViewModel @Inject constructor(
    private val getReceivedMateRequestsUseCase: GetReceivedMateRequestsUseCase,
    private val updateMateRequestStatusUseCase: UpdateMateRequestStatusUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MateRequestUiState())
    val uiState: StateFlow<MateRequestUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<MateRequestEffect>()
    val effect: SharedFlow<MateRequestEffect> = _effect.asSharedFlow()


    init {
        loadReceivedRequests()
    }


    fun onAcceptClick(mateId: Long) {
        updateStatus(mateId, MateStatus.Accepted, successMsg = "친구 요청을 수락했습니다.")
    }

    fun onRejectClick(mateId: Long) {
        updateStatus(mateId, MateStatus.Rejected, successMsg = "친구 요청을 거절했습니다.")
    }

    private fun loadReceivedRequests() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getReceivedMateRequestsUseCase()
                .onSuccess { requests ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            requests = requests.map { mateRequest -> mateRequest.toUiModel() })
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    _effect.emit(MateRequestEffect.ShowSnackBar("친구 요청 목록을 불러오지 못했습니다"))
                }
        }
    }

    private fun updateStatus(mateId: Long, status: MateStatus, successMsg: String) {
        viewModelScope.launch {
            if (_uiState.value.isLoading) return@launch
            _uiState.update { it.copy(isLoading = true) }

            updateMateRequestStatusUseCase(MateId(mateId), status)
                .onSuccess { processedMateId ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            requests = state.requests.filter { it.id != processedMateId.value }
                        )
                    }
                    _effect.emit(MateRequestEffect.ShowSnackBar(successMsg))
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    _effect.emit(MateRequestEffect.ShowSnackBar("처리 중 오류가 발생했습니다"))
                }
        }
    }
}

data class MateRequestUiState(
    val requests: List<MateRequestUiModel> = emptyList(),
    val isLoading: Boolean = false,
)

sealed interface MateRequestEffect {
    data class ShowSnackBar(val msg: String) : MateRequestEffect
}
