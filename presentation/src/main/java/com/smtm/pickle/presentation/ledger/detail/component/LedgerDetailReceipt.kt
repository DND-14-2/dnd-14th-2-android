package com.smtm.pickle.presentation.ledger.detail.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import java.time.LocalDate

private val shadowElevationValue = 4.dp

@Composable
fun LedgerDetailReceipt(
    modifier: Modifier = Modifier,
    ledger: LedgerUiModel,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = LedgerDetailReceiptShape(),
        color = PickleTheme.colors.base0,
        shadowElevation = shadowElevationValue
    ) {
        Column(

        ) {
            // 헤더
            ReceiptHeader(
                categoryIconResId = ledger.category.iconResId,
                title = ledger.description
            )

            // 바디
            ReceiptBody(
                date = ledger.occurredOn,
                amount = ledger.amount,
                type = ledger.type,
                paymentMethod = ledger.paymentMethod,
                memo = ledger.memo
            )
            //
        }
    }

}

@Composable
private fun ReceiptHeader(
    modifier: Modifier = Modifier,
    categoryIconResId: Int,
    title: String,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = PickleTheme.colors.gray200),
                color = PickleTheme.colors.gray50
            ) {
                Image(
                    painter = painterResource(categoryIconResId),
                    contentDescription = "category",
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                style = PickleTheme.typography.head4SemiBold,
                color = PickleTheme.colors.gray700,
            )
        }
    }
}

@Composable
private fun ReceiptBody(
    modifier: Modifier = Modifier,
    date: LocalDate,
    amount: Long,
    type: LedgerTypeUiModel,
    paymentMethod: PaymentMethodUiModel,
    memo: String?,
) {
    Column(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.common_yyyy_mm_dd, date.year, date.monthValue, date.dayOfMonth),
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.gray500,
            textAlign = TextAlign.Start,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "금액",
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray600,
            )

            val amountStr = if (type == LedgerTypeUiModel.Expense)
                stringResource(R.string.common_plus_str, amount.toMoneyFormat())
            else
                stringResource(R.string.common_minus_str, amount.toMoneyFormat())
            val amountColor = if (type == LedgerTypeUiModel.Expense)
                PickleTheme.colors.primary500
            else
                PickleTheme.colors.error50

            Text(
                modifier = Modifier.weight(1f),
                text = amountStr,
                style = PickleTheme.typography.body2Medium,
                color = amountColor,
                textAlign = TextAlign.End
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "유형",
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray600,
            )

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(paymentMethod.stringResId),
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray700,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LedgerDetailMemo(
            modifier = Modifier.padding(bottom = 30.dp),
            memo = memo ?: "",
            onMemoChange = {}
        )
    }
}

@Preview
@Composable
private fun LedgerDetailReceiptPreview() {
    PickleTheme {
        LedgerDetailReceipt(
            ledger = LedgerUiModel(
                id = 0L,
                type = LedgerTypeUiModel.Expense,
                amount = 1000000L,
                category = CategoryUiModel.Transport,
                description = "삼성전자 주식 팔았다",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethodUiModel.BankTransfer,
                memo = null
            )
        )
    }
}
