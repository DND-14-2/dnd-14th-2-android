package com.smtm.pickle.presentation.mypage

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.components.profile.MyPageProfileSection
import com.smtm.pickle.presentation.mypage.tabs.statistics.StatisticsTab

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    onNavigateMyLedger: () -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateAlarmSetting: () -> Unit,
    onNavigateMyProfile: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    MyPageContent(
        uiState = uiState,
        onNavigateMyLedger = onNavigateMyLedger,
        onNavigateSetting = onNavigateSetting,
        onNavigateMyProfile = onNavigateMyProfile,
        onStatisticsTabSelected = viewModel::onStatisticsTabSelected,
    )
}

@Composable
private fun MyPageContent(
    uiState: MyPageUiState,
    onNavigateMyLedger: () -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateMyProfile: () -> Unit,
    onStatisticsTabSelected: (Int) -> Unit
) {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.background50)
        ) {
            stickyHeader("top_bar") {
                PickleAppBar(
                    title = "마이페이지",
                    actions = {
                        PickleIconButtonWithTouchCustom(
                            iconRes = R.drawable.ic_appbar_setting,
                            contentDescription = "설정",
                            onClick = onNavigateSetting,
                            iconSize = 48.dp,
                        )
                    },
                    isInScaffold = false
                )
            }

            item("profile") {
                MyPageProfileSection(
                    modifier = Modifier.background(PickleTheme.colors.base0),
                    nickname = uiState.profile.nickname,
                    onNicknameEditClick = onNavigateMyProfile,
                )
            }

            item("tab") {
                StatisticsTab(
                    statisticsState = uiState.statistics,
                    onMyLedgerClick = onNavigateMyLedger,
                    onTabSelected = onStatisticsTabSelected,
                )
            }
        }
    }
}


@Preview
@Composable
private fun MyPageScreenPreview() {
    PickleTheme {
        MyPageContent(
            uiState = MyPageUiState(),
            onNavigateMyLedger = { },
            onNavigateSetting = { },
            onNavigateMyProfile = { },
            onStatisticsTabSelected = { },
        )
    }
}
