package com.smtm.pickle.presentation.ledger.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

private const val MEMO_MAX_LENGTH = 100

@Composable
fun LedgerDetailMemo(
    modifier: Modifier = Modifier,
    memo: String,
    onMemoChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(Dimensions.radius))
            .background(PickleTheme.colors.gray50)
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
