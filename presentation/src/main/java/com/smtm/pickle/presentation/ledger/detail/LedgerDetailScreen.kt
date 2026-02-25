package com.smtm.pickle.presentation.ledger.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import com.smtm.pickle.presentation.designsystem.components.PickleBottomSheet
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailAppBar
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailDeleteDialog
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailJudgmentRequestDialog
import com.smtm.pickle.presentation.ledger.detail.component.LedgerDetailReceipt
import java.time.LocalDate

private val GradTop = Color(0xFFECFAF9)
private val GradBottom = Color(0xFFC4EEEB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerDetailScreen(
    viewModel: LedgerDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToEdit: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
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

    when (dialogState) {
        LedgerDetailDialogState.DeleteConfirm -> {
            LedgerDetailDeleteDialog(
                onDismiss = viewModel::dismissDialog,
                onDeleteButtonClick = viewModel::deleteLedger,
            )
        }

        LedgerDetailDialogState.JudgmentRequest -> {
            LedgerDetailJudgmentRequestDialog(
                onConfirmClick = viewModel::confirmJudgmentRequest,
                onCancelClick = viewModel::dismissDialog,
                onDismiss = viewModel::dismissDialog,
            )
        }

        LedgerDetailDialogState.None -> Unit
    }

    LedgerDetailContent(
        uiState = uiState,
        onNavigateBack = viewModel::navigateBack,
        onEditClick = viewModel::navigateToUpdate,
        onDeleteButtonClick = viewModel::onDeleteClick,
        onJudgmentRequestClick = viewModel::onJudgmentRequestClick,
    )

    SnackbarHost(snackbarState = snackbarState)
}

@Composable
private fun LedgerDetailContent(
    uiState: LedgerDetailUiState,
    onNavigateBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onJudgmentRequestClick: () -> Unit,
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
            onBackClick = onNavigateBack,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteButtonClick
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

                Spacer(modifier = Modifier.weight(1f))

                if (uiState.ledger.type == LedgerTypeUiModel.Expense) {
                    PickleButton(
                        text = stringResource(R.string.ledger_detail_judgement_request),
                        onClick = onJudgmentRequestClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 14.dp, top = 6.dp)
                    )
                }
            }

            LedgerDetailUiState.Error -> {
                // TODO Implement Error Content
            }
        }
    }
}

@Preview
@Composable
private fun LedgerDetailContentPreview() {
    PickleTheme {
        LedgerDetailContent(
            uiState = LedgerDetailUiState.Success(
                ledger = LedgerUiModel(
                    id = 1L,
                    type = LedgerTypeUiModel.Expense,
                    amount = 10000,
                    category = CategoryUiModel.Food,
                    description = "점심 식사",
                    occurredOn = LocalDate.now(),
                    paymentMethod = PaymentMethodUiModel.CreditCard,
                    memo = "맛있는 점심"
                )
            ),
            onNavigateBack = {},
            onEditClick = {},
            onDeleteButtonClick = {},
            onJudgmentRequestClick = {},
        )
    }
}
