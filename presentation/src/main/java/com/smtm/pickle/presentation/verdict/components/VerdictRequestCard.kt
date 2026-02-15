package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun VerdictNewRequestCard(
    modifier: Modifier = Modifier,
    onVerdictRequestClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFFFFF5E6),
                    shape = RoundedCornerShape(Dimensions.profileRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.illust_mypage_gavel),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "판결 요청이 도착했어요",
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.gray700
                )
                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    painter = painterResource(R.drawable.ic_common_new),
                    contentDescription = null
                )
            }

            Text(
                text = "소비를 확인하고 판결해보세요",
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray600
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = onVerdictRequestClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.primary50,
                contentColor = PickleTheme.colors.primary500
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(45.dp, 32.dp)
        ) {
            Text("보기")
        }
    }
}

@Composable
fun VerdictRequestEmptyCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFFFFF5E6),
                    shape = RoundedCornerShape(Dimensions.profileRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.illust_mypage_gavel),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "판결 요청이 도착했어요",
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.gray700
                )
                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    painter = painterResource(R.drawable.ic_common_new),
                    contentDescription = null
                )
            }

            Text(
                text = "소비를 확인하고 판결해보세요",
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray600
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun VerdictNewRequestCardPreview() {
    PickleTheme {
        VerdictNewRequestCard() {}
    }
}

@Preview(showBackground = true)
@Composable
private fun VerdictRequestEmptyCardPreview() {
    PickleTheme {
        VerdictRequestEmptyCard()
    }
}
