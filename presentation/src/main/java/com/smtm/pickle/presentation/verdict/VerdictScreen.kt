package com.smtm.pickle.presentation.verdict

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.verdict.model.VerdictTypeUiModel
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.designsystem.components.PickleBottomSheet
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.verdict.components.EmptyVerdictContent
import com.smtm.pickle.presentation.verdict.components.JudgementDialog
import com.smtm.pickle.presentation.verdict.components.VerdictListItem
import com.smtm.pickle.presentation.verdict.components.VerdictPendingBottomSheetContent
import com.smtm.pickle.presentation.verdict.components.VerdictTabs
import com.smtm.pickle.presentation.verdict.model.AssignedVerdictUiModel
import com.smtm.pickle.presentation.verdict.model.LedgerEntryUiModel
import com.smtm.pickle.presentation.verdict.model.RequestedVerdictUiModel
import com.smtm.pickle.presentation.verdict.model.VerdictCounts

private val defaultPadding: Dp = 16.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerdictScreen(
    viewModel: VerdictViewModel = hiltViewModel(),
    onNavigateJurorList: () -> Unit,
    onNavigateVerdictRequest: () -> Unit,
    onNavigateVerdictResult: (Long) -> Unit,
    onNavigateJurorDetail: (Long) -> Unit,
    onNavigateVerdictCompleted: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    val snackbarState = remember { SnackbarState() }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { true }
    )

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    VerdictEffect.NavigateToRequest -> onNavigateVerdictRequest()
                    is VerdictEffect.NavigateToResult -> onNavigateVerdictResult(effect.id)
                    is VerdictEffect.NavigateToJurorDetail -> onNavigateJurorDetail(effect.id)
                    is VerdictEffect.NavigateToCompleted -> onNavigateVerdictCompleted(effect.defendantNickname)
                    is VerdictEffect.ShowSnackBar -> {
                        snackbarState.show(
                            PickleSnackbar.toastError(effect.message)
                        )
                    }
                }
            }
        }
    }

    // 내 판결 BottomSheet (완료된 것 클릭 시)
    val selectedAssignedVerdict = uiState.selectedAssignedVerdict
    if (selectedAssignedVerdict != null) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = viewModel::onDismissBottomSheet,
        ) {
            VerdictPendingBottomSheetContent(
                modifier = Modifier,
                jurorNickname = uiState.userNickname,
                defendantNickname = selectedAssignedVerdict.defendant.nickname,
                title = selectedAssignedVerdict.ledgerEntry.description,
                category = selectedAssignedVerdict.ledgerEntry.category,
                amount = selectedAssignedVerdict.ledgerEntry.amount,
                paymentMethod = selectedAssignedVerdict.ledgerEntry.paymentMethod,
                verdictType = selectedAssignedVerdict.verdictType,
            )
        }
    }

    // 내 심판 BottomSheet
    val selectedRequestedVerdict = uiState.selectedRequestedVerdict
    if (selectedRequestedVerdict != null) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = viewModel::onDismissBottomSheet,
        ) {
            VerdictPendingBottomSheetContent(
                modifier = Modifier,
                // TODO: 서버에서 jurorNickname 추가 후 변경
                jurorNickname = "익명 배심원",
                defendantNickname = uiState.userNickname,
                title = selectedRequestedVerdict.ledgerEntry.description,
                category = selectedRequestedVerdict.ledgerEntry.category,
                amount = selectedRequestedVerdict.ledgerEntry.amount,
                paymentMethod = selectedRequestedVerdict.ledgerEntry.paymentMethod,
                verdictType = selectedRequestedVerdict.verdictType,
            )
        }
    }

    // 판결 다이얼로그
    val selectedVerdictForJudgement = uiState.selectedAssignedVerdictForJudgement
    if (selectedVerdictForJudgement != null) {
        JudgementDialog(
            onDismiss = viewModel::onJudgementDialogDismiss,
            onGuiltyClick = { viewModel.onSubmitJudgement(isGuilty = true) },
            onInnocentClick = { viewModel.onSubmitJudgement(isGuilty = false) }
        )
    }

    VerdictContent(
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::loadVerdicts,
        selectedTabIndex = uiState.selectedTabIndex,
        requestedFilterIndex = uiState.requestedVerdicts.filterIndex,
        assignedFilterIndex = uiState.assignedVerdicts.filterIndex,
        requestedCounts = uiState.requestedVerdicts.counts,
        assignedCounts = uiState.assignedVerdicts.counts,
        requestedVerdictItems = uiState.requestedVerdicts.items,
        assignedVerdictItems = uiState.assignedVerdicts.items,
        onJurorListClick = onNavigateJurorList,
        onTabSelected = viewModel::onTabSelected,
        onFilterSelected = viewModel::onFilterSelected,
        onAssignedVerdictItemClick = viewModel::onAssignedVerdictItemClick,
        onRequestedVerdictItemClick = viewModel::onRequestedVerdictItemClick,
    )

    SnackbarHost(snackbarState)
}

@Composable
private fun VerdictContent(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    selectedTabIndex: Int,
    requestedFilterIndex: Int,
    assignedFilterIndex: Int,
    requestedCounts: VerdictCounts,
    assignedCounts: VerdictCounts,
    requestedVerdictItems: List<RequestedVerdictUiModel>,
    assignedVerdictItems: List<AssignedVerdictUiModel>,
    onJurorListClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFilterSelected: (Int) -> Unit,
    onAssignedVerdictItemClick: (AssignedVerdictUiModel) -> Unit,
    onRequestedVerdictItemClick: (RequestedVerdictUiModel) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullToRefreshState,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(Dimensions.pullToRefreshIndicatorSize),
                color = PickleTheme.colors.primary300,
                containerColor = PickleTheme.colors.primary50,
                maxDistance = Dimensions.pullToRefreshDistance
            )
        }
    ) {
        Scaffold(
            topBar = {
                PickleAppBar(title = "심판") {
                    PickleIconButtonWithTouchCustom(
                        iconRes = R.drawable.ic_verdict_juror,
                        onClick = onJurorListClick
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }

                item("tabs") {
                    VerdictTabs(
                        selectedTabIndex = selectedTabIndex,
                        requestedFilterIndex = requestedFilterIndex,
                        assignedFilterIndex = assignedFilterIndex,
                        requestedCounts = requestedCounts,
                        assignedCounts = assignedCounts,
                        onTabSelected = onTabSelected,
                        onFilterSelected = onFilterSelected,
                        modifier = Modifier.padding(horizontal = defaultPadding)
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                when (selectedTabIndex) {
                    TabIndex.REQUESTED -> {
                        if (requestedVerdictItems.isEmpty()) {
                            item { EmptyVerdictContent(selectedTabIndex = selectedTabIndex) }
                        } else {
                            itemsIndexed(
                                items = requestedVerdictItems,
                                key = { _, item -> item.id }
                            ) { index, item ->
                                VerdictListItem(
                                    amount = item.ledgerEntry.amount,
                                    description = item.ledgerEntry.description,
                                    categoryIconResId = item.ledgerEntry.category.iconResId,
                                    paymentMethodIconResId = item.ledgerEntry.paymentMethod.iconResId,
                                    verdictType = item.verdictType,
                                    onItemClick = { onRequestedVerdictItemClick(item) },
                                    modifier = Modifier.padding(horizontal = defaultPadding)
                                )
                                if (index < requestedVerdictItems.lastIndex)
                                    Spacer(modifier = Modifier.height(12.dp))
                                else
                                    Spacer(modifier = Modifier.height(80.dp))
                            }
                        }
                    }

                    TabIndex.ASSIGNED -> {
                        if (assignedVerdictItems.isEmpty()) {
                            item { EmptyVerdictContent(selectedTabIndex = selectedTabIndex) }
                        } else {
                            itemsIndexed(
                                items = assignedVerdictItems,
                                key = { _, item -> item.id }
                            ) { index, item ->
                                VerdictListItem(
                                    amount = item.ledgerEntry.amount,
                                    description = item.ledgerEntry.description,
                                    categoryIconResId = item.ledgerEntry.category.iconResId,
                                    paymentMethodIconResId = item.ledgerEntry.paymentMethod.iconResId,
                                    verdictType = item.verdictType,
                                    onItemClick = { onAssignedVerdictItemClick(item) },
                                    modifier = Modifier.padding(horizontal = defaultPadding)
                                )
                                if (index < assignedVerdictItems.lastIndex)
                                    Spacer(modifier = Modifier.height(12.dp))
                                else
                                    Spacer(modifier = Modifier.height(80.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun VerdictContentPreview() {
    val mockLedgerEntry = LedgerEntryUiModel(
        id = 101L,
        amount = 15000L,
        category = CategoryUiModel.Food,
        paymentMethod = PaymentMethodUiModel.Cash,
        description = "가계부 15자 입력"
    )
    val mockRequestedItems = listOf(
        RequestedVerdictUiModel(
            id = 1,
            ledgerEntry = mockLedgerEntry,
            verdictType = VerdictTypeUiModel.Pending,
        ),
        RequestedVerdictUiModel(
            id = 2,
            ledgerEntry = mockLedgerEntry.copy(id = 102L, amount = 5000L, description = "커피 한잔"),
            verdictType = VerdictTypeUiModel.Guilty,
        ),
        RequestedVerdictUiModel(
            id = 3,
            ledgerEntry = mockLedgerEntry.copy(id = 103L, amount = 25000L, description = "야식 치킨"),
            verdictType = VerdictTypeUiModel.NotGuilty,
        )
    )
    PickleTheme {
        VerdictContent(
            isRefreshing = false,
            onRefresh = {},
            selectedTabIndex = 0,
            requestedFilterIndex = 0,
            assignedFilterIndex = 0,
            requestedCounts = VerdictCounts(10, 5, 5),
            assignedCounts = VerdictCounts(5, 3, 2),
            requestedVerdictItems = mockRequestedItems,
            assignedVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onAssignedVerdictItemClick = {},
            onRequestedVerdictItemClick = {},
        )
    }
}

@Preview(name = "Empty Requested")
@Composable
private fun VerdictContentEmptyRequestedPreview() {
    PickleTheme {
        VerdictContent(
            isRefreshing = false,
            onRefresh = {},
            selectedTabIndex = 0,
            requestedFilterIndex = 0,
            assignedFilterIndex = 0,
            requestedCounts = VerdictCounts(0, 0, 0),
            assignedCounts = VerdictCounts(0, 0, 0),
            requestedVerdictItems = emptyList(),
            assignedVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onAssignedVerdictItemClick = {},
            onRequestedVerdictItemClick = {},
        )
    }
}

@Preview(name = "Empty Assigned")
@Composable
private fun VerdictContentEmptyAssignedPreview() {
    PickleTheme {
        VerdictContent(
            isRefreshing = false,
            onRefresh = {},
            selectedTabIndex = 1,
            requestedFilterIndex = 0,
            assignedFilterIndex = 0,
            requestedCounts = VerdictCounts(0, 0, 0),
            assignedCounts = VerdictCounts(0, 0, 0),
            requestedVerdictItems = emptyList(),
            assignedVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onAssignedVerdictItemClick = {},
            onRequestedVerdictItemClick = {},
        )
    }
}
