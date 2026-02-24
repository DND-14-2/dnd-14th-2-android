package com.smtm.pickle.presentation.verdict.jurorlist.materequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.verdict.jurorlist.materequest.components.MateRequestItem
import com.smtm.pickle.presentation.verdict.model.MateUiModel

@Composable
fun MateRequestScreen(
    onNavigateBack: () -> Unit = {}
) {
    // 임시 더미 데이터 (뷰모델 연결 전)
    val dummyRequests = (1..2).map {
        MateUiModel(it.toLong(), "지인닉네임")
    }

    MateRequestContent(
        requests = dummyRequests,
        invitationCode = "CODE",
        onBackClick = onNavigateBack,
        onAcceptClick = {},
        onRejectClick = {},
    )
}

@Composable
private fun MateRequestContent(
    requests: List<MateUiModel>,
    invitationCode: String,
    onBackClick: () -> Unit,
    onAcceptClick: (Long) -> Unit,
    onRejectClick: (Long) -> Unit,
) {
    Scaffold(
        topBar = {
            PickleAppBar(
                title = stringResource(id = R.string.juror_list_mate_request_title),
                navigationItem = NavigationItem.Back(onBackClick)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.base0)
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    items = requests,
                    key = { it.id }
                ) { request ->
                    MateRequestItem(
                        nickname = request.nickname,
                        code = invitationCode,
                        onAcceptClick = { onAcceptClick(request.id) },
                        onRejectClick = { onRejectClick(request.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MateRequestContentPreview() {
    PickleTheme {
        MateRequestContent(
            requests = (1..2).map { MateUiModel(it.toLong(), "지인닉네임") },
            onBackClick = {},
            onAcceptClick = {},
            onRejectClick = {},
            invitationCode = "CODE23"
        )
    }
}
