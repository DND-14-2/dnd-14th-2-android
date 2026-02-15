package com.smtm.pickle.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import java.time.LocalDate

fun LazyListScope.dailyLedgerInfoSection(
    modifier: Modifier = Modifier,
    date: LocalDate,
    ledgers: List<LedgerUiModel>,
    totalIncome: Long,
    totalExpense: Long,
    onLedgerClick: (LedgerId) -> Unit,
) {
    item("selected_date") {
        SelectedDate(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp, bottom = 6.dp),
            date = date
        )
    }
    if (ledgers.isEmpty()) {
        item("empty_notice") {
            EmptyNotice(modifier = modifier)
        }
    } else {
        item("selected_date_amount") {
            SelectedDateAmount(
                modifier = modifier
                    .padding(horizontal = 16.dp),
                totalIncome = totalIncome,
                totalExpense = totalExpense
            )
        }

        itemsIndexed(
            items = ledgers,
            key = { _, item -> item.id }
        ) { index, item ->
            val paddingTop = if (index == 0) 16.dp else 10.dp
            val paddingBottom = if (index == ledgers.size - 1) 90.dp else 0.dp

            LedgerCard(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = paddingTop, bottom = paddingBottom),
                title = stringResource(item.category.stringResId),
                description = item.description,
                amount = if (item.type == LedgerTypeUiModel.Income) {
                    stringResource(R.string.common_plus_str, item.amount.toMoneyFormat())
                } else {
                    stringResource(R.string.common_minus_str, item.amount.toMoneyFormat())
                },
                amountColor = if (item.type == LedgerTypeUiModel.Income) {
                    PickleTheme.colors.primary500
                } else {
                    PickleTheme.colors.error100
                },
                mainIconResId = item.category.iconResId,
                subIconResId = item.paymentMethod.iconResId,
                onClick = { onLedgerClick(LedgerId(item.id)) }
            )
        }
    }
}

@Composable
private fun SelectedDate(
    modifier: Modifier = Modifier,
    date: LocalDate
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.common_mm_dd, date.monthValue, date.dayOfMonth),
        textAlign = TextAlign.Start,
        style = PickleTheme.typography.body1Bold,
        color = PickleTheme.colors.gray700
    )
}

@Composable
fun EmptyNotice(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 74.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_ledger_empty),
            contentDescription = "empty_ledger",
            modifier = Modifier.size(120.dp),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.ledger_empty_notice),
            modifier = Modifier.padding(vertical = 10.dp),
            style = PickleTheme.typography.body3Regular,
            color = PickleTheme.colors.gray600,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SelectedDateAmount(
    modifier: Modifier = Modifier,
    totalIncome: Long,
    totalExpense: Long,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.common_income),
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.black,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            modifier = Modifier.width(110.dp),
            text = stringResource(R.string.common_plus_str, totalIncome.toMoneyFormat()),
            textAlign = TextAlign.Start,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.primary400
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = stringResource(R.string.common_expense),
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.black,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            modifier = Modifier.width(110.dp),
            text = stringResource(R.string.common_minus_str, totalExpense.toMoneyFormat()),
            textAlign = TextAlign.Start,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.error100
        )
    }
}

@Composable
fun LedgerCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    amount: String,
    amountColor: Color,
    @DrawableRes mainIconResId: Int?,
    @DrawableRes subIconResId: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = PickleTheme.colors.base0,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = PickleTheme.colors.gray200),
                color = PickleTheme.colors.gray50
            ) {
                mainIconResId?.let { resId ->
                    Icon(
                        painter = painterResource(resId),
                        contentDescription = "main_image",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(4.dp),
                        tint = Color.Unspecified,
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = PickleTheme.typography.body4Medium,
                        color = PickleTheme.colors.gray800
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_ellipse_dot),
                        contentDescription = "ellipse_dot",
                        modifier = Modifier.padding(horizontal = 2.dp),
                        tint = Color.Unspecified,
                    )
                    Icon(
                        painter = painterResource(subIconResId),
                        contentDescription = "sub_image",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified,
                    )
                }


                Text(
                    text = description,
                    style = PickleTheme.typography.caption2Regular,
                    color = PickleTheme.colors.gray500
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = amount,
                style = PickleTheme.typography.caption1Medium,
                color = amountColor
            )

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = "arrow_right",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview(
    name = "DailyLedgerInfoSection - Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun DailyLedgerInfoSectionEmptyPreview() {
    PickleTheme {
        LazyColumn {
            dailyLedgerInfoSection(
                date = LocalDate.now(),
                ledgers = emptyList(),
                totalIncome = 0L,
                totalExpense = 0L,
                onLedgerClick = {},
            )
        }
    }
}

@Preview(
    name = "DailyLedgerInfoSection - With Data",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun DailyLedgerInfoSectionWithDataPreview() {
    val sampleLedgers = listOf(
        LedgerUiModel(
            id = 1L,
            type = LedgerTypeUiModel.Income,
            amount = 2500000L,
            category = CategoryUiModel.SavingFinance,
            description = "월급",
            occurredOn = LocalDate.now(),
            paymentMethod = PaymentMethodUiModel.BankTransfer,
            memo = null
        ),
        LedgerUiModel(
            id = 2L,
            type = LedgerTypeUiModel.Expense,
            amount = 12000L,
            category = CategoryUiModel.Food,
            description = "점심",
            occurredOn = LocalDate.now(),
            paymentMethod = PaymentMethodUiModel.CreditCard,
            memo = null
        ),
        LedgerUiModel(
            id = 3L,
            type = LedgerTypeUiModel.Expense,
            amount = 4_500,
            category = CategoryUiModel.Food,
            description = "커피",
            occurredOn = LocalDate.now(),
            paymentMethod = PaymentMethodUiModel.CreditCard,
            memo = null
        )
    )

    PickleTheme {
        LazyColumn {
            dailyLedgerInfoSection(
                date = LocalDate.now(),
                ledgers = sampleLedgers,
                totalIncome = 2500000L,
                totalExpense = 16500L,
                onLedgerClick = {},
            )
        }
    }
}