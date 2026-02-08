package com.smtm.pickle.presentation.ledger.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateAppBar
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateExitDialog
import com.smtm.pickle.presentation.ledger.create.component.firststep.LedgerCreateFirstStepContent
import com.smtm.pickle.presentation.ledger.create.component.secondstep.LedgerCreateSecondContent
import java.time.LocalDate

@Composable
fun LedgerCreateScreen(
    date: LocalDate,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: LedgerCreateViewModel = hiltViewModel(),
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarState() }

    BackHandler {
        viewModel.showExitDialog()
    }

    if (uiState.showExitDialog) {
        LedgerCreateExitDialog(
            onDismiss = viewModel::dismissExitDialog,
            onExitButtonClick = viewModel::confirmExit
        )
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    LedgerCreateEffect.NavigateToHome -> {
                        onNavigateToHome()
                    }

                    LedgerCreateEffect.NavigateBack -> {
                        onNavigateBack()
                    }

                    is LedgerCreateEffect.ShowSnackBar -> {
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

    SnackbarHost(snackbarState = snackbarState)

    LedgerCreateContent(
        date = date,
        uiState = uiState,
        setAmount = viewModel::setAmount,
        selectLedgerType = viewModel::selectLedgerType,
        selectCategory = viewModel::selectCategory,
        setDescription = viewModel::setDescription,
        setStep = viewModel::setStep,
        selectPaymentMethod = viewModel::selectPaymentMethod,
        setMemo = viewModel::setMemo,
        createLedger = viewModel::createLedger,
        onNavigationClick = viewModel::showExitDialog,
    )
}

@Composable
private fun LedgerCreateContent(
    date: LocalDate,
    uiState: LedgerCreateUiState,
    setAmount: (String) -> Unit,
    selectLedgerType: (LedgerTypeUiModel) -> Unit,
    selectCategory: (CategoryUiModel?) -> Unit,
    setDescription: (String) -> Unit,
    setStep: (LedgerCreateStep) -> Unit,
    selectPaymentMethod: (PaymentMethodUiModel) -> Unit,
    setMemo: (String) -> Unit,
    createLedger: (LocalDate) -> Unit,
    onNavigationClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PickleTheme.colors.base0)
            .systemBarsPadding()
            .clearFocusOnBackgroundTab(focusManager),
    ) {
        LedgerCreateAppBar(
            modifier = Modifier,
            title = stringResource(R.string.common_yyyy_mm_dd, date.year, date.monthValue, date.dayOfMonth),
            onNavigationClick = onNavigationClick,
        )

        when (uiState.step) {
            LedgerCreateStep.First -> {
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
                    onNextClick = { setStep(LedgerCreateStep.Second) },
                )
            }

            LedgerCreateStep.Second -> {
                LedgerCreateSecondContent(
                    selectedPaymentMethod = uiState.secondStepState.selectedPaymentMethod,
                    memo = uiState.secondStepState.memo,
                    isSuccessEnabled = uiState.secondStepState.isSuccessEnabled,
                    onPaymentMethodClick = selectPaymentMethod,
                    onMemoChange = setMemo,
                    onPreviousClick = { setStep(LedgerCreateStep.First) },
                    onSuccessClick = { createLedger(date) },
                )
            }
        }
    }
}
