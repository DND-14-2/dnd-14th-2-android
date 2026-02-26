package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.domain.model.verdict.VerdictType
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.model.ledger.CategoryUiModel
import com.smtm.pickle.presentation.common.model.ledger.PaymentMethodUiModel
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun VerdictPendingBottomSheetContent(
    jurorNickname: String,
    defendantNickname: String,
    title: String,
    category: CategoryUiModel,
    amount: Long,
    paymentMethod: PaymentMethodUiModel,
    modifier: Modifier = Modifier,
    verdictType: VerdictType = VerdictType.Pending,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "소비 심판 내용",
            style = PickleTheme.typography.head3Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        ProfileSection(
            jurorNickname = jurorNickname,
            defendantNickname = defendantNickname,
        )
        HorizontalDivider(thickness = 1.dp, color = PickleTheme.colors.gray100)

        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ConsumptionItem(
                title = title,
                category = category,
                amount = amount,
                paymentMethod = paymentMethod,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 11.dp)
            )

            when (verdictType) {
                VerdictType.Guilty -> {
                    JudgmentResultChip(
                        resultText = "유죄",
                        containerColor = PickleTheme.semantic.guiltyBackground,
                        contentColor = PickleTheme.semantic.guilty
                    )
                }

                VerdictType.NotGuilty -> {
                    JudgmentResultChip(
                        resultText = "무죄",
                        containerColor = PickleTheme.semantic.innocentBackground,
                        contentColor = PickleTheme.semantic.innocent
                    )
                }

                VerdictType.Pending -> {
                    JudgmentResultChip(
                        resultText = "미완료",
                        containerColor = PickleTheme.colors.gray100,
                        contentColor = PickleTheme.colors.gray700
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileSection(
    jurorNickname: String,
    defendantNickname: String,
) {
    Row(
        modifier = Modifier.padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        BottomSheetProfile(name = jurorNickname)
        Image(
            painter = painterResource(R.drawable.ic_verdict_arrow_right),
            contentDescription = null,
            modifier = Modifier.padding(13.dp)
        )
        BottomSheetProfile(name = defendantNickname)
    }
}

@Composable
private fun BottomSheetProfile(
    modifier: Modifier = Modifier,
    name: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(Dimensions.profileSizeCircle)
                .clip(CircleShape)
                .background(PickleTheme.colors.gray100)
        ) {
            Image(
                painter = painterResource(R.drawable.illust_profile_default),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(45.dp)
            )
        }
        Text(
            text = name,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.gray800
        )
    }
}

@Composable
private fun JudgmentResultChip(
    resultText: String,
    containerColor: Color,
    contentColor: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(containerColor)
            .padding(horizontal = 4.dp, vertical = 3.5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = resultText,
            style = PickleTheme.typography.caption1Medium,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
    }
}

@Preview(
    name = "bottom sheet - pending",
    showBackground = true
)
@Composable
private fun VerdictPendingBottomSheetContentPendingPreview() {
    PickleTheme {
        VerdictPendingBottomSheetContent(
            jurorNickname = "홍길동",
            defendantNickname = "김철수",
            title = "가계부 15자 입력",
            category = CategoryUiModel.Food,
            amount = 15000L,
            paymentMethod = PaymentMethodUiModel.Cash,
        )
    }
}

@Preview(
    name = "bottom sheet - guilty",
    showBackground = true
)
@Composable
private fun VerdictPendingBottomSheetContentGuiltyPreview() {
    PickleTheme {
        VerdictPendingBottomSheetContent(
            jurorNickname = "홍길동",
            defendantNickname = "김철수",
            title = "가계부 15자 입력",
            category = CategoryUiModel.Food,
            amount = 15000L,
            paymentMethod = PaymentMethodUiModel.Cash,
            verdictType = VerdictType.Guilty
        )
    }
}

@Preview(
    name = "bottom sheet - innocent",
    showBackground = true
)
@Composable
private fun VerdictPendingBottomSheetContentInnocentPreview() {
    PickleTheme {
        VerdictPendingBottomSheetContent(
            jurorNickname = "홍길동",
            defendantNickname = "김철수",
            title = "가계부 15자 입력",
            category = CategoryUiModel.Food,
            amount = 15000L,
            paymentMethod = PaymentMethodUiModel.Cash,
            verdictType = VerdictType.NotGuilty
        )
    }
}
