package com.smtm.pickle.presentation.mypage

data class MyPageUiState(
    val profile: ProfileState = ProfileState(),
    val statistics: StatisticsState = StatisticsState(),
    val activity: ActivityState = ActivityState(),
) {
    data class ProfileState(
        val nickname: String = "",
        val badgeName: String = "",
        val invitationCode: String = "",
    )

    data class StatisticsState(
        val selectedTabIndex: Int = 0,
        val expenditure: StatisticsDetailState = StatisticsDetailState(),
        val income: StatisticsDetailState = StatisticsDetailState(),
    )

    data class StatisticsDetailState(
        val totalAmount: Long = 0L,
        val comparedToPreviousMonth: Long = 0L,
        val month: Int = 1,
        val chartItems: List<ChartItemState> = emptyList(),
    )

    data class ChartItemState(
        val label: String,
        val value: Float,
        val colorHex: Long,
    )

    data class ActivityState(
        val pendingJudgments: List<PendingJudgmentState> = emptyList(),
    )

    data class PendingJudgmentState(
        val id: String,
        val title: String,
        val price: Long,
        val iconRes: Int,
    )
}
