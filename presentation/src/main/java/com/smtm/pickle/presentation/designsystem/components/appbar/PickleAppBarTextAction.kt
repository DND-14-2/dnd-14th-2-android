package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleAppBarTextAction(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        modifier = Modifier.clickable(onClick = onClick),
        style = PickleTheme.typography.body2Medium,
        color = PickleTheme.colors.gray700,
    )
}

@Preview(
    name = "PickleAppBarTextAction",
    showBackground = true,
)
@Composable
private fun PickleAppBarTextActionPreview() {
    PickleTheme {
        PickleAppBarTextAction(
            text = "취소",
            onClick = {}
        )
    }
}
