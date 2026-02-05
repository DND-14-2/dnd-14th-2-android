package com.smtm.pickle.presentation.ledger.create.component.firststep

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
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.create.LedgerCreateStep
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateStepStatusBar

@Composable
fun LedgerCreateFirstStepContent(
    modifier: Modifier = Modifier,
    amount: String,
    selectedLedgerType: LedgerTypeUiModel,
    selectedCategory: CategoryUiModel?,
    description: String,
    isNextEnabled: Boolean,
    onAmountChange: (String) -> Unit,
    onLedgerTypeClick: (LedgerTypeUiModel) -> Unit,
    onCategoryClick: (CategoryUiModel?) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
    ) {
        LedgerCreateStepStatusBar(step = LedgerCreateStep.First)

        LedgerTypeSelectors(
            selectedType = selectedLedgerType,
            onLedgerTypeClick = { selectedType ->
                if (selectedType == selectedLedgerType) return@LedgerTypeSelectors
                onLedgerTypeClick(selectedType)
                onCategoryClick(null)
            },
        )

        LedgerAmountInputField(
            value = amount,
            onValueChange = onAmountChange,
        )

        LedgerCategorySelectors(
            selectedLedgerType = selectedLedgerType,
            selectedCategory = selectedCategory,
            onCategoryClick = onCategoryClick,
        )

        LedgerDescriptionInputField(
            value = description,
            onValueChange = onDescriptionChange,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Spacer(modifier = Modifier.weight(1f))

        LedgerCreateFirstBottomButton(
            enableNext = isNextEnabled,
            onNextClick = onNextClick,
        )
    }
}

@Preview(
    name = "LedgerCreateFirstStepContentPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateFirstStepContentPreview() {
    PickleTheme {
        LedgerCreateFirstStepContent(
            amount = "0",
            selectedLedgerType = LedgerTypeUiModel.Expense,
            description = "",
            isNextEnabled = false,
            onAmountChange = {},
            onLedgerTypeClick = {},
            selectedCategory = null,
            onCategoryClick = {},
            onDescriptionChange = {},
            onNextClick = {}
        )
    }
}
