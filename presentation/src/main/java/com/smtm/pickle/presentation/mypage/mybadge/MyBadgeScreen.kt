package com.smtm.pickle.presentation.mypage.mybadge

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleBottomSheet
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.mybadge.components.MyBadgeList
import com.smtm.pickle.presentation.mypage.mybadge.components.MyBadgeSummary
import com.smtm.pickle.presentation.mypage.mybadge.components.MyPageBadgeBottomSheetContent
import com.smtm.pickle.presentation.mypage.mybadge.model.BadgeType
import com.smtm.pickle.presentation.mypage.mybadge.model.BadgeUiState

@Composable
fun MyBadgeScreen(
    viewModel: MyBadgeViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is MyBadgeViewModel.MyBadgeEffect.NavigateBack -> onNavigateBack()
                    is MyBadgeViewModel.MyBadgeEffect.ShowSnackBar -> {
                        PickleSnackbar.toastSuccess(message = effect.msg)
                    }
                }
            }
        }
    }

    MyBadgeContent(
        uiState = uiState,
        onBadgeClick = viewModel::changeBadge,
        onBackClick = viewModel::onBackClick
    )

    SnackbarHost(snackbarState = snackbarState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyBadgeContent(
    modifier: Modifier = Modifier,
    uiState: List<BadgeUiState>,
    onBadgeClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    var clickedBadgeId by remember { mutableStateOf<Int?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    val clickedBadge = remember(clickedBadgeId) {
        uiState.find { it.type.id == clickedBadgeId }
    }

    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(PickleTheme.colors.base0),
        ) {
            stickyHeader("top_bar") {
                PickleAppBar(
                    title = stringResource(R.string.my_badge_title),
                    navigationItem = NavigationItem.Back(onClick = onBackClick)
                )
            }

            item("my_badge_summary") {
                MyBadgeSummary()
            }

            item("divider") {
                HorizontalDivider(
                    thickness = 10.dp,
                    color = PickleTheme.colors.background50
                )
            }

            item("my_badge_list") {
                MyBadgeList(
                    badges = uiState,
                    onBadgeClick = { id ->
                        clickedBadgeId = id
                        showBottomSheet = true
                    }
                )
            }
        }
    }

    if (showBottomSheet && clickedBadge != null) {
        PickleBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
            onDismiss = {
                showBottomSheet = false
                clickedBadgeId = null
            }
        ) {
            MyPageBadgeBottomSheetContent(
                badgeName = clickedBadge.type.badgeName,
                description = "배지 설명", // TODO: BadgeType에 description 추가 필요
                onBadgeChangeClick = {
                    onBadgeClick(clickedBadge.type.id)
                    showBottomSheet = false
                    clickedBadgeId = null
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyBadgeContentPreview() {
    PickleTheme {
        MyBadgeContent(
            uiState = listOf(
                BadgeUiState(BadgeType.DEFAULT, isSelected = true),
                BadgeUiState(BadgeType.FIRST_JUDGMENT),
                BadgeUiState(BadgeType.JUDGMENT_MASTER),
            ),
            onBadgeClick = {},
            onBackClick = {}
        )
    }
}
