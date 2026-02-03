package com.smtm.pickle.presentation.mypage

import androidx.lifecycle.ViewModel
import com.smtm.pickle.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(createMockUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    fun onStatisticsTabSelected(index: Int) {
        _uiState.value = _uiState.value.copy(
            statistics = _uiState.value.statistics.copy(selectedTabIndex = index)
        )
    }

    private fun createMockUiState(): MyPageUiState {
        return MyPageUiState(
            profile = MyPageUiState.ProfileState(
                nickname = "유저 닉네임",
                badgeName = "배지명",
                invitationCode = "PICKLE2024"
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
}
