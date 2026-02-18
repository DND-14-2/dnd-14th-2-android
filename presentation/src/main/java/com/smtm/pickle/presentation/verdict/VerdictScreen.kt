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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.verdict.components.EmptyVerdictContent
import com.smtm.pickle.presentation.verdict.components.VerdictListItem
import com.smtm.pickle.presentation.verdict.components.VerdictNewInfoBanner
import com.smtm.pickle.presentation.verdict.components.VerdictTabs

private val defaultPadding: Dp = 16.dp

@Composable
fun VerdictScreen(
    viewModel: VerdictViewModel = hiltViewModel(),
    onNavigateVerdictCreate: () -> Unit,
    onNavigateVerdictRequest: () -> Unit,
    onNavigateVerdictResult: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onNavigateJurorDetail: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    VerdictContent(
        uiState = uiState,
        onNavigateVerdictCreate = onNavigateVerdictCreate,
        onNavigateJurorList = onNavigateJurorList,
        onTabSelected = viewModel::onTabSelected,
        onFilterSelected = viewModel::onFilterSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerdictContent(
    uiState: VerdictUiState,
    onNavigateVerdictCreate: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onFilterSelected: (Int) -> Unit,
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
            item("verdict_info") {
                VerdictNewInfoBanner()
            }

            item("divider") {
                HorizontalDivider(
                    thickness = 8.dp,
                    color = PickleTheme.colors.background50
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item("tabs") {
                VerdictTabs(
                    uiState = uiState,
                    onTabSelected = onTabSelected,
                    onFilterSelected = onFilterSelected,
                    modifier = Modifier.padding(horizontal = defaultPadding)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            val items = if (uiState.selectedTabIndex == 0) uiState.myJudgementItems else uiState.myVerdictItems
            if (items.isEmpty()) {
                item {
                    EmptyVerdictContent(selectedTabIndex = uiState.selectedTabIndex)
                }
            } else {
                items(items) { item ->
                    VerdictListItem(
                        item = item,
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
    PickleTheme {
        VerdictContent(
            uiState = VerdictUiState(
                myJudgementItems = emptyList()
            ),
            onNavigateVerdictCreate = {},
            onNavigateJurorList = {},
            onTabSelected = {},
            onFilterSelected = {}
        )
    }
}

@Preview(name = "Empty Judgement")
@Composable
private fun VerdictContentEmptyJudgementPreview() {
    PickleTheme {
        VerdictContent(
            uiState = VerdictUiState(
                selectedTabIndex = 0,
                myJudgementItems = emptyList()
            ),
            onNavigateVerdictCreate = {},
            onNavigateJurorList = {},
            onTabSelected = {},
            onFilterSelected = {}
        )
    }
}

@Preview(name = "Empty Verdict")
@Composable
private fun VerdictContentEmptyVerdictPreview() {
    PickleTheme {
        VerdictContent(
            uiState = VerdictUiState(
                selectedTabIndex = 1,
                myVerdictItems = emptyList()
            ),
            onNavigateVerdictCreate = {},
            onNavigateJurorList = {},
            onTabSelected = {},
            onFilterSelected = {}
        )
    }
}
