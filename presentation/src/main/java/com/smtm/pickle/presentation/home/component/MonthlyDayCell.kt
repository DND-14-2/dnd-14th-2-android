package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import java.time.LocalDate

@Composable
fun MonthlyDayCell(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    isSelected: Boolean,
    totalExpense: Long? = null,
    totalIncome: Long? = null,
    onClick: (CalendarDay) -> Unit,
) {
    val isActiveDay = day.position == DayPosition.MonthDate
    DayCell(
        modifier = modifier,
        date = day.date,
        isActiveDay = isActiveDay,
        isSelected = isSelected,
        totalExpense = totalExpense,
        totalIncome = totalIncome,
        onClick = { onClick(day) }
    )
}

@Composable
fun WeeklyDayCell(
    modifier: Modifier = Modifier,
    day: WeekDay,
    isSelected: Boolean,
    totalExpense: Long? = null,
    totalIncome: Long? = null,
    onClick: (WeekDay) -> Unit,
) {
    val isActiveDay = day.position == WeekDayPosition.RangeDate
    DayCell(
        modifier = modifier,
        date = day.date,
        isActiveDay = isActiveDay,
        isSelected = isSelected,
        totalExpense = totalExpense,
        totalIncome = totalIncome,
        onClick = { onClick(day) }
    )
}


@Composable
private fun DayCell(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isActiveDay: Boolean,
    isSelected: Boolean,
    totalExpense: Long? = null,
    totalIncome: Long? = null,
    onClick: () -> Unit,
) {
    val dateTextColor = when {
        !isActiveDay -> PickleTheme.colors.gray500
        isSelected -> PickleTheme.colors.primary500
        else -> PickleTheme.colors.gray800
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clickable(enabled = isActiveDay, onClick = onClick),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(vertical = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = dateTextColor,
                style = PickleTheme.typography.body2Medium,
            )
            if (isActiveDay) {
                Spacer(modifier = Modifier.height(6.dp))

                totalExpense?.let { totalExpense ->
                    AmountItem(
                        amount = totalExpense,
                        color = PickleTheme.colors.error50
                    )
                }
                totalIncome?.let { totalIncome ->
                    AmountItem(
                        amount = totalIncome,
                        color = PickleTheme.colors.primary500
                    )
                }
            }
        }
    }
}

@Composable
private fun AmountItem(
    modifier: Modifier = Modifier,
    amount: Long,
    color: Color,
) {
    Text(
        modifier = modifier,
        text = amount.toMoneyFormat(),
        color = color,
        fontSize = 8.sp,
        maxLines = 1,
        lineHeight = 11.sp,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true, name = "Default")
@Composable
private fun DayCellPreview() {
    PickleTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = true,
            isSelected = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Selected")
@Composable
private fun DayCellSelectedPreview() {
    PickleTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = true,
            isSelected = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With Finance")
@Composable
private fun DayCellWithFinancePreview() {
    PickleTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = true,
            isSelected = false,
            totalIncome = 3_000_000L,
            totalExpense = 12_000L,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Inactive")
@Composable
private fun DayCellInactivePreview() {
    PickleTheme {
        DayCell(
            date = LocalDate.of(2024, 1, 15),
            isActiveDay = false,
            isSelected = false,
            onClick = {}
        )
    }
}