package com.smtm.pickle.presentation.mypage.tabs.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.MyPageUiState
import com.smtm.pickle.presentation.mypage.tabs.activity.components.ActivityActionCardRow
import com.smtm.pickle.presentation.mypage.tabs.activity.components.ActivityPendingJudgment

@Composable
fun ActivityTab(
    modifier: Modifier = Modifier,
    activityState: MyPageUiState.ActivityState,
) {
    Column(
        modifier = modifier
            .heightIn(min = 500.dp)
            .background(PickleTheme.colors.background50)
            .padding(16.dp)
            .padding(top = 4.dp)
    ) {
        ActivityActionCardRow(
            onJudgmentClick = {},
            onJuryClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ActivityPendingJudgment(
            pendingJudgments = activityState.pendingJudgments
        )
    }
}


@Preview
@Composable
private fun ActivityTabPreview() {
    PickleTheme {
        ActivityTab(
            activityState = MyPageUiState.ActivityState()
        )
    }
}
