package com.smtm.pickle.presentation.verdict.jurorlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextField
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun VerdictSearchAppBar(
    modifier: Modifier = Modifier,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    hint: String = "",
    onSearch: (() -> Unit)? = null,
    onBackClick: () -> Unit,
) {
    val hasText = searchValue.isNotEmpty()
    val trailingIcon = remember(hasText) {
        if (hasText) {
            @Composable {
                IconButton(onClick = { onSearchValueChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_close),
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.iconMedium)
                    )
                }
            }
        } else {
            null
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PickleTheme.colors.base0)
            .statusBarsPadding()
            .height(Dimensions.appbarHeight)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PickleTextField(
            modifier = Modifier.weight(1f),
            value = searchValue,
            onValueChange = onSearchValueChange,
            height = Dimensions.searchHeight,
            hint = hint,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search_magnifier),
                    contentDescription = null,
                )
            },
            trailingIcon = trailingIcon,
            keyboardType = KeyboardType.Text,
            inputState = InputState.Idle,
            imeAction = ImeAction.Search,
            onImeAction = onSearch,
        )
        Spacer(modifier = Modifier.width(10.dp))

        TextButton(
            onClick = onBackClick,
            contentPadding = PaddingValues(horizontal = 0.dp),
            modifier = Modifier.width(28.dp),
        ) {
            Text(
                text = stringResource(id = R.string.juror_list_cancel),
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray700
            )
        }
    }
}

@Preview
@Composable
fun VerdictSearchAppBarPreview() {
    var text by remember { mutableStateOf("") }
    PickleTheme {
        VerdictSearchAppBar(
            searchValue = text,
            onSearchValueChange = { text = it },
            onBackClick = {},
            hint = stringResource(id = R.string.juror_list_search_hint)
        )
    }
}
