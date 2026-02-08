package com.smtm.pickle.presentation.ledger.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailAppBar
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailDeleteDialog
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailReceipt

private val GradTop = Color(0xFFECFAF9)
private val GradBottom = Color(0xFFC4EEEB)

@Composable
fun LedgerDetailScreen(
    viewModel: LedgerDetailViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is LedgerDetailEffect.NavigateToHome -> {
                        onNavigateToHome()
                    }

                    is LedgerDetailEffect.ShowSnackBar -> {
                        snackbarState.show(
                            PickleSnackbar.toastError(
                                message = effect.msg,
                            )
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        LedgerDetailDeleteDialog(
            onDismiss = viewModel::dismissDeleteDialog,
            onDeleteButtonClick = viewModel::deleteLedger,
        )
    }

    SnackbarHost(snackbarState = snackbarState)

    LedgerDetailContent(
        uiState = uiState,
        onNavigateToHome = viewModel::navigateToHome,
        onDeleteButtonClick = viewModel::showDeleteDialog,
    )
}

@Composable
private fun LedgerDetailContent(
    uiState: LedgerDetailUiState,
    onNavigateToHome: () -> Unit,
    onDeleteButtonClick: () -> Unit,
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
            onBackClick = onNavigateToHome,
            onEditClick = {},
            onDeleteClick = onDeleteButtonClick
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
