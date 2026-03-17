package com.smtm.pickle.presentation.designsystem.components.appbar.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.model.PickleAppBarAction
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
internal fun PickleAppBarActions(actions: List<PickleAppBarAction>) {
    actions.forEach { action ->
        when (action) {
            is PickleAppBarAction.Icon -> PickleAppBarActionIcon(
                iconRes = action.icon,
                contentDescription = action.contentDescription,
                onClick = action.onClick
            )

            is PickleAppBarAction.Text -> PickleAppBarTextAction(
                text = action.label,
                onClick = action.onClick
            )
        }
    }
}

@Preview(
    name = "PickleAppBarActions - 아이콘",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarActionsIconPreview() {
    PickleTheme {
        Row {
            PickleAppBarActions(
                actions = listOf(
                    PickleAppBarAction.Icon(
                        icon = R.drawable.ic_app_bar_option,
                        contentDescription = "옵션",
                        onClick = {},
                    )
                )
            )
        }
    }
}

@Preview(
    name = "PickleAppBarActions - 텍스트",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarActionsTextPreview() {
    PickleTheme {
        Row {
            PickleAppBarActions(
                actions = listOf(
                    PickleAppBarAction.Text(
                        label = "완료",
                        onClick = {},
                    )
                )
            )
        }
    }
}

@Preview(
    name = "PickleAppBarActions - 아이콘 복수",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarActionsMultiIconPreview() {
    PickleTheme {
        Row {
            PickleAppBarActions(
                actions = listOf(
                    PickleAppBarAction.Icon(
                        icon = R.drawable.ic_app_bar_statistics,
                        contentDescription = "통계",
                        onClick = {},
                    ),
                    PickleAppBarAction.Icon(
                        icon = R.drawable.ic_app_bar_search,
                        contentDescription = "검색",
                        onClick = {},
                    ),
                )
            )
        }
    }
}
