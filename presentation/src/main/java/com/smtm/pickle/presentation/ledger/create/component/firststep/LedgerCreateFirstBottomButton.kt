package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun LedgerCreateFirstBottomButton(
    modifier: Modifier = Modifier,
    enableNext: Boolean,
    onNextClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 14.dp)
            .height(Dimensions.buttonHeight),
        onClick = onNextClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = PickleTheme.colors.primary400,
            disabledContentColor = PickleTheme.colors.gray100,
        ),
        shape = RoundedCornerShape(Dimensions.radius),
        enabled = enableNext,
    ) {
        Text(
            text = stringResource(R.string.common_next),
            style = PickleTheme.typography.body1Bold,
            color = if (enableNext) PickleTheme.colors.base0 else PickleTheme.colors.gray600,
        )
    }
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
