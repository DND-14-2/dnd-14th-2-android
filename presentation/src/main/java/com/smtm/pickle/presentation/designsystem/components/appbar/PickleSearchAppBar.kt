package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarLayout
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarSearchField
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarTextAction
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleSearchAppBar(
    value: String,
    onValueChange: (String) -> Unit,
    onCancel: () -> Unit,
    hint: String = "",
    containerColor: Color = PickleTheme.colors.base0,
) {
    PickleAppBarLayout(
        containerColor = containerColor,
        center = {
            PickleAppBarSearchField(
                value = value,
                onValueChange = onValueChange,
                hint = hint,
            )
        },
        end = {
            PickleAppBarTextAction(
                text = stringResource(R.string.common_cancel),
                onClick = onCancel
            )
        }
    )
}

@Preview(
    name = "PickleSearchAppBar - 빈 상태",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleSearchAppBarEmptyPreview() {
    PickleTheme {
        PickleSearchAppBar(
            value = "",
            onValueChange = {},
            onCancel = {},
            hint = "검색어를 입력하세요",
        )
    }
}

@Preview(
    name = "PickleSearchAppBar - 입력 상태",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleSearchAppBarFilledPreview() {
    PickleTheme {
        PickleSearchAppBar(
            value = "검색어",
            onValueChange = {},
            onCancel = {},
            hint = "검색어를 입력하세요",
        )
    }
}
