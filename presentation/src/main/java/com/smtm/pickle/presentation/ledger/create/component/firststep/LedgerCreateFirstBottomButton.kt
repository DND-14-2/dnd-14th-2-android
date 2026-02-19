package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun LedgerCreateFirstBottomButton(
    modifier: Modifier = Modifier,
    enableNext: Boolean,
    onNextClick: () -> Unit
) {
    PickleButton(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 14.dp)
            .fillMaxWidth(),
        text = stringResource(R.string.common_next),
        onClick = onNextClick,
        enabled = enableNext,
    )
}

@Preview(
    name = "LedgerCreateFirstBottomButtonPreview - Disable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateFirstBottomButtonDisablePreview() {
    PickleTheme {
        LedgerCreateFirstBottomButton(
            enableNext = false,
            onNextClick = {}
        )
    }
}

@Preview(
    name = "LedgerCreateFirstBottomButtonPreview - Enable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateFirstBottomButtonEnablePreview() {
    PickleTheme {
        LedgerCreateFirstBottomButton(
            enableNext = true,
            onNextClick = {}
        )
    }
}
