package com.smtm.pickle.presentation.designsystem.components.appbar.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextField
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
internal fun PickleAppBarSearchField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    onSearch: (() -> Unit)? = null,
) {
    PickleTextField.Search(
        modifier = modifier.padding(end = 6.dp),
        value = value,
        onValueChange = onValueChange,
        height = Dimensions.searchHeight,
        hint = hint,
        onSearch = onSearch,
    )
}

@Preview(
    name = "PickleAppBarSearchField - 빈 상태",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarSearchFieldEmptyPreview() {
    PickleTheme {
        PickleAppBarSearchField(
            value = "",
            onValueChange = {},
            hint = "검색어를 입력하세요",
        )
    }
}

@Preview(
    name = "PickleAppBarSearchField - 입력 상태",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarSearchFieldFilledPreview() {
    PickleTheme {
        PickleAppBarSearchField(
            value = "검색어",
            onValueChange = {},
            hint = "검색어를 입력하세요",
            onSearch = {},
        )
    }
}