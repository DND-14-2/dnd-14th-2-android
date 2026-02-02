package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun LedgerCalendar(
    modifier: Modifier = Modifier,
    ledgerCalendarDays: Map<LocalDate, LedgerCalendarDay>,
    selectedYearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
) {
    val currentMonth = YearMonth.now()
    val startMonth = currentMonth.minusMonths(12)
    val endMonth = currentMonth.plusMonths(12)

    val monthlyCalendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = DayOfWeek.SATURDAY,
    )
    val visibleMonth = monthlyCalendarState.firstVisibleMonth.yearMonth

    LaunchedEffect(visibleMonth) {
        onMonthChanged(visibleMonth)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PickleTheme.colors.base0)
            .padding(16.dp)
    ) {
        MonthHeader(yearMonth = selectedYearMonth)

        Spacer(modifier = Modifier.height(24.dp))

        WeekDaysHeader()

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalCalendar(
            modifier = Modifier.fillMaxWidth(),
            state = monthlyCalendarState,
            contentHeightMode = ContentHeightMode.Wrap,
            dayContent = { day ->
                val ledgerCalendarDay = ledgerCalendarDays[day.date]
                MonthlyDayCell(
                    day = day,
                    isSelected = selectedDate == day.date,
                    totalExpense = ledgerCalendarDay?.totalExpense,
                    totalIncome = ledgerCalendarDay?.totalIncome,
                    onClick = { clickedDay ->
                        onDateClick(clickedDay.date)
                    }
                )
            }
        )
    }
}

@Preview(
    name = "LedgerCalendar - Monthly",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCalendarMonthlyPreview() {
    val currentDate = LocalDate.now()

    LedgerCalendar(
        ledgerCalendarDays = emptyMap(),
        selectedYearMonth = YearMonth.now(),
        selectedDate = currentDate,
        onDateClick = {},
        onMonthChanged = {},
    )
}