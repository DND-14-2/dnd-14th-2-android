package com.smtm.pickle.presentation.verdict.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerTypeUiModel
import com.smtm.pickle.presentation.common.model.ledger.LedgerUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.verdict.model.MateUiModel
import com.smtm.pickle.presentation.verdict.model.VerdictUiModel
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun VerdictListItem(
    amount: Long,
    description: String,
    @DrawableRes categoryIconResId: Int,
    @DrawableRes paymentMethodIconResId: Int,
    status: VerdictStatus,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(Dimensions.radiusSurface),
                ambientColor = PickleTheme.colors.gray700,
                spotColor = PickleTheme.colors.gray700
            )
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(Dimensions.radiusSurface),
        colors = CardDefaults.cardColors(containerColor = PickleTheme.colors.base0),
        border = BorderStroke(width = 1.dp, color = PickleTheme.colors.gray100),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color = PickleTheme.colors.gray100),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(categoryIconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = description,
                    style = PickleTheme.typography.body2Medium,
                    color = PickleTheme.colors.gray700
                )
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${amount.toMoneyFormat()}원",
                        style = PickleTheme.typography.caption1Medium,
                        color = PickleTheme.colors.gray600
                    )
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(color = PickleTheme.colors.gray200)
                    )
                    Image(
                        painter = painterResource(paymentMethodIconResId),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        HorizontalDivider(color = PickleTheme.colors.gray100)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when (status) {
                VerdictStatus.PENDING -> {
                    StatusChip(
                        state = "미완료",
                        containerColor = PickleTheme.colors.background100,
                        contentColor = PickleTheme.colors.gray700
                    )
                }

                VerdictStatus.COMPLETED -> {
                    StatusChip(
                        state = "완료",
                        containerColor = PickleTheme.colors.primary50,
                        contentColor = PickleTheme.colors.primary500
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusChip(
    state: String,
    containerColor: Color,
    contentColor: Color
) {
    Box(
        modifier = Modifier
            .size(width = 40.dp, height = 24.dp)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(6.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state,
            style = PickleTheme.typography.caption1Medium,
            color = contentColor
        )
    }
}

@Preview
@Composable
private fun VerdictListItemPreview() {
    PickleTheme {
        val item = VerdictUiModel(
            id = 1,
            ledger = LedgerUiModel(
                id = 1L,
                type = LedgerTypeUiModel.Expense,
                amount = 15000,
                category = CategoryUiModel.Food,
                description = "가계부 15자 입력",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethodUiModel.Cash,
                memo = null
            ),
            defendant = MateUiModel(1, "홍길동"),
            status = VerdictStatus.PENDING,
            createdAt = LocalDateTime.now()
        )
        VerdictListItem(
            amount = item.ledger.amount,
            description = item.ledger.description,
            categoryIconResId = item.ledger.category.iconResId,
            paymentMethodIconResId = item.ledger.paymentMethod.iconResId,
            status = item.status,
            onItemClick = {}
        )
    }
}
