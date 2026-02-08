package com.smtm.pickle.presentation.ledger.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LedgerDetailScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToUpdate: (Long) -> Unit = {},
) {
    LedgerDetailContent()
}

@Composable
private fun LedgerDetailContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Ledger Detail Screen")
        }
    }
}
