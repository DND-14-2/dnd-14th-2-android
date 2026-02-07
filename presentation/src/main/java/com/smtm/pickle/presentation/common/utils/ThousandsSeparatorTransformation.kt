package com.smtm.pickle.presentation.common.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat

class ThousandsSeparatorTransformation(
    private val emptyText: String = "0"
) : VisualTransformation {

    private val formatter = DecimalFormat("#,###")

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val raw = originalText.ifEmpty { emptyText }
        val formatted = formatter.format(raw.toLongOrNull() ?: 0L)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (originalText.isEmpty()) return 0
                if (offset <= 0) return 0

                val totalDigits = originalText.length
                val digitsAfterOffset = totalDigits - offset

                val totalCommas = (totalDigits - 1) / 3
                val commasAfterOffset = if (digitsAfterOffset > 0) (digitsAfterOffset - 1) / 3 else 0
                val commasBeforeOffset = totalCommas - commasAfterOffset

                return (offset + commasBeforeOffset).coerceIn(0, formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (originalText.isEmpty()) return 0
                if (offset <= 0) return 0

                val commasBeforeOffset = formatted.take(offset.coerceAtMost(formatted.length))
                    .count { it == ',' }

                return (offset - commasBeforeOffset).coerceIn(0, originalText.length)
            }
        }
        return TransformedText(
            text = AnnotatedString(text = formatted),
            offsetMapping = offsetMapping
        )
    }
}