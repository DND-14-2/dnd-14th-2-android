package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import kotlin.math.abs

@Composable
fun ConsumptionItem(
    title: String,
    amount: Long,
    category: CategoryUiModel,
    paymentMethod: PaymentMethodUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(PickleTheme.colors.gray100),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(category.iconResId),
                contentDescription = stringResource(category.stringResId),
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray800,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "-${abs(amount).toMoneyFormat()}",
                    style = PickleTheme.typography.caption1Medium,
                    color = PickleTheme.colors.gray600,
                )
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(PickleTheme.colors.gray200)
                )
                Image(
                    painter = painterResource(paymentMethod.iconResId),
                    contentDescription = stringResource(paymentMethod.stringResId),
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConsumptionItemPreview() {
    PickleTheme {
        ConsumptionItem(
            title = "소비내역 15자 입력",
            amount = -150000,
            category = CategoryUiModel.Food,
            paymentMethod = PaymentMethodUiModel.Cash
        )
    }
}
