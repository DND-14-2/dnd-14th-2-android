package com.smtm.pickle.presentation.mypage.tabs.statistics.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun StatsSummaryCard(
    isExpenditure: Boolean,
    cost: Long,
    onMyLedgerClick: () -> Unit = {},
    comparedValue: Long,
) {
    PickleCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = if (isExpenditure) "이번달 내 지출은?" else "이번달 내 수입은?",
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.gray600
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${String.format("%,d", cost)}원",
                    style = PickleTheme.typography.head2SemiBold,
                    color = PickleTheme.colors.gray700
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(
                    if (isExpenditure) R.drawable.illust_mypage_spending
                    else R.drawable.illust_mypage_income
                ),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        PickleButton(
            text = "내 장부 보기",
            onClick = onMyLedgerClick,
            color = PickleTheme.colors.primary50,
            textColor = PickleTheme.colors.primary500
        )
        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(thickness = 1.dp, color = PickleTheme.colors.gray100)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_mypage_coin),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = buildAnnotatedString {
                    append("지난달보다 ")

                    withStyle(style = SpanStyle(color = PickleTheme.colors.primary500)) {
                        append("${String.format("%,d", comparedValue)}원")
                    }

                    append(if (isExpenditure) " 절약했어요" else " 증가했어요") // TODO: 실제 증감 여부에 따른 로직 필요
                },
                style = PickleTheme.typography.body1Bold,
                color = PickleTheme.colors.gray600
            )
        }
    }
}

@Preview
@Composable
private fun StatsSummaryCardPreview() {
    PickleTheme {
        StatsSummaryCard(
            isExpenditure = true,
            cost = 30000,
            comparedValue = 999,
        )
    }
}
