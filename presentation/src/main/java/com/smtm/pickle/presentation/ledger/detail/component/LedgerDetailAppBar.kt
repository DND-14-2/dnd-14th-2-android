package com.smtm.pickle.presentation.ledger.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun LedgerDetailAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(Dimensions.appbarHeight)
            .padding(start = 4.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PickleIconButton(
            painter = painterResource(R.drawable.ic_app_bar_back),
            onClick = onBackClick,
            buttonSize = 48.dp,
        )

        Text(
            text = stringResource(R.string.ledger_detail_title),
            style = PickleTheme.typography.head4SemiBold,
            color = PickleTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.weight(1f))

        PickleIconButton(
            painter = painterResource(R.drawable.ic_ledger_detail_edit_pen),
            onClick = onEditClick,
            buttonSize = 32.dp,
        )

        Spacer(modifier = Modifier.width(4.dp))

        PickleIconButton(
            painter = painterResource(R.drawable.ic_ledger_detail_trashcan),
            onClick = onDeleteClick,
            buttonSize = 32.dp,
        )
    }
}

@Preview(
    name = "LedgerDetailTopBarPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerDetailAppBarPreview() {
    PickleTheme {
        LedgerDetailAppBar(
            onBackClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}
