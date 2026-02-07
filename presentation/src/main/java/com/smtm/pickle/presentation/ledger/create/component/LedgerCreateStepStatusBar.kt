package com.smtm.pickle.presentation.ledger.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.create.LedgerCreateStep

@Composable
fun LedgerCreateStepStatusBar(
    modifier: Modifier = Modifier,
    step: LedgerCreateStep,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        when (step) {
            LedgerCreateStep.First -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(PickleTheme.colors.primary400)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(PickleTheme.colors.gray100)
                )
            }

            LedgerCreateStep.Second -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(PickleTheme.colors.primary400)
                )
            }
        }
    }
}