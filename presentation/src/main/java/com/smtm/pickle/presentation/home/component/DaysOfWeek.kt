package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

private val DaysOfWeek = listOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY
)

@Composable
fun WeekDaysHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DaysOfWeek.forEach { dayOfWeek ->
            val textColor = when (dayOfWeek) {
                DayOfWeek.SUNDAY -> PickleTheme.colors.error50
                else -> PickleTheme.colors.gray700
            }

            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = PickleTheme.typography.caption1Medium,
                color = textColor
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun WeekDaysHeaderPreview() {
    MaterialTheme {
        WeekDaysHeader()
    }
}