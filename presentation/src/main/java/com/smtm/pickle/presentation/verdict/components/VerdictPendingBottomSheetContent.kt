package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun VerdictPendingBottomSheetContent(

    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "대기중인 심판",
            style = PickleTheme.typography.head3Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        ProfileSection()

        HorizontalDivider(thickness = 1.dp, color = PickleTheme.colors.gray100)

        ConsumptionHistoryItem()

        PleadingMessageSection("최후의 변론 내용입니다.최후의 변론 내용입니다.최후의 변론 내용입니다.최후의 변론 내용입니다.최후의 변론 내용입니다.최후의 변론 내용입니다.최후의 변론 내용입니다.")
    }
}

@Composable
private fun ProfileSection() {
    Row(
        modifier = Modifier.padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        BottomSheetProfile(name = "사용자 5자")
        Image(
            painter = painterResource(R.drawable.ic_verdict_arrow_right),
            contentDescription = null,
            modifier = Modifier.padding(13.dp)
        )
        BottomSheetProfile(name = "사용자 5자")
    }
}

@Composable
private fun BottomSheetProfile(modifier: Modifier = Modifier, name: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(Dimensions.profileSizeCircle)
                .clip(CircleShape)
                .background(PickleTheme.colors.gray100)
        ) {
            // TODO: 사용자 프로필 이미지
        }
        Text(
            text = name,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.gray800
        )
    }
}

@Composable
private fun ConsumptionHistoryItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .size(Dimensions.iconLarge)
                .clip(CircleShape)
                .background(PickleTheme.colors.gray100),
            contentAlignment = Alignment.Center
        ) {
            // TODO: 카테고리 아이콘
            Box(modifier = Modifier.size(Dimensions.iconMedium))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "소비 내역 15자",
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray700
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "-000,000,000,",
                    style = PickleTheme.typography.caption1Medium,
                    color = PickleTheme.colors.gray600
                )
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(PickleTheme.colors.gray200)
                )
                // TODO: 결제수단 아이콘
                Box(modifier = Modifier.size(Dimensions.iconSmall))
            }
        }
    }
}

@Composable
private fun PleadingMessageSection(pleadingMessage: String) {
    Card(
        modifier = Modifier.padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = PickleTheme.colors.background50
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_common_open_double_quotation),
                contentDescription = null,
            )
            Text(
                text = pleadingMessage,
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray700,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.ic_common_close_double_quotation),
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerdictPendingBottomSheetContentPreview() {
    PickleTheme {
        VerdictPendingBottomSheetContent()
    }
}
