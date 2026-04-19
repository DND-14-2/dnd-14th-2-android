package com.smtm.pickle.presentation.designsystem.components.appbar.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
internal fun PickleAppBarTitle(
    text: String,
    style: TextStyle = PickleTheme.typography.head4SemiBold,
    color: Color = PickleTheme.colors.gray800,
) {
    Text(
        text = text,
        style = style,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview(
    name = "PickleAppBarTitle",
    showBackground = true,
)
@Composable
private fun PickleAppBarTitlePreview() {
    PickleTheme {
        PickleAppBarTitle(text = "타이틀")
    }
}