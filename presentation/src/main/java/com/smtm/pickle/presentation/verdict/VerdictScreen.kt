package com.smtm.pickle.presentation.verdict

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.domain.model.verdict.VerdictResult
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
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
import com.smtm.pickle.presentation.verdict.model.JurorUiModel
import com.smtm.pickle.presentation.verdict.model.VerdictCounts
import com.smtm.pickle.presentation.verdict.model.VerdictUiModel
import java.time.LocalDate
import java.time.LocalDateTime

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

    val selectedVerdict = uiState.selectedVerdict
    if (selectedVerdict != null) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = viewModel::onDismissBottomSheet,
        ) {
            if (uiState.selectedTabIndex == TabIndex.JUDGEMENTS) {
                VerdictPendingBottomSheetContent(
                    modifier = Modifier,
                    jurorNickname = uiState.userNickname,
                    defendantNickname = selectedVerdict.defendant.nickname,
                    title = selectedVerdict.ledger.description,
                    category = selectedVerdict.ledger.category,
                    amount = selectedVerdict.ledger.amount,
                    paymentMethod = selectedVerdict.ledger.paymentMethod,
                    result = selectedVerdict.result
                )
            } else {
                VerdictPendingBottomSheetContent(
                    modifier = Modifier,
                    jurorNickname = selectedVerdict.defendant.nickname,
                    defendantNickname = uiState.userNickname,
                    title = selectedVerdict.ledger.description,
                    category = selectedVerdict.ledger.category,
                    amount = selectedVerdict.ledger.amount,
                    paymentMethod = selectedVerdict.ledger.paymentMethod,
                    result = selectedVerdict.result
                )
            }
        }
    }

    val selectedVerdictForJudgement = uiState.selectedVerdictForJudgement
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
        myJudgementItems = uiState.judgements.items,
        myVerdictItems = uiState.verdicts.items,
        onJurorListClick = viewModel::navigateToJurorList,
        onTabSelected = viewModel::onTabSelected,
        onFilterSelected = viewModel::onFilterSelected,
        onVerdictItemClick = viewModel::onVerdictItemClick,
    )
}

@Composable
private fun VerdictContent(
    selectedTabIndex: Int,
    myJudgementFilterIndex: Int,
    myVerdictFilterIndex: Int,
    myJudgementCounts: VerdictCounts,
    myVerdictCounts: VerdictCounts,
    myJudgementItems: List<VerdictUiModel>,
    myVerdictItems: List<VerdictUiModel>,
    onJurorListClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFilterSelected: (Int) -> Unit,
    onVerdictItemClick: (VerdictUiModel) -> Unit,
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

            val items = if (selectedTabIndex == TabIndex.JUDGEMENTS) myJudgementItems else myVerdictItems
            if (items.isEmpty()) {
                item {
                    EmptyVerdictContent(selectedTabIndex = selectedTabIndex)
                }
            } else {
                itemsIndexed(
                    items = items,
                    key = { _, item -> item.id }
                ) { index, item ->
                    Box {
                        if (item.isNew) {
                            Image(
                                painter = painterResource(R.drawable.ic_common_new),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = defaultPadding)
                                    .zIndex(1f)
                                    .offset(x = 14.dp, y = (-10).dp)
                            )
                        }
                        VerdictListItem(
                            amount = item.ledger.amount,
                            description = item.ledger.description,
                            categoryIconResId = item.ledger.category.iconResId,
                            paymentMethodIconResId = item.ledger.paymentMethod.iconResId,
                            status = item.status,
                            onItemClick = { onVerdictItemClick(item) },
                            modifier = Modifier.padding(horizontal = defaultPadding)
                        )
                    }
                    if (index < items.lastIndex)
                        Spacer(modifier = Modifier.height(12.dp))
                    else
                        Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun VerdictContentPreview() {
    val mockJudgementItems = listOf(
        VerdictUiModel(
            id = 1,
            ledger = LedgerUiModel(
                id = 101L,
                type = LedgerTypeUiModel.Expense,
                amount = 15000L,
                category = CategoryUiModel.Food,
                description = "가계부 15자 입력",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethodUiModel.Cash,
                memo = null
            ),
            defendant = JurorUiModel(201, "홍길동"),
            status = VerdictStatus.PENDING,
            createdAt = LocalDateTime.now().minusDays(1),
            isNew = true
        ),
        VerdictUiModel(
            id = 2,
            ledger = LedgerUiModel(
                id = 102L,
                type = LedgerTypeUiModel.Expense,
                amount = 5000L,
                category = CategoryUiModel.Food,
                description = "커피 한잔",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethodUiModel.CreditCard,
                memo = null
            ),
            defendant = JurorUiModel(202, "김철수"),
            status = VerdictStatus.COMPLETED,
            result = VerdictResult.GUILTY,
            createdAt = LocalDateTime.now().minusDays(2)
        ),
        VerdictUiModel(
            id = 3,
            ledger = LedgerUiModel(
                id = 103L,
                type = LedgerTypeUiModel.Expense,
                amount = 25000L,
                category = CategoryUiModel.Food,
                description = "야식 치킨",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethodUiModel.CreditCard,
                memo = null
            ),
            defendant = JurorUiModel(203, "이영희"),
            status = VerdictStatus.COMPLETED,
            result = VerdictResult.INNOCENT,
            createdAt = LocalDateTime.now().minusDays(3)
        )
    )
    PickleTheme {
        VerdictContent(
            selectedTabIndex = 0,
            myJudgementFilterIndex = 0,
            myVerdictFilterIndex = 0,
            myJudgementCounts = VerdictCounts(10, 5, 5),
            myVerdictCounts = VerdictCounts(5, 3, 2),
            myJudgementItems = mockJudgementItems,
            myVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onVerdictItemClick = {},
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
            myJudgementItems = emptyList(),
            myVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onVerdictItemClick = {},
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
            myJudgementItems = emptyList(),
            myVerdictItems = emptyList(),
            onJurorListClick = {},
            onTabSelected = {},
            onFilterSelected = {},
            onVerdictItemClick = {},
        )
    }
}
