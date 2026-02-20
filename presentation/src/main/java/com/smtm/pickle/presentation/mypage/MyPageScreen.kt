package com.smtm.pickle.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
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
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is MyPageEffect.ShowSnackBar -> {
                        snackbarState.show(
                            PickleSnackbar.snackbarShort(
                                message = effect.msg,
                            )
                        )
                    }
                }
            }
        }
    }

    MyPageContent(
        uiState = uiState,
        onNavigateMyLedger = onNavigateMyLedger,
        onNavigateSetting = onNavigateSetting,
        onNavigateMyProfile = onNavigateMyProfile,
        onStatisticsTabSelected = viewModel::onStatisticsTabSelected,
    )

    SnackbarHost(snackbarState = snackbarState)
}

@Composable
private fun MyPageContent(
    uiState: MyPageUiState,
    onNavigateMyLedger: () -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateMyProfile: () -> Unit,
    onStatisticsTabSelected: (Int) -> Unit
) {
    Scaffold(
        topBar = {
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
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PickleTheme.colors.background50)
        ) {
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
