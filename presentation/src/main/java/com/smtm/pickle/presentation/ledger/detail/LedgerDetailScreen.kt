package com.smtm.pickle.presentation.ledger.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailAppBar
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailReceipt

private val GradTop = Color(0xFFECFAF9)
private val GradBottom = Color(0xFFC4EEEB)

@Composable
fun LedgerDetailScreen(
    viewModel: LedgerDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LedgerDetailContent(
        uiState = uiState,
    )
}

@Composable
private fun LedgerDetailContent(
    uiState: LedgerDetailUiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradTop, GradBottom)
                )
            ),
    ) {
        LedgerDetailAppBar(
            onBackClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )

        Spacer(modifier = Modifier.height(60.dp))

        when (uiState) {
            LedgerDetailUiState.Loading -> {
                Box() {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is LedgerDetailUiState.Success -> {
                LedgerDetailReceipt(
                    ledger = uiState.ledger
                )
            }

            LedgerDetailUiState.Error -> {

            }
        }
    }
}
