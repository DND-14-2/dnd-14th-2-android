package com.smtm.pickle.presentation.ledger.create.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun LedgerCreateHeaderText(
    modifier: Modifier = Modifier,
    text: String,
    highlightText: String? = null,
    highlightTextColor: Color = PickleTheme.colors.primary400
) {
    val annotatedString = if (highlightText != null) {
        val highlightTextStartIndex = text.indexOf(highlightText)
        val highlightTextEndIndex = highlightTextStartIndex + highlightText.length
        buildAnnotatedString {
            append(text)
            if (highlightTextStartIndex >= 0) {
                addStyle(
                    style = SpanStyle(color = highlightTextColor),
                    start = highlightTextStartIndex,
                    end = highlightTextEndIndex
                )
            }
        }
    } else {
        buildAnnotatedString { append(text) }
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        style = PickleTheme.typography.head4SemiBold,
        color = PickleTheme.colors.gray800
    )
}

@Preview(
    name = "LedgerCreateHeaderTextPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateHeaderTextPreview() {
    PickleTheme {
        LedgerCreateHeaderText(
            text = "가계부 내용을 입력해주세요.*",
            highlightText = "*"
        )
    }
}
