package com.smtm.pickle.presentation.verdict

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.domain.model.verdict.VerdictType
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.designsystem.components.PickleBottomSheet
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.verdict.components.EmptyVerdictContent
import com.smtm.pickle.presentation.verdict.components.JudgementDialog
import com.smtm.pickle.presentation.verdict.components.VerdictListItem
import com.smtm.pickle.presentation.verdict.components.VerdictPendingBottomSheetContent
import com.smtm.pickle.presentation.verdict.components.VerdictTabs
import com.smtm.pickle.presentation.verdict.model.JurorVerdictUiModel
import com.smtm.pickle.presentation.verdict.model.LedgerEntryUiModel
import com.smtm.pickle.presentation.verdict.model.MateUiModel
import com.smtm.pickle.presentation.verdict.model.MyVerdictUiModel
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

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { true }
    )

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    VerdictEffect.NavigateToRequest -> onNavigateVerdictRequest()
                    VerdictEffect.NavigateToJurorList -> onNavigateJurorList()
                    is VerdictEffect.NavigateToResult -> onNavigateVerdictResult(effect.id)
                    is VerdictEffect.NavigateToJurorDetail -> onNavigateJurorDetail(effect.id)
                    is VerdictEffect.NavigateToCompleted -> onNavigateVerdictCompleted(effect.defendantNickname)
                }
            }
        }
    }

    // 배심원 심판 BottomSheet (완료된 것 클릭 시)
    val selectedJurorVerdict = uiState.selectedJurorVerdict
    if (selectedJurorVerdict != null) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = viewModel::onDismissBottomSheet,
        ) {
            VerdictPendingBottomSheetContent(
                modifier = Modifier,
                jurorNickname = uiState.userNickname,
                defendantNickname = selectedJurorVerdict.defendant.nickname,
                title = selectedJurorVerdict.ledgerEntry.description,
                category = selectedJurorVerdict.ledgerEntry.category,
                amount = selectedJurorVerdict.ledgerEntry.amount,
                paymentMethod = selectedJurorVerdict.ledgerEntry.paymentMethod,
                verdictType = selectedJurorVerdict.verdictType,
            )
        }
    }

    // 내 심판 BottomSheet
    val selectedMyVerdict = uiState.selectedMyVerdict
    if (selectedMyVerdict != null) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = viewModel::onDismissBottomSheet,
        ) {
            VerdictPendingBottomSheetContent(
                modifier = Modifier,
                // TODO: 서버에서 jurorNickname 추가 후 변경
                jurorNickname = "익명 배심원",
                defendantNickname = uiState.userNickname,
                title = selectedMyVerdict.ledgerEntry.description,
                category = selectedMyVerdict.ledgerEntry.category,
                amount = selectedMyVerdict.ledgerEntry.amount,
                paymentMethod = selectedMyVerdict.ledgerEntry.paymentMethod,
                verdictType = selectedMyVerdict.verdictType,
            )
        }
    }

    // 판결 다이얼로그
    val selectedVerdictForJudgement = uiState.selectedJurorVerdictForJudgement
    if (selectedVerdictForJudgement != null) {
        JudgementDialog(
            onDismiss = viewModel::onJudgementDialogDismiss,
            onGuiltyClick = { viewModel.onSubmitJudgement(isGuilty = true) },
            onInnocentClick = { viewModel.onSubmitJudgement(isGuilty = false) }
        )
    }

    VerdictContent(
        selectedTabIndex = uiState.selectedTabIndex,
        myJudgementFilterIndex = uiState.judgements.filterIndex,
        myVerdictFilterIndex = uiState.verdicts.filterIndex,
        myJudgementCounts = uiState.judgements.counts,
        myVerdictCounts = uiState.verdicts.counts,
        jurorVerdictItems = uiState.judgements.items,
        myVerdictItems = uiState.verdicts.items,
        onJurorListClick = viewModel::navigateToJurorList,
        onTabSelected = viewModel::onTabSelected,
        onFilterSelected = viewModel::onFilterSelected,
        onJurorVerdictItemClick = viewModel::onJurorVerdictItemClick,
        onMyVerdictItemClick = viewModel::onMyVerdictItemClick,
    )
}

@Composable
private fun VerdictContent(
    selectedTabIndex: Int,
    myJudgementFilterIndex: Int,
    myVerdictFilterIndex: Int,
    myJudgementCounts: VerdictCounts,
    myVerdictCounts: VerdictCounts,
    jurorVerdictItems: List<JurorVerdictUiModel>,
    myVerdictItems: List<MyVerdictUiModel>,
    onJurorListClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFilterSelected: (Int) -> Unit,
    onJurorVerdictItemClick: (JurorVerdictUiModel) -> Unit,
    onMyVerdictItemClick: (MyVerdictUiModel) -> Unit,
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
                    myJudgementFilterIndex = myJudgementFilterIndex,
                    myVerdictFilterIndex = myVerdictFilterIndex,
                    myJudgementCounts = myJudgementCounts,
                    myVerdictCounts = myVerdictCounts,
                    onTabSelected = onTabSelected,
                    onFilterSelected = onFilterSelected,
                    modifier = Modifier.padding(horizontal = defaultPadding)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            if (selectedTabIndex == TabIndex.JUDGEMENTS) {
                if (jurorVerdictItems.isEmpty()) {
                    item { EmptyVerdictContent(selectedTabIndex = selectedTabIndex) }
                } else {
                    itemsIndexed(
                        items = jurorVerdictItems,
                        key = { _, item -> item.id }
                    ) { index, item ->
                        VerdictListItem(
                            amount = item.ledgerEntry.amount,
                            description = item.ledgerEntry.description,
                            categoryIconResId = item.ledgerEntry.category.iconResId,
                            paymentMethodIconResId = item.ledgerEntry.paymentMethod.iconResId,
                            verdictType = item.verdictType,
                            onItemClick = { onJurorVerdictItemClick(item) },
                            modifier = Modifier.padding(horizontal = defaultPadding)
                        )
                        if (index < jurorVerdictItems.lastIndex)
                            Spacer(modifier = Modifier.height(12.dp))
                        else
                            Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            } else {
                if (myVerdictItems.isEmpty()) {
                    item { EmptyVerdictContent(selectedTabIndex = selectedTabIndex) }
                } else {
                    itemsIndexed(
                        items = myVerdictItems,
                        key = { _, item -> item.id }
                    ) { index, item ->
                        VerdictListItem(
                            amount = item.ledgerEntry.amount,
                            description = item.ledgerEntry.description,
                            categoryIconResId = item.ledgerEntry.category.iconResId,
                            paymentMethodIconResId = item.ledgerEntry.paymentMethod.iconResId,
                            verdictType = item.verdictType,
                            onItemClick = { onMyVerdictItemClick(item) },
                            modifier = Modifier.padding(horizontal = defaultPadding)
                        )
                        if (index < myVerdictItems.lastIndex)
                            Spacer(modifier = Modifier.height(12.dp))
                        else
                            Spacer(modifier = Modifier.height(80.dp))
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
    val mockJudgementItems = listOf(
        JurorVerdictUiModel(
            id = 1,
            defendant = MateUiModel(201, "홍길동", level = 1, invitationCode = "AAAAAA"),
            ledgerEntry = mockLedgerEntry,
            verdictType = VerdictType.Pending,
        ),
        JurorVerdictUiModel(
            id = 2,
            defendant = MateUiModel(202, "김철수", level = 2, invitationCode = "BBBBBB"),
            ledgerEntry = mockLedgerEntry.copy(id = 102L, amount = 5000L, description = "커피 한잔"),
            verdictType = VerdictType.Guilty,
        ),
        JurorVerdictUiModel(
            id = 3,
            defendant = MateUiModel(203, "이영희", level = 3, invitationCode = "CCCCCC"),
            ledgerEntry = mockLedgerEntry.copy(id = 103L, amount = 25000L, description = "야식 치킨"),
            verdictType = VerdictType.NotGuilty,
        )
    )
    PickleTheme {
        VerdictContent(
            selectedTabIndex = 0,
            myJudgementFilterIndex = 0,
            myVerdictFilterIndex = 0,
            myJudgementCounts = VerdictCounts(10, 5, 5),
            myVerdictCounts = VerdictCounts(5, 3, 2),
            jurorVerdictItems = mockJudgementItems,
            myVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onJurorVerdictItemClick = {},
            onMyVerdictItemClick = {},
        )
    }
}

@Preview(name = "Empty Judgement")
@Composable
private fun VerdictContentEmptyJudgementPreview() {
    PickleTheme {
        VerdictContent(
            selectedTabIndex = 0,
            myJudgementFilterIndex = 0,
            myVerdictFilterIndex = 0,
            myJudgementCounts = VerdictCounts(0, 0, 0),
            myVerdictCounts = VerdictCounts(0, 0, 0),
            jurorVerdictItems = emptyList(),
            myVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onJurorVerdictItemClick = {},
            onMyVerdictItemClick = {},
        )
    }
}

@Preview(name = "Empty Verdict")
@Composable
private fun VerdictContentEmptyVerdictPreview() {
    PickleTheme {
        VerdictContent(
            selectedTabIndex = 1,
            myJudgementFilterIndex = 0,
            myVerdictFilterIndex = 0,
            myJudgementCounts = VerdictCounts(0, 0, 0),
            myVerdictCounts = VerdictCounts(0, 0, 0),
            jurorVerdictItems = emptyList(),
            myVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onJurorVerdictItemClick = {},
            onMyVerdictItemClick = {},
        )
    }
}
