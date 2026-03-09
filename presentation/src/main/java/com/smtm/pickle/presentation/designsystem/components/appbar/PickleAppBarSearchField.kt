package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextField
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleAppBarSearchField(
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