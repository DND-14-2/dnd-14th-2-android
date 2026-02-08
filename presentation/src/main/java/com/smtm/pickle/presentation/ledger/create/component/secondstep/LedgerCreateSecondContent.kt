package com.smtm.pickle.presentation.ledger.create.component.secondstep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.create.LedgerCreateStep
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateStepStatusBar

@Composable
fun LedgerCreateSecondContent(
    modifier: Modifier = Modifier,
    selectedPaymentMethod: PaymentMethodUiModel?,
    memo: String,
    isSuccessEnabled: Boolean,
    onPaymentMethodClick: (PaymentMethodUiModel) -> Unit,
    onMemoChange: (String) -> Unit,
    onPreviousClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
    ) {
        LedgerCreateStepStatusBar(step = LedgerCreateStep.Second)

        LedgerPaymentMethodSelectors(
            selectedPaymentMethod = selectedPaymentMethod,
            onPaymentMethodClick = onPaymentMethodClick
        )

        LedgerCreateMemo(
            memo = memo,
            onMemoChange = onMemoChange
        )

        Spacer(modifier = Modifier.height(40.dp))

        Spacer(modifier = Modifier.weight(1f))

        LedgerCreateSecondBottomButtons(
            enabledSuccess = isSuccessEnabled,
            onPreviousClick = onPreviousClick,
            onSuccessClick = onSuccessClick,
        )
    }
}

@Preview(
    name = "LedgerCreateSecondContentPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondContentPreview() {
    PickleTheme {
        LedgerCreateSecondContent(
            selectedPaymentMethod = null,
            memo = "",
            isSuccessEnabled = false,
            onPaymentMethodClick = {},
            onMemoChange = {},
            onPreviousClick = {},
            onSuccessClick = {},
        )
    }
}