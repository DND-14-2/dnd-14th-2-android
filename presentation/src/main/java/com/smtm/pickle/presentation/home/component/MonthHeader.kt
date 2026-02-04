package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.layout.Row
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
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import java.time.YearMonth

@Composable
fun MonthHeader(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 7.dp)
                .padding(start = 4.dp),
            text = stringResource(R.string.common_yyyy_mm, yearMonth.year, yearMonth.monthValue),
            style = PickleTheme.typography.body1Bold,
            color = PickleTheme.colors.gray700,
        )
    }
}

@Preview(
    name = "MonthHeader",
    showBackground = true,
    widthDp = 360
)
@Composable
fun MonthHeaderMonthlyPreview() {

    PickleTheme {
        MonthHeader(
            yearMonth = YearMonth.of(2026, 1),
        )
    }
}