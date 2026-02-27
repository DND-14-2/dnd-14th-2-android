package com.smtm.pickle.presentation.verdict

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.verdict.VerdictType
import com.smtm.pickle.domain.usecase.nickname.ObserveNicknameUseCase
import com.smtm.pickle.domain.usecase.verdict.GetJurorVerdictsUseCase
import com.smtm.pickle.domain.usecase.verdict.GetMyVerdictsUseCase
import com.smtm.pickle.domain.usecase.verdict.JudgeVerdictUseCase
import com.smtm.pickle.presentation.verdict.model.JurorVerdictUiModel
import com.smtm.pickle.presentation.verdict.model.MyVerdictUiModel
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

    private var allJurorVerdicts: List<JurorVerdictUiModel> = emptyList()
    private var allMyVerdicts: List<MyVerdictUiModel> = emptyList()

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
            val updated = if (state.selectedTabIndex == TabIndex.JUDGEMENTS) {
                state.copy(judgements = state.judgements.copy(filterIndex = index))
            } else {
                state.copy(verdicts = state.verdicts.copy(filterIndex = index))
            }
            updated.applyFilters()
        }
    }

    private fun VerdictUiState.applyFilters(): VerdictUiState {
        return copy(
            judgements = judgements.copy(
                items = filterJurorVerdicts(allJurorVerdicts, judgements.filterIndex),
                counts = calculateJurorCounts(allJurorVerdicts)
            ),
            verdicts = verdicts.copy(
                items = filterMyVerdicts(allMyVerdicts, verdicts.filterIndex),
                counts = calculateMyCounts(allMyVerdicts)
            )
        )
    }

    fun onJurorVerdictItemClick(verdict: JurorVerdictUiModel) {
        when (verdict.verdictType) {
            VerdictType.Pending -> {
                _uiState.update {
                    it.copy(
                        selectedJurorVerdictForJudgement = verdict,
                        selectedMyVerdict = null,
                    )
                }
            }

            VerdictType.Guilty, VerdictType.NotGuilty -> {
                _uiState.update {
                    it.copy(
                        selectedJurorVerdict = verdict,
                        selectedMyVerdict = null,
                    )
                }
            }
        }
    }

    fun onMyVerdictItemClick(verdict: MyVerdictUiModel) {
        _uiState.update {
            it.copy(
                selectedMyVerdict = verdict,
                selectedJurorVerdict = null,
                selectedJurorVerdictForJudgement = null,
            )
        }
    }

    fun onJudgementDialogDismiss() {
        _uiState.update { it.copy(selectedJurorVerdictForJudgement = null) }
    }

    fun onSubmitJudgement(isGuilty: Boolean) {
        val verdict = _uiState.value.selectedJurorVerdictForJudgement ?: return
        val verdictType = if (isGuilty) VerdictType.Guilty else VerdictType.NotGuilty

        viewModelScope.launch {
            _uiState.update { it.copy(selectedJurorVerdictForJudgement = null) }

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
                selectedJurorVerdict = null,
                selectedMyVerdict = null,
            )
        }
    }

    fun navigateToJurorList() {
        viewModelScope.launch {
            _effect.emit(VerdictEffect.NavigateToJurorList)
        }
    }

    private fun loadVerdicts() {
        viewModelScope.launch {
            getJurorVerdictsUseCase()
                .onSuccess { jurorVerdicts ->
                    allJurorVerdicts = jurorVerdicts.map { it.toUiModel() }
                    _uiState.update { it.applyFilters() }
                }
                .onFailure { e ->
                    Timber.e(e, "배심원 평결 로드 실패")
                }
        }
        viewModelScope.launch {
            getMyVerdictsUseCase()
                .onSuccess { myVerdicts ->
                    allMyVerdicts = myVerdicts.map { it.toUiModel() }
                    _uiState.update { it.applyFilters() }
                }
                .onFailure { e ->
                    Timber.e(e, "내 평가 로드 실패")
                }
        }
    }

    private fun calculateJurorCounts(verdicts: List<JurorVerdictUiModel>): VerdictCounts {
        return VerdictCounts(
            total = verdicts.size,
            pending = verdicts.count { it.verdictType == VerdictType.Pending },
            completed = verdicts.count { it.verdictType != VerdictType.Pending }
        )
    }

    private fun calculateMyCounts(verdicts: List<MyVerdictUiModel>): VerdictCounts {
        return VerdictCounts(
            total = verdicts.size,
            pending = verdicts.count { it.verdictType == VerdictType.Pending },
            completed = verdicts.count { it.verdictType != VerdictType.Pending }
        )
    }

    private fun filterJurorVerdicts(
        verdicts: List<JurorVerdictUiModel>,
        filterIndex: Int,
    ): List<JurorVerdictUiModel> {
        return when (filterIndex) {
            0 -> verdicts
            1 -> verdicts.filter { it.verdictType == VerdictType.Pending }
            2 -> verdicts.filter { it.verdictType != VerdictType.Pending }
            else -> verdicts
        }
    }

    private fun filterMyVerdicts(
        verdicts: List<MyVerdictUiModel>,
        filterIndex: Int,
    ): List<MyVerdictUiModel> {
        return when (filterIndex) {
            0 -> verdicts
            1 -> verdicts.filter { it.verdictType == VerdictType.Pending }
            2 -> verdicts.filter { it.verdictType != VerdictType.Pending }
            else -> verdicts
        }
    }
}

object TabIndex {
    const val JUDGEMENTS = 0
    const val VERDICTS = 1
}

data class VerdictUiState(
    val selectedTabIndex: Int = TabIndex.JUDGEMENTS,
    val userNickname: String = "유저 닉네임",
    val selectedJurorVerdict: JurorVerdictUiModel? = null,
    val selectedMyVerdict: MyVerdictUiModel? = null,
    val selectedJurorVerdictForJudgement: JurorVerdictUiModel? = null,
    val judgements: JurorVerdictListState = JurorVerdictListState(),
    val verdicts: MyVerdictListState = MyVerdictListState(),
)

data class JurorVerdictListState(
    val filterIndex: Int = 0,
    val items: List<JurorVerdictUiModel> = emptyList(),
    val counts: VerdictCounts = VerdictCounts(),
)

data class MyVerdictListState(
    val filterIndex: Int = 0,
    val items: List<MyVerdictUiModel> = emptyList(),
    val counts: VerdictCounts = VerdictCounts(),
)

sealed interface VerdictEffect {
    data object NavigateToRequest : VerdictEffect
    data object NavigateToJurorList : VerdictEffect
    data class NavigateToResult(val id: Long) : VerdictEffect
    data class NavigateToJurorDetail(val id: Long) : VerdictEffect
    data class NavigateToCompleted(val defendantNickname: String) : VerdictEffect
    data class ShowSnackBar(val message: String) : VerdictEffect
}
