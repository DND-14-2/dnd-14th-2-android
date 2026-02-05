package com.smtm.pickle.presentation.ledger.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.smtm.pickle.presentation.R
import java.time.LocalDate

@Composable
fun LedgerCreateScreen(
    date: LocalDate,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
) {

    LedgerCreateContent(
        date
    )
}

@Composable
private fun LedgerCreateContent(
    date: LocalDate,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Date: ${stringResource(R.string.common_mm_dd, date.monthValue, date.dayOfMonth)}")
        }
    }
}