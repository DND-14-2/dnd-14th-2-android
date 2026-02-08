package com.smtm.pickle.presentation.ledger.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

private const val MEMO_MAX_LENGTH = 100

@Composable
fun LedgerDetailMemo(
    modifier: Modifier = Modifier,
    memo: String?,
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(Dimensions.radius))
            .background(PickleTheme.colors.gray50)
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.gray50),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (memo.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.ledger_create_memo_hint),
                        style = PickleTheme.typography.body4Medium,
                        color = PickleTheme.colors.gray600,
                    )
                } else {
                    Text(
                        text = memo,
                        style = PickleTheme.typography.body4Medium,
                        color = PickleTheme.colors.gray800,
                    )
                }
            }

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "${memo?.length ?: 0}/$MEMO_MAX_LENGTH",
                style = PickleTheme.typography.caption1Medium,
                color = PickleTheme.colors.gray600,
            )
        }
    }
}
