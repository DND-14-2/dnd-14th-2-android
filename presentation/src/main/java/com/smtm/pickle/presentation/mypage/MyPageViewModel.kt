package com.smtm.pickle.presentation.mypage

import androidx.lifecycle.ViewModel
import com.smtm.pickle.presentation.R
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.nickname.CheckNicknameAvailableUseCase
import com.smtm.pickle.domain.usecase.nickname.GetNicknameUseCase
import com.smtm.pickle.domain.usecase.nickname.SaveNicknameUseCase
import com.smtm.pickle.presentation.common.constant.NicknameValidation
import com.smtm.pickle.presentation.common.utils.NicknameUtils
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val checkNicknameAvailableUseCase: CheckNicknameAvailableUseCase,
    private val saveNicknameUseCase: SaveNicknameUseCase,
    getNicknameUseCase: GetNicknameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(createMockUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<MyPageEffect>(replay = 0)
    val effect: SharedFlow<MyPageEffect> = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            getNicknameUseCase().collect { nickname ->
                _uiState.update { state ->
                    state.copy(
                        profile = state.profile.copy(nickname = nickname)
                    )
                }
            }
        }
    }

    fun onStatisticsTabSelected(index: Int) {
        _uiState.update {
            it.copy(
                statistics = it.statistics.copy(selectedTabIndex = index)
            )
        }
    }

    fun onNicknameChanged(nickname: String) {
        val correctNickname = nickname.take(NicknameValidation.MAX_NICKNAME_LENGTH)

        _uiState.update { state ->
            state.copy(
                profile = state.profile.copy(
                    editingNickname = correctNickname,
                    inputState = NicknameUtils.validateNicknameFormat(correctNickname),
                    isCheckingDuplicate = false,
                    isAvailable = null,
                    isNicknameModified = true
                )
            )
        }
    }

    fun checkDuplicate() {
        val state = uiState.value
        if (state.profile.inputState !is InputState.Success) return
        val requestedNickname = state.profile.editingNickname

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    profile = it.profile.copy(
                        isCheckingDuplicate = true,
                        isAvailable = null,
                    )
                )
            }

            val isAvailable = checkNicknameAvailableUseCase(requestedNickname)
                .onFailure { e -> Timber.e(e, "닉네임 중복 체크 실패") }
                .getOrDefault(false)

            _uiState.update {
                if (it.profile.editingNickname != requestedNickname) return@update it

                it.copy(
                    profile = it.profile.copy(
                        isCheckingDuplicate = false,
                        isAvailable = isAvailable,
                        inputState = if (isAvailable) {
                            InputState.Success("사용 가능한 닉네임이에요!")
                        } else {
                            InputState.Error("이미 사용중인 닉네임이에요.")
                        }
                    )
                )
            }
        }
    }

    fun saveNickname() {
        viewModelScope.launch {
            saveNicknameUseCase(uiState.value.profile.editingNickname)
                .onSuccess {
                    _effect.emit(MyPageEffect.NavigateBack)
                }
                .onFailure { e ->
                    Timber.e(e, "닉네임 저장 실패")
                }
        }
    }

    fun startNicknameEditing() {
        _uiState.update { state ->
            state.copy(
                profile = state.profile.copy(
                    editingNickname = state.profile.nickname,
                    inputState = InputState.Idle,
                    isCheckingDuplicate = false,
                    isAvailable = null,
                    isNicknameModified = false
                )
            )
        }
    }

    private fun validateFormat(nickname: String): InputState =
        NicknameUtils.validateNicknameFormat(nickname)

    private fun createMockUiState(): MyPageUiState {
        return MyPageUiState(
            profile = MyPageUiState.ProfileState(
                nickname = "유저 닉네임",
                editingNickname = "유저 닉네임",
                badgeName = "배지명",
                invitationCode = "PICKLE2026",
                inputState = InputState.Idle
            ),
            statistics = MyPageUiState.StatisticsState(
                selectedTabIndex = 0,
                expenditure = MyPageUiState.StatisticsDetailState(
                    totalAmount = 1_250_000L,
                    comparedToPreviousMonth = 35_000L,
                    month = 2,
                    chartItems = listOf(
                        MyPageUiState.ChartItemState("저축/금융", 28f, 0xFFFF9429),
                        MyPageUiState.ChartItemState("식비", 30f, 0xFF2BC4C1),
                        MyPageUiState.ChartItemState("쇼핑", 20f, 0xFFFF70A7),
                        MyPageUiState.ChartItemState("교통비", 15f, 0xFFFFDD52),
                        MyPageUiState.ChartItemState("여가/취미", 12f, 0xFFB362FF),
                        MyPageUiState.ChartItemState("주거비", 10f, 0xFF4493FF),
                        MyPageUiState.ChartItemState("의료/건강", 8f, 0xFF63C3FF),
                        MyPageUiState.ChartItemState("기타", 5f, 0xFFAAAAAA),
                        MyPageUiState.ChartItemState("교육/자기계발", 4f, 0xFF75C375),
                    )
                ),
                income = MyPageUiState.StatisticsDetailState(
                    totalAmount = 3_200_000L,
                    comparedToPreviousMonth = 200_000L,
                    month = 2,
                    chartItems = emptyList()
                )
            ),
            activity = MyPageUiState.ActivityState(
                pendingJudgments = listOf(
                    MyPageUiState.PendingJudgmentState(
                        id = "1",
                        title = "식비",
                        price = 10000L,
                        iconRes = R.drawable.ic_mypage_coin
                    ),
                    MyPageUiState.PendingJudgmentState(
                        id = "2",
                        title = "식비",
                        price = 10000L,
                        iconRes = R.drawable.ic_mypage_coin
                    ),
                    MyPageUiState.PendingJudgmentState(
                        id = "3",
                        title = "식비",
                        price = 10000L,
                        iconRes = R.drawable.ic_mypage_coin
                    ),
                    MyPageUiState.PendingJudgmentState(
                        id = "4",
                        title = "식비",
                        price = 10000L,
                        iconRes = R.drawable.ic_mypage_coin
                    ),
                    MyPageUiState.PendingJudgmentState(
                        id = "5",
                        title = "식비",
                        price = 10000L,
                        iconRes = R.drawable.ic_mypage_coin
                    )
                )
            ),
        )
    }

    sealed interface MyPageEffect {
        data object NavigateBack : MyPageEffect
    }
}
