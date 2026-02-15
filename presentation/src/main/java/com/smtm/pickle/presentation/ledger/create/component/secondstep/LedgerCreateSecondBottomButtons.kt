package com.smtm.pickle.presentation.ledger.create.component.secondstep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun LedgerCreateSecondBottomButtons(
    modifier: Modifier = Modifier,
    enabledSuccess: Boolean,
    onPreviousClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            modifier = Modifier
                .height(Dimensions.buttonHeight)
                .width(96.dp),
            onClick = onPreviousClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.gray100,
                contentColor = PickleTheme.colors.gray600,
            ),
            shape = RoundedCornerShape(Dimensions.radius)
        ) {
            Text(
                text = stringResource(R.string.common_previous),
                style = PickleTheme.typography.body1Bold,
            )
        }

        Button(
            modifier = Modifier
                .height(Dimensions.buttonHeight)
                .fillMaxWidth()
                .weight(1f),
            onClick = onSuccessClick,
            enabled = enabledSuccess,
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.primary400,
                disabledContainerColor = PickleTheme.colors.background100,
                contentColor = PickleTheme.colors.base0,
                disabledContentColor = PickleTheme.colors.gray600,
            ),
            shape = RoundedCornerShape(Dimensions.radius)
        ) {
            Text(
                text = stringResource(R.string.ledger_create_success),
                style = PickleTheme.typography.body1Bold,
            )
        }
    }
}

@Preview(
    name = "LedgerCreateSecondBottomButtonsPreview - Disable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondBottomButtonsDisablePreview() {
    PickleTheme {
        LedgerCreateSecondBottomButtons(
            enabledSuccess = false,
            onPreviousClick = {},
            onSuccessClick = {}
        )
    }
}

@Preview(
    name = "LedgerCreateSecondBottomButtonsPreview - Enable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondBottomButtonsEnablePreview() {
    PickleTheme {
        LedgerCreateSecondBottomButtons(
            enabledSuccess = true,
            onPreviousClick = {},
            onSuccessClick = {}
        )
    }
}