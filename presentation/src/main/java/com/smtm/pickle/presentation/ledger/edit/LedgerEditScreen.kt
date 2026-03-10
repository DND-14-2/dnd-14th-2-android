package com.smtm.pickle.presentation.ledger.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.extension.clearFocusOnBackgroundTab
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleTitleAppBar
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.create.component.firststep.LedgerCreateFirstStepContent
import com.smtm.pickle.presentation.ledger.create.component.secondstep.LedgerCreateSecondContent

@Composable
fun LedgerEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: LedgerEditViewModel = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showExitDialog by viewModel.showExitDialog.collectAsStateWithLifecycle()

    val snackbarState = remember { SnackbarState() }

    BackHandler {
        viewModel.showExitDialog()
    }

    if (showExitDialog) {
        LedgerEditExitDialog(
            onContinueEdit = viewModel::dismissExitDialog,
            onConfirmExit = viewModel::confirmExit
        )
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is LedgerEditEffect.NavigateBack -> onNavigateBack()

                    is LedgerEditEffect.ShowSnackBar -> {
                        snackbarState.show(
                            PickleSnackbar.toastError(
                                message = effect.msg
                            )
                        )
                    }
                }
            }
        }
    }

    LedgerEditContent(
        uiState = uiState,
        setAmount = viewModel::setAmount,
        selectLedgerType = viewModel::selectLedgerType,
        selectCategory = viewModel::selectCategory,
        setDescription = viewModel::setDescription,
        setStep = viewModel::setStep,
        selectPaymentMethod = viewModel::selectPaymentMethod,
        setMemo = viewModel::setMemo,
        updateLedger = viewModel::editLedger,
        onNavigationClick = viewModel::showExitDialog,
    )

    SnackbarHost(snackbarState = snackbarState)
}

@Composable
private fun LedgerEditContent(
    uiState: LedgerUpdateUiState,
    setAmount: (String) -> Unit,
    selectLedgerType: (LedgerTypeUiModel) -> Unit,
    selectCategory: (CategoryUiModel?) -> Unit,
    setDescription: (String) -> Unit,
    setStep: (LedgerEditStep) -> Unit,
    selectPaymentMethod: (PaymentMethodUiModel) -> Unit,
    setMemo: (String) -> Unit,
    updateLedger: (String?) -> Unit,
    onNavigationClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val date = uiState.date

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PickleTheme.colors.base0)
            .systemBarsPadding()
            .clearFocusOnBackgroundTab(focusManager),
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            PickleTitleAppBar(
                title = stringResource(R.string.common_yyyy_mm_dd, date.year, date.monthValue, date.dayOfMonth),
                onBack = onNavigationClick,
            )

            when (uiState.step) {
                LedgerEditStep.First -> {
                    LedgerCreateFirstStepContent(
                        amount = uiState.firstStepState.amount,
                        selectedLedgerType = uiState.firstStepState.selectedLedgerType,
                        selectedCategory = uiState.firstStepState.selectedCategory,
                        description = uiState.firstStepState.description,
                        isNextEnabled = uiState.firstStepState.isNextEnabled,
                        onAmountChange = setAmount,
                        onLedgerTypeClick = selectLedgerType,
                        onCategoryClick = selectCategory,
                        onDescriptionChange = setDescription,
                        onNextClick = { setStep(LedgerEditStep.Second) },
                    )
                }

                LedgerEditStep.Second -> {
                    val defaultLedgerDescription = uiState.firstStepState.selectedCategory?.let {
                        stringResource(it.stringResId)
                    }
                    LedgerCreateSecondContent(
                        selectedPaymentMethod = uiState.secondStepState.selectedPaymentMethod,
                        memo = uiState.secondStepState.memo,
                        isSuccessEnabled = uiState.secondStepState.isSuccessEnabled,
                        onPaymentMethodClick = selectPaymentMethod,
                        onMemoChange = setMemo,
                        onPreviousClick = { setStep(LedgerEditStep.First) },
                        onSuccessClick = {
                            updateLedger(defaultLedgerDescription)
                        },
                    )
                }
            }
        }
    }
}