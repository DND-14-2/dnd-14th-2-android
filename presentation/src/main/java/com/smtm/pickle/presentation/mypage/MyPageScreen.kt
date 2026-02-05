package com.smtm.pickle.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.components.MyPageTabSection
import com.smtm.pickle.presentation.mypage.components.profile.MyPageProfileSection

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    onNavigateMyLedger: () -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateAlarmSetting: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    MyPageContent(
        uiState = uiState,
        onNavigateMyLedger = onNavigateMyLedger,
        onStatisticsTabSelected = viewModel::onStatisticsTabSelected,
    )
}

@Composable
private fun MyPageContent(
    uiState: MyPageUiState,
    onNavigateMyLedger: () -> Unit,
    onStatisticsTabSelected: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PickleTheme.colors.background50)
            .verticalScroll(scrollState)
    ) {
        MyPageProfileSection(
            modifier = Modifier.background(PickleTheme.colors.base0),
            nickname = uiState.profile.nickname,
            badgeName = uiState.profile.badgeName,
            invitationCode = uiState.profile.invitationCode,
            onNicknameEditClick = {},
            onMyJuryClick = {},
            onMyBadgeClick = {},
        )
        MyPageTabSection(
            statisticsState = uiState.statistics,
            activityState = uiState.activity,
            onNavigateMyLedger = onNavigateMyLedger,
            onStatisticsTabSelected = onStatisticsTabSelected,
        )
    }
}


@Preview
@Composable
private fun MyPageScreenPreview() {
    PickleTheme {
        MyPageContent(
            uiState = MyPageUiState(),
            onNavigateMyLedger = { },
            onStatisticsTabSelected = { },
        )
    }
}
