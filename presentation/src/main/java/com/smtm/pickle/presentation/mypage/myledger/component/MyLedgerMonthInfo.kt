package com.smtm.pickle.presentation.mypage.myledger.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions


@Composable
fun MyLedgerMonthInfo(
    modifier: Modifier = Modifier,
    month: Int,
    totalIncome: Long,
    totalExpense: Long,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PickleTheme.colors.background50)
            .padding(bottom = 10.dp)
            .background(PickleTheme.colors.base0)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.my_ledger_month, month),
            style = PickleTheme.typography.head2SemiBold,
            color = PickleTheme.colors.gray800
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(Dimensions.radiusSurface))
                .background(PickleTheme.colors.gray50)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.common_expense),
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.gray700
                )

                Text(
                    text = stringResource(R.string.common_minus_str_won, totalExpense.toMoneyFormat()),
                    style = PickleTheme.typography.body2Medium,
                    color = PickleTheme.colors.error50
                )
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.common_income),
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.gray700
                )

                Text(
                    text = stringResource(R.string.common_plus_str_won, totalIncome.toMoneyFormat()),
                    style = PickleTheme.typography.body2Medium,
                    color = PickleTheme.colors.primary500
                )
            }
        }
    }
}

@Preview(
    name = "MyLedgerMonthInfo",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MyLedgerMonthInfoPreview() {
    PickleTheme {
        MyLedgerMonthInfo(
            month = 2,
            totalIncome = 1000000,
            totalExpense = 500000,
        )
    }

}