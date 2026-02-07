package com.smtm.pickle.presentation.mypage.tabs.activity.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.mypage.MyPageUiState

private const val MAX_VISIBLE_ITEMS = 3

@Composable
fun ActivityPendingJudgment(
    modifier: Modifier = Modifier,
    pendingJudgments: List<MyPageUiState.PendingJudgmentState> = emptyList(),
) {
    var isExpanded by remember { mutableStateOf(false) }
    val hasMoreItems = pendingJudgments.size > MAX_VISIBLE_ITEMS
    val visibleItems = if (isExpanded) pendingJudgments else pendingJudgments.take(MAX_VISIBLE_ITEMS)

    PickleCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .animateContentSize(),
        contentPadding = PaddingValues(0.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "내가 보류한 심판",
                style = PickleTheme.typography.body1Bold,
                color = PickleTheme.colors.gray700
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { /* TODO 리스트 페이지 */ }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_mypage_arrow_right),
                    contentDescription = "더보기",
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (pendingJudgments.isEmpty()) {
            PendingJudgmentEmptyItem(modifier = Modifier.padding(horizontal = 16.dp))
        } else {
            visibleItems.forEach { judgment ->
                PendingJudgmentItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    iconRes = judgment.iconRes,
                    title = judgment.title,
                    price = String.format(java.util.Locale.KOREA, "%,d원", judgment.price),
                    onClick = { /* 심판하기 클릭 이벤트 */ }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (hasMoreItems) {
            HorizontalDivider(thickness = 1.dp, color = PickleTheme.colors.gray100)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.buttonHeight)
                    .clickable { isExpanded = !isExpanded }
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isExpanded) "접기" else "전체보기",
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.gray600
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.rotate(if (isExpanded) 180f else 0f),
                    painter = painterResource(R.drawable.ic_mypage_arrow_bottom),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
private fun ActivityPendingJudgmentEmptyPreview() {
    PickleTheme {
        ActivityPendingJudgment()
    }
}

@Preview
@Composable
private fun ActivityPendingJudgmentPreview() {
    PickleTheme {
        ActivityPendingJudgment(
            pendingJudgments = listOf(
                MyPageUiState.PendingJudgmentState(
                    id = "1",
                    title = "식비",
                    price = 10000L,
                    iconRes = R.drawable.ic_mypage_coin
                ),
                MyPageUiState.PendingJudgmentState(
                    id = "2",
                    title = "식비",
                    price = 10000L,
                    iconRes = R.drawable.ic_mypage_coin
                ),
                MyPageUiState.PendingJudgmentState(
                    id = "3",
                    title = "식비",
                    price = 10000L,
                    iconRes = R.drawable.ic_mypage_coin
                ),
                MyPageUiState.PendingJudgmentState(
                    id = "4",
                    title = "식비",
                    price = 10000L,
                    iconRes = R.drawable.ic_mypage_coin
                ),
            )
        )
    }
}
