package com.smtm.pickle.presentation.ledger.create.component.secondstep

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateHeaderText

private const val MEMO_MAX_LENGTH = 100

@Composable
fun LedgerCreateMemo(
    modifier: Modifier = Modifier,
    memo: String,
    onMemoChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        LedgerCreateHeaderText(
            text = stringResource(R.string.ledger_create_memo_header),
        )

        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(Dimensions.radius))
                .background(PickleTheme.colors.gray50)
                .onFocusChanged { isFocused = it.isFocused }
                .then(
                    if (isFocused) {
                        Modifier.border(
                            shape = RoundedCornerShape(Dimensions.radius),
                            width = 1.5.dp,
                            color = PickleTheme.colors.primary400
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(12.dp),
            value = memo,
            textStyle = PickleTheme.typography.body3Regular,
            onValueChange = { new ->
                if (new.length <= MEMO_MAX_LENGTH) {
                    onMemoChange(new)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus(force = true) }
            ),
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (memo.isEmpty()) {
                            Text(
                                text = stringResource(R.string.ledger_create_memo_hint),
                                style = PickleTheme.typography.body3Regular,
                                color = PickleTheme.colors.gray600,
                            )
                        }

                        innerTextField()
                    }

                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = "${memo.length}/$MEMO_MAX_LENGTH",
                        style = PickleTheme.typography.body3Regular,
                        color = PickleTheme.colors.gray600,
                    )
                }
            }
        )
    }
}

@Preview(
    name = "LedgerCreateMemoPreview - Memo Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateMemoEmptyPreview() {
    PickleTheme {
        LedgerCreateMemo(
            memo = "",
            onMemoChange = {}
        )
    }
}

@Preview(
    name = "LedgerCreateMemoPreview - Memo Non Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateMemoPreview() {
    PickleTheme {
        LedgerCreateMemo(
            memo = "짱 맛있어염.",
            onMemoChange = {}
        )
    }
}