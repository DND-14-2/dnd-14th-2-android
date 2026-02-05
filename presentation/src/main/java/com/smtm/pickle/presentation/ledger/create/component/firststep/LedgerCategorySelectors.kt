package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateHeaderText

@Composable
fun LedgerCategorySelectors(
    modifier: Modifier = Modifier,
    selectedLedgerType: LedgerTypeUiModel,
    selectedCategory: CategoryUiModel? = null,
    onCategoryClick: (CategoryUiModel) -> Unit
) {
    val categories = getCategories(selectedLedgerType)

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        LedgerCreateHeaderText(
            text = stringResource(R.string.ledger_create_category_header),
            highlightText = stringResource(R.string.ledger_create_category_header_highlight),
        )

        Spacer(modifier = Modifier.height(10.dp))

        CategoryGrid(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategoryClick = onCategoryClick,
        )
    }
}

@Composable
private fun CategoryGrid(
    modifier: Modifier = Modifier,
    categories: List<CategoryUiModel>,
    selectedCategory: CategoryUiModel? = null,
    onCategoryClick: (CategoryUiModel) -> Unit,
) {
    val rows = categories.chunked(3)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                rowItems.forEach { category ->
                    CategoryChip(
                        modifier = Modifier.weight(1f),
                        category = category,
                        isSelected = category == selectedCategory,
                        onClick = { onCategoryClick(category) }
                    )
                }

            }
        }
    }
}

@Composable
private fun CategoryChip(
    modifier: Modifier = Modifier,
    category: CategoryUiModel,
    isSelected: Boolean,
    onClick: (CategoryUiModel) -> Unit
) {
    val backgroundColor = if (isSelected) PickleTheme.colors.gray700 else PickleTheme.colors.gray50
    val textColor = if (isSelected) PickleTheme.colors.base0 else PickleTheme.colors.gray700
    val imageResId = category.iconResId

    Surface(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(Dimensions.radius),
        color = backgroundColor,
        onClick = { onClick(category) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(imageResId),
                contentDescription = "category_icon",
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(category.stringResId),
                style = PickleTheme.typography.body1Bold,
                color = textColor
            )
        }

    }
}

private fun getCategories(ledgerType: LedgerTypeUiModel): List<CategoryUiModel> = when (ledgerType) {
    LedgerTypeUiModel.Income -> {
        listOf(
            CategoryUiModel.Salary, CategoryUiModel.SideIncome, CategoryUiModel.Bonus,
            CategoryUiModel.Allowance, CategoryUiModel.PartTimeIncome, CategoryUiModel.FinancialIncome,
            CategoryUiModel.SplitBill, CategoryUiModel.Transfer, CategoryUiModel.Other,
        )
    }

    LedgerTypeUiModel.Expense -> {
        listOf(
            CategoryUiModel.Food, CategoryUiModel.Transport, CategoryUiModel.Housing,
            CategoryUiModel.Shopping, CategoryUiModel.HealthMedical, CategoryUiModel.EducationSelfDevelopment,
            CategoryUiModel.LeisureHobby, CategoryUiModel.SavingFinance, CategoryUiModel.Other,
        )
    }
}


@Preview(
    name = "LedgerCategorySelectorsPreview - Non Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCategorySelectorsNonSelectedPreview() {
    PickleTheme {
        LedgerCategorySelectors(
            selectedLedgerType = LedgerTypeUiModel.Expense,
            selectedCategory = null,
            onCategoryClick = {}
        )
    }
}

@Preview(
    name = "LedgerCategorySelectorsPreview - Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCategorySelectorsSelectedPreview() {
    PickleTheme {
        LedgerCategorySelectors(
            selectedLedgerType = LedgerTypeUiModel.Expense,
            selectedCategory = CategoryUiModel.Housing,
            onCategoryClick = {}
        )
    }
}
