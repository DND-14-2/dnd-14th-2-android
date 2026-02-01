package com.smtm.pickle.presentation.login.nickname.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
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

@Composable
fun CheckDuplicateButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(57.dp)
            .height(24.dp),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = PickleTheme.colors.primary500,
            containerColor = PickleTheme.colors.primary100,
            disabledContentColor = PickleTheme.colors.gray500,
            disabledContainerColor = PickleTheme.colors.gray200,
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = stringResource(R.string.nickname_check_duplicate),
            style = PickleTheme.typography.caption1Medium
        )
    }
}

@Preview
@Composable
private fun CheckDuplicateButtonPreview() {
    PickleTheme {
        CheckDuplicateButton(
            enabled = true,
            onClick = {}
        )
    }
}
