package com.smtm.pickle.presentation.verdict

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.button.PickleChip
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.verdict.components.MyVerdictRequestItem
import com.smtm.pickle.presentation.verdict.components.SentRequestTitle
import com.smtm.pickle.presentation.verdict.components.VerdictNewRequestCard

@Composable
fun VerdictScreen(
    viewModel: VerdictViewModel = hiltViewModel(),
    onNavigateVerdictCreate: () -> Unit,
    onNavigateVerdictRequest: () -> Unit,
    onNavigateVerdictResult: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onNavigateJurorDetail: () -> Unit,
) {

    VerdictContent(
        onNavigateVerdictCreate = onNavigateVerdictCreate,
        onNavigateVerdictRequest = onNavigateVerdictRequest,
        onNavigateVerdictResult = onNavigateVerdictResult,
        onNavigateJurorList = onNavigateJurorList,
        onNavigateJurorDetail = onNavigateJurorDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerdictContent(
    onNavigateVerdictCreate: () -> Unit,
    onNavigateVerdictRequest: () -> Unit,
    onNavigateVerdictResult: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onNavigateJurorDetail: () -> Unit,
) {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PickleTheme.colors.base0),
            ) {
                stickyHeader("top_bar") {
                    PickleAppBar(title = "심판", isInMainRoute = true) {
                        PickleIconButtonWithTouchCustom(
                            iconRes = R.drawable.ic_verdict_juror,
                            onClick = onNavigateJurorList
                        )
                    }
                }

                item("verdict_request") {
                    VerdictNewRequestCard(
                        onVerdictRequestClick = onNavigateVerdictResult
                    )
                }

                item("divider") {
                    HorizontalDivider(
                        thickness = 10.dp,
                        color = PickleTheme.colors.background50
                    )
                }

                item("space") {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item("sent_request_title") {
                    SentRequestTitle()
                }

                item("filter") {
                    val categories = listOf("전체", "대기중", "완료")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        categories.forEach { category ->
                            PickleChip(
                                text = category,
                                onClick = {},
                                selected = false,
                            )
                        }
                    }
                }


                item("filter_count") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = "전체",
                            style = PickleTheme.typography.body2Medium,
                            color = PickleTheme.colors.gray700,
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "0",
                            style = PickleTheme.typography.body2Medium,
                            color = PickleTheme.colors.gray600,
                        )
                    }
                }

                items(4) { index ->
                    val status = if (index < 2) "대기중" else "완료"
                    MyVerdictRequestItem(
                        nickname = "지인 닉네임",
                        badge = "배지명",
                        status = status,
                        judgements = 0,
                    )
                    if (index < 3) {
                        HorizontalDivider(
                            color = PickleTheme.colors.gray100,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }

            // FAB 버튼
            PickleIconButton(
                painter = painterResource(R.drawable.ic_fab_add),
                onClick = onNavigateVerdictCreate,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 10.dp)
                    .size(52.dp),
                iconSize = 52.dp
            )
        }
    }
}

@Preview
@Composable
private fun VerdictContentPreview() {
    PickleTheme {
        VerdictContent(
            onNavigateVerdictCreate = {},
            onNavigateVerdictRequest = {},
            onNavigateVerdictResult = {},
            onNavigateJurorList = {},
            onNavigateJurorDetail = {},
        )
    }
}
