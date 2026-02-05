package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun LedgerTypeSelectors(
    modifier: Modifier = Modifier,
    onLedgerTypeClick: (LedgerTypeUiModel) -> Unit,
    selectedType: LedgerTypeUiModel,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 15.dp, bottom = 16.dp),
        color = PickleTheme.colors.background50,
        shape = RoundedCornerShape(Dimensions.radiusSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        ) {
            LedgerTypeChip(
                modifier = Modifier.weight(1f),
                isSelected = LedgerTypeUiModel.Expense == selectedType,
                type = LedgerTypeUiModel.Expense,
                onClick = {
                    onLedgerTypeClick(LedgerTypeUiModel.Expense)
                }
            )

            LedgerTypeChip(
                modifier = Modifier.weight(1f),
                isSelected = LedgerTypeUiModel.Income == selectedType,
                type = LedgerTypeUiModel.Income,
                onClick = {
                    onLedgerTypeClick(LedgerTypeUiModel.Income)
                }
            )
        }
    }
}

@Composable
private fun LedgerTypeChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    type: LedgerTypeUiModel,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        when (type) {
            LedgerTypeUiModel.Income -> PickleTheme.colors.primary400
            LedgerTypeUiModel.Expense -> PickleTheme.colors.error100
        }
    } else {
        PickleTheme.colors.transparent
    }
    val textColor = if (isSelected) PickleTheme.colors.base0 else PickleTheme.colors.gray600

    Surface(
        modifier = modifier.height(48.dp),
        onClick = onClick,
        color = backgroundColor,
        shape = RoundedCornerShape(Dimensions.radiusSurface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(type.stringResId),
                color = textColor,
                style = PickleTheme.typography.body2Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    name = "LedgerTypeSelectors - Income Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerTypeSelectorsIncomeSelectedPreview() {
    PickleTheme {
        LedgerTypeSelectors(
            onLedgerTypeClick = {},
            selectedType = LedgerTypeUiModel.Income
        )
    }
}

@Preview(
    name = "LedgerTypeSelectors - Expense Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerTypeSelectorsExpenseSelectedPreview() {
    PickleTheme {
        LedgerTypeSelectors(
            onLedgerTypeClick = {},
            selectedType = LedgerTypeUiModel.Expense
        )
    }
}

@Preview(
    name = "LedgerTypeChip - Selected",
    showBackground = true
)
@Composable
private fun LedgerTypeChipSelectedPreview() {
    PickleTheme {
        LedgerTypeChip(
            modifier = Modifier.width(160.dp),
            isSelected = true,
            type = LedgerTypeUiModel.Income,
            onClick = {}
        )
    }
}

@Preview(
    name = "LedgerTypeChip - Unselected",
    showBackground = true
)
@Composable
private fun LedgerTypeChipUnselectedPreview() {
    PickleTheme {
        LedgerTypeChip(
            modifier = Modifier.width(160.dp),
            isSelected = false,
            type = LedgerTypeUiModel.Expense,
            onClick = {}
        )
    }
}
