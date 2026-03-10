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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleTitleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.PickleAppBarAction
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailDeleteDialog
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailReceipt

private val GradTop = Color(0xFFECFAF9)
private val GradBottom = Color(0xFFC4EEEB)

@Composable
fun LedgerDetailScreen(
    viewModel: LedgerDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToEdit: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is LedgerDetailEffect.NavigateBack -> {
                        onNavigateBack()
                    }

                    is LedgerDetailEffect.NavigateToEdit -> {
                        onNavigateToEdit()
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
        onNavigateBack = viewModel::navigateBack,
        onEditClick = viewModel::navigateToUpdate,
        onDeleteButtonClick = viewModel::showDeleteDialog,
    )
}

@Composable
private fun LedgerDetailContent(
    uiState: LedgerDetailUiState,
    onNavigateBack: () -> Unit,
    onEditClick: () -> Unit,
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
        PickleTitleAppBar(
            title = stringResource(R.string.ledger_detail_title),
            onBack = onNavigateBack,
            actions = listOf(
                PickleAppBarAction.Icon(
                    icon = R.drawable.ic_ledger_detail_edit_pen,
                    onClick = onEditClick,
                    contentDescription = stringResource(R.string.ledger_detail_action_edit),
                ),
                PickleAppBarAction.Icon(
                    icon = R.drawable.ic_ledger_detail_trashcan,
                    onClick = onDeleteButtonClick,
                    contentDescription = stringResource(R.string.ledger_detail_action_delete),
                )
            ),
            containerColor = PickleTheme.colors.transparent
        )

        Spacer(modifier = Modifier.height(60.dp))

        when (uiState) {
            LedgerDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }

            is LedgerDetailUiState.Success -> {
                LedgerDetailReceipt(
                    ledger = uiState.ledger
                )
            }

            LedgerDetailUiState.Error -> {
                // TODO Implement Error Content
            }
        }
    }
}
