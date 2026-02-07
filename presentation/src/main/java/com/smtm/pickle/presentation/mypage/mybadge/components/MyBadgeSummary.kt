package com.smtm.pickle.presentation.mypage.mybadge.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.components.button.PickleBadge
import com.smtm.pickle.presentation.designsystem.components.profile.PickleProfile
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileSizeType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun MyBadgeSummary(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PickleProfile(
            iconRes = R.drawable.illust_profile_default,
            sizyType = ProfileSizeType.InSetting,
        )
        Spacer(modifier = Modifier.height(10.dp))

        PickleBadge(text = "배지명")
    }
}

// TODO: MVP 이후 활동 요약 카드
@Composable
private fun ActivitySummary(
    modifier: Modifier = Modifier,
    judgmentCount: Int = 0,
    juryCount: Int = 0,
) {
    PickleCard(
        color = PickleTheme.colors.gray50,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        ActivityInfoRow(
            title = stringResource(id = R.string.my_page_judge_activity),
            count = judgmentCount
        )
        Spacer(modifier = Modifier.height(12.dp))
        ActivityInfoRow(
            title = stringResource(id = R.string.my_page_jury_activity),
            count = juryCount
        )
    }
}

@Composable
private fun ActivityInfoRow(
    title: String,
    count: Int = 0,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.gray600
        )
        Text(
            text = "${count}회",
            style = PickleTheme.typography.body1Bold,
            color = PickleTheme.colors.gray700
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyBadgeSummaryPreview() {
    PickleTheme {
        MyBadgeSummary()
    }
}
