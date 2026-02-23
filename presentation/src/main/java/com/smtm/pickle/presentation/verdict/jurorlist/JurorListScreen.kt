package com.smtm.pickle.presentation.verdict.jurorlist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.domain.model.verdict.Juror
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleBottomSheet
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.verdict.jurorlist.components.EmptyJurorContent
import com.smtm.pickle.presentation.verdict.jurorlist.components.InviteBottomSheetContent
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListItem
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListMateRequestBanner
import com.smtm.pickle.presentation.verdict.jurorlist.components.VerdictSearchAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JurorListScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToMateRequest: () -> Unit = {},
    onNavigateToJurorDetail: (Long) -> Unit = {},
) {
    var isExpendedSearchBar by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val dummyJurors = (1..5).map {
        Juror(it.toLong(), "지인닉네임$it")
    }

    BackHandler(isExpendedSearchBar) {
        isExpendedSearchBar = false
    }

    if (showBottomSheet) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = { showBottomSheet = false },
        ) {
            InviteBottomSheetContent(
                invitationCode = "A123456"
            )
        }
    }

    JurorListContent(
        jurors = dummyJurors,
        isExpendedSearchBar = isExpendedSearchBar,
        searchQuery = "",
        onBackClick = onNavigateBack,
        onSearchToggle = { isExpendedSearchBar = !isExpendedSearchBar },
        onInviteClick = { showBottomSheet = true },
        onMateRequestClick = onNavigateToMateRequest,
        onJurorClick = { id -> onNavigateToJurorDetail(id) },
        onSearchQueryChanged = {},
    )
}

@Composable
private fun JurorListContent(
    jurors: List<Juror>,
    isExpendedSearchBar: Boolean,
    searchQuery: String,
    onBackClick: () -> Unit,
    onSearchToggle: () -> Unit,
    onInviteClick: () -> Unit,
    onMateRequestClick: () -> Unit,
    onJurorClick: (Long) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            if (isExpendedSearchBar) {
                VerdictSearchAppBar(
                    searchValue = searchQuery,
                    onSearchValueChange = onSearchQueryChanged,
                    hint = stringResource(id = R.string.juror_list_search_hint),
                    onBackClick = onSearchToggle,
                )
            } else {
                PickleAppBar(
                    title = stringResource(id = R.string.juror_list_title),
                    navigationItem = NavigationItem.Back(onBackClick),
                ) {
                    PickleIconButtonWithTouchCustom(
                        iconRes = R.drawable.ic_search_magnifier,
                        onClick = onSearchToggle,
                        tint = PickleTheme.colors.gray700,
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.base0)
                .padding(paddingValues),
        ) {
            if (jurors.isEmpty()) {
                EmptyJurorContent(onInviteClick = onInviteClick)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // TODO: 메이트 요청 여부에 따라 보이도록 조절
                    item {
                        JurorListMateRequestBanner(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 10.dp),
                            onClick = onMateRequestClick
                        )
                    }

                    items(
                        items = jurors,
                        key = { it.id }
                    ) { juror ->
                        JurorListItem(
                            nickname = juror.nickname,
                            onClick = { onJurorClick(juror.id) },
                            togetherVerdictCount = 0,
                            code = "CODE23",
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JurorListContentPreview() {
    PickleTheme {
        JurorListContent(
            jurors = (1..5).map {
                Juror(it.toLong(), "지인닉네임")
            },
            isExpendedSearchBar = false,
            onSearchToggle = {},
            searchQuery = "",
            onBackClick = {},
            onSearchQueryChanged = {},
            onJurorClick = {},
            onInviteClick = {},
            onMateRequestClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JurorListEmptyPreview() {
    PickleTheme {
        JurorListContent(
            jurors = emptyList(),
            isExpendedSearchBar = true,
            onSearchToggle = {},
            searchQuery = "",
            onBackClick = {},
            onSearchQueryChanged = {},
            onJurorClick = {},
            onInviteClick = {},
            onMateRequestClick = {},
        )
    }
}
