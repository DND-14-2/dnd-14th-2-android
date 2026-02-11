package com.smtm.pickle.presentation.mypage.myledger.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.home.component.MonthHeader
import com.smtm.pickle.presentation.home.component.MonthlyDayCell
import com.smtm.pickle.presentation.home.component.WeekDaysHeader
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MyLedgerCalendar(
    modifier: Modifier = Modifier,
    ledgerCalendarDays: Map<LocalDate, LedgerCalendarDay>,
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
) {
    val calendarState = rememberCalendarState(
        startMonth = yearMonth,
        endMonth = yearMonth,
        firstVisibleMonth = yearMonth,
        firstDayOfWeek = DayOfWeek.SUNDAY,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PickleTheme.colors.base0)
            .padding(16.dp)
    ) {
        MonthHeader(yearMonth = yearMonth)

        Spacer(modifier = Modifier.height(24.dp))

        WeekDaysHeader()

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalCalendar(
            modifier = Modifier.fillMaxWidth(),
            state = calendarState,
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
    name = "MyLedgerCalendar",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MyLedgerCalendarPreview() {
    PickleTheme {
        MyLedgerCalendar(
            ledgerCalendarDays = emptyMap(),
            yearMonth = YearMonth.now(),
            selectedDate = LocalDate.now(),
            onDateClick = {},
        )
    }
}
