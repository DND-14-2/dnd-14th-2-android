package com.smtm.pickle.presentation.verdict

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.domain.model.verdict.JurorInfo
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
import com.smtm.pickle.presentation.verdict.components.VerdictListItem
import com.smtm.pickle.presentation.verdict.components.VerdictNewInfoBanner
import com.smtm.pickle.presentation.verdict.components.VerdictPendingBottomSheetContent
import com.smtm.pickle.presentation.verdict.components.VerdictTabs
import com.smtm.pickle.presentation.verdict.model.VerdictCounts
import com.smtm.pickle.presentation.verdict.model.VerdictUiModel
import java.time.LocalDate
import java.time.LocalDateTime

private val defaultPadding: Dp = 16.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerdictScreen(
    viewModel: VerdictViewModel = hiltViewModel(),
    onNavigateVerdictCreate: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onNavigateVerdictRequest: () -> Unit,
    onNavigateVerdictResult: (Long) -> Unit,
    onNavigateJurorDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { true }
    )

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    VerdictEffect.NavigateToCreate -> onNavigateVerdictCreate()
                    VerdictEffect.NavigateToRequest -> onNavigateVerdictRequest()
                    is VerdictEffect.NavigateToResult -> onNavigateVerdictResult(effect.id)
                    is VerdictEffect.NavigateToJurorDetail -> onNavigateJurorDetail(effect.id)
                }
            }
        }
    }

    if (uiState.selectedVerdict != null) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = viewModel::onDismissBottomSheet,
        ) {
            // TODO: 사용자 정보 전달하기
            VerdictPendingBottomSheetContent()
        }
    }

    VerdictContent(
        selectedTabIndex = uiState.selectedTabIndex,
        myJudgementFilterIndex = uiState.judgements.filterIndex,
        myVerdictFilterIndex = uiState.verdicts.filterIndex,
        myJudgementCounts = uiState.judgements.counts,
        myVerdictCounts = uiState.verdicts.counts,
        myJudgementItems = uiState.judgements.items,
        myVerdictItems = uiState.verdicts.items,
        onNavigateVerdictCreate = onNavigateVerdictCreate,
        onNavigateJurorList = onNavigateJurorList,
        onTabSelected = viewModel::onTabSelected,
        onFilterSelected = viewModel::onFilterSelected,
        onVerdictItemClick = viewModel::onVerdictItemClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerdictContent(
    selectedTabIndex: Int,
    myJudgementFilterIndex: Int,
    myVerdictFilterIndex: Int,
    myJudgementCounts: VerdictCounts,
    myVerdictCounts: VerdictCounts,
    myJudgementItems: List<VerdictUiModel>,
    myVerdictItems: List<VerdictUiModel>,
    onNavigateVerdictCreate: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFilterSelected: (Int) -> Unit,
    onVerdictItemClick: (VerdictUiModel) -> Unit,
) {
    Scaffold(
        topBar = {
            PickleAppBar(title = "심판") {
                PickleIconButtonWithTouchCustom(
                    iconRes = R.drawable.ic_verdict_juror,
                    onClick = onNavigateJurorList
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateVerdictCreate,
                shape = CircleShape,
                modifier = Modifier.size(52.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_fab_add),
                    contentDescription = "Create Verdict"
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
        ) {
            // TODO: 새로운 소식 여부에 따라 보이도록 처리
            item("verdict_info") {
                VerdictNewInfoBanner()
            }

            // TODO: 새로운 소식 여부에 따라 보이도록 처리
            item("divider") {
                HorizontalDivider(
                    thickness = 8.dp,
                    color = PickleTheme.colors.background50
                )
            }

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

            val items = if (selectedTabIndex == 0) myJudgementItems else myVerdictItems
            if (items.isEmpty()) {
                item {
                    EmptyVerdictContent(selectedTabIndex = selectedTabIndex)
                }
            } else {
                items(
                    items = items,
                    key = { it.id }
                ) { item ->
                    VerdictListItem(
                        selectedTabIndex = selectedTabIndex,
                        jurorNickname = item.juror.nickname,
                        amount = item.ledger.amount,
                        description = item.ledger.description,
                        categoryIconResId = item.ledger.category.iconResId,
                        paymentMethodIconResId = item.ledger.paymentMethod.iconResId,
                        status = item.status,
                        onItemClick = { onVerdictItemClick(item) },
                        modifier = Modifier.padding(horizontal = defaultPadding)
                    )
                    if (item != items.last())
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
            juror = JurorInfo(201, "홍길동", "BADGE_1", "배지", "JUROR_CODE_1"),
            status = VerdictStatus.PENDING,
            createdAt = LocalDateTime.now().minusDays(1)
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
            juror = JurorInfo(202, "김철수", "BADGE_2", "배지", "JUROR_CODE_2"),
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
            juror = JurorInfo(203, "이영희", "BADGE_3", "배지", "JUROR_CODE_3"),
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
            onNavigateVerdictCreate = {},
            onNavigateJurorList = {},
            onTabSelected = {},
            onFilterSelected = {},
        ) {}
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
            onNavigateVerdictCreate = {},
            onNavigateJurorList = {},
            onTabSelected = {},
            onFilterSelected = {},
        ) {}
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
            onNavigateVerdictCreate = {},
            onNavigateJurorList = {},
            onTabSelected = {},
            onFilterSelected = {},
        ) {}
    }
}
