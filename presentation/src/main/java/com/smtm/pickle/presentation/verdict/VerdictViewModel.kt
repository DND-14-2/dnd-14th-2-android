package com.smtm.pickle.presentation.verdict

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.verdict.VerdictType
import com.smtm.pickle.domain.usecase.nickname.ObserveNicknameUseCase
import com.smtm.pickle.domain.usecase.verdict.GetJurorVerdictsUseCase
import com.smtm.pickle.domain.usecase.verdict.GetMyVerdictsUseCase
import com.smtm.pickle.domain.usecase.verdict.JudgeVerdictUseCase
import com.smtm.pickle.presentation.verdict.model.AssignedVerdictUiModel
import com.smtm.pickle.presentation.verdict.model.RequestedVerdictUiModel
import com.smtm.pickle.presentation.verdict.model.VerdictCounts
import com.smtm.pickle.presentation.verdict.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VerdictViewModel @Inject constructor(
    observeNicknameUseCase: ObserveNicknameUseCase,
    private val getJurorVerdictsUseCase: GetJurorVerdictsUseCase,
    private val getMyVerdictsUseCase: GetMyVerdictsUseCase,
    private val judgeVerdictUseCase: JudgeVerdictUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerdictUiState())
    val uiState: StateFlow<VerdictUiState> = combine(
        _uiState,
        observeNicknameUseCase(),
    ) { state, nickname ->
        state.copy(userNickname = nickname ?: "유저 닉네임")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = VerdictUiState()
    )

    private val _effect = MutableSharedFlow<VerdictEffect>(replay = 0)
    val effect: SharedFlow<VerdictEffect> = _effect.asSharedFlow()

    private var allRequestedVerdicts: List<RequestedVerdictUiModel> = emptyList()
    private var allAssignedVerdicts: List<AssignedVerdictUiModel> = emptyList()

    init {
        loadVerdicts()
    }

    fun onTabSelected(index: Int) {
        _uiState.update { state ->
            state.copy(selectedTabIndex = index).applyFilters()
        }
    }

    fun onFilterSelected(index: Int) {
        _uiState.update { state ->
            val updated = if (state.selectedTabIndex == TabIndex.REQUESTED) {
                state.copy(requestedVerdicts = state.requestedVerdicts.copy(filterIndex = index))
            } else {
                state.copy(assignedVerdicts = state.assignedVerdicts.copy(filterIndex = index))
            }
            updated.applyFilters()
        }
    }

    private fun VerdictUiState.applyFilters(): VerdictUiState {
        return copy(
            requestedVerdicts = requestedVerdicts.copy(
                items = filterRequestedVerdicts(allRequestedVerdicts, requestedVerdicts.filterIndex),
                counts = calculateRequestedCounts(allRequestedVerdicts)
            ),
            assignedVerdicts = assignedVerdicts.copy(
                items = filterAssignedVerdicts(allAssignedVerdicts, assignedVerdicts.filterIndex),
                counts = calculateAssignedCounts(allAssignedVerdicts)
            )
        )
    }

    fun onAssignedVerdictItemClick(verdict: AssignedVerdictUiModel) {
        when (verdict.verdictType) {
            VerdictType.Pending -> {
                _uiState.update {
                    it.copy(
                        selectedAssignedVerdictForJudgement = verdict,
                        selectedRequestedVerdict = null,
                    )
                }
            }

            VerdictType.Guilty, VerdictType.NotGuilty -> {
                _uiState.update {
                    it.copy(
                        selectedAssignedVerdict = verdict,
                        selectedRequestedVerdict = null,
                    )
                }
            }
        }
    }

    fun onRequestedVerdictItemClick(verdict: RequestedVerdictUiModel) {
        _uiState.update {
            it.copy(
                selectedRequestedVerdict = verdict,
                selectedAssignedVerdict = null,
                selectedAssignedVerdictForJudgement = null,
            )
        }
    }

    fun onJudgementDialogDismiss() {
        _uiState.update { it.copy(selectedAssignedVerdictForJudgement = null) }
    }

    fun onSubmitJudgement(isGuilty: Boolean) {
        val verdict = _uiState.value.selectedAssignedVerdictForJudgement ?: return
        val verdictType = if (isGuilty) VerdictType.Guilty else VerdictType.NotGuilty

        viewModelScope.launch {
            _uiState.update { it.copy(selectedAssignedVerdictForJudgement = null) }

            judgeVerdictUseCase(verdict.id, verdictType)
                .onSuccess {
                    _effect.emit(VerdictEffect.NavigateToCompleted(verdict.defendant.nickname))
                    loadVerdicts()
                }
                .onFailure { e ->
                    Timber.e(e, "판결 실패: id=${verdict.id}")
                    _effect.emit(
                        VerdictEffect.ShowSnackBar(
                            e.message ?: "판결 처리 중 오류가 발생했습니다"
                        )
                    )
                }
        }
    }

    fun onDismissBottomSheet() {
        _uiState.update {
            it.copy(
                selectedAssignedVerdict = null,
                selectedRequestedVerdict = null,
            )
        }
    }

    fun loadVerdicts() {
        viewModelScope.launch {
            getJurorVerdictsUseCase()
                .onSuccess { assignedVerdicts ->
                    allAssignedVerdicts = assignedVerdicts.map { it.toUiModel() }
                        .sortedByDescending { it.id }
                    _uiState.update { it.applyFilters() }
                }
                .onFailure { e ->
                    Timber.e(e, "내 판결 로드 실패")
                }
        }
        viewModelScope.launch {
            getMyVerdictsUseCase()
                .onSuccess { requestedVerdicts ->
                    allRequestedVerdicts = requestedVerdicts.map { it.toUiModel() }
                        .sortedByDescending { it.id }
                    _uiState.update { it.applyFilters() }
                }
                .onFailure { e ->
                    Timber.e(e, "내 심판 로드 실패")
                }
        }
    }

    private fun calculateAssignedCounts(verdicts: List<AssignedVerdictUiModel>): VerdictCounts {
        return VerdictCounts(
            total = verdicts.size,
            pending = verdicts.count { it.verdictType == VerdictType.Pending },
            completed = verdicts.count { it.verdictType != VerdictType.Pending }
        )
    }

    private fun calculateRequestedCounts(verdicts: List<RequestedVerdictUiModel>): VerdictCounts {
        return VerdictCounts(
            total = verdicts.size,
            pending = verdicts.count { it.verdictType == VerdictType.Pending },
            completed = verdicts.count { it.verdictType != VerdictType.Pending }
        )
    }

    private fun filterAssignedVerdicts(
        verdicts: List<AssignedVerdictUiModel>,
        filterIndex: Int,
    ): List<AssignedVerdictUiModel> {
        return when (filterIndex) {
            0 -> verdicts
            1 -> verdicts.filter { it.verdictType == VerdictType.Pending }
            2 -> verdicts.filter { it.verdictType != VerdictType.Pending }
            else -> verdicts
        }
    }

    private fun filterRequestedVerdicts(
        verdicts: List<RequestedVerdictUiModel>,
        filterIndex: Int,
    ): List<RequestedVerdictUiModel> {
        return when (filterIndex) {
            0 -> verdicts
            1 -> verdicts.filter { it.verdictType == VerdictType.Pending }
            2 -> verdicts.filter { it.verdictType != VerdictType.Pending }
            else -> verdicts
        }
    }
}

object TabIndex {
    const val REQUESTED = 0
    const val ASSIGNED = 1
}

data class VerdictUiState(
    val selectedTabIndex: Int = TabIndex.REQUESTED,
    val userNickname: String = "유저 닉네임",
    val isRefreshing: Boolean = false,
    val selectedAssignedVerdict: AssignedVerdictUiModel? = null,
    val selectedRequestedVerdict: RequestedVerdictUiModel? = null,
    val selectedAssignedVerdictForJudgement: AssignedVerdictUiModel? = null,
    val requestedVerdicts: RequestedVerdictListState = RequestedVerdictListState(),
    val assignedVerdicts: AssignedVerdictListState = AssignedVerdictListState(),
)

data class RequestedVerdictListState(
    val filterIndex: Int = 0,
    val items: List<RequestedVerdictUiModel> = emptyList(),
    val counts: VerdictCounts = VerdictCounts(),
)

data class AssignedVerdictListState(
    val filterIndex: Int = 0,
    val items: List<AssignedVerdictUiModel> = emptyList(),
    val counts: VerdictCounts = VerdictCounts(),
)

sealed interface VerdictEffect {
    data object NavigateToRequest : VerdictEffect
    data class NavigateToResult(val id: Long) : VerdictEffect
    data class NavigateToJurorDetail(val id: Long) : VerdictEffect
    data class NavigateToCompleted(val defendantNickname: String) : VerdictEffect
    data class ShowSnackBar(val message: String) : VerdictEffect
}
