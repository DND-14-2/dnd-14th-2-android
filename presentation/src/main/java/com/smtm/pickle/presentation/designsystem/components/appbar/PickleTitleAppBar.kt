package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.model.AppBarAction
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleTitleAppBar(
    title: String,
    onBack: () -> Unit,
    actions: List<AppBarAction> = emptyList(),
) {
    PickleAppBarLayout(
        start = { PickleAppBarBackButton(onClick = onBack) },
        center = { PickleAppBarTitle(text = title) },
        end = {
            actions.forEach { action ->
                when (action) {
                    is AppBarAction.Icon -> PickleAppBarActionIcon(
                        iconRes = action.icon,
                        contentDescription = action.contentDescription,
                        onClick = action.onClick
                    )

                    is AppBarAction.Text -> PickleAppBarTextAction(
                        text = action.label,
                        onClick = action.onClick
                    )
                }
            }
        }
    )
}

@Preview(
    name = "PickleTitleAppBar - 액션 없음",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleTitleAppBarNoActionsPreview() {
    PickleTheme {
        PickleTitleAppBar(
            title = "타이틀",
            onBack = {},
        )
    }
}

@Preview(
    name = "PickleTitleAppBar - 아이콘 액션",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleTitleAppBarIconActionsPreview() {
    PickleTheme {
        PickleTitleAppBar(
            title = "타이틀",
            onBack = {},
            actions = listOf(
                AppBarAction.Icon(
                    icon = R.drawable.ic_app_bar_option,
                    contentDescription = "옵션",
                    onClick = {},
                ),
            ),
        )
    }
}

@Preview(
    name = "PickleTitleAppBar - 텍스트 액션",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleTitleAppBarTextActionPreview() {
    PickleTheme {
        PickleTitleAppBar(
            title = "타이틀",
            onBack = {},
            actions = listOf(
                AppBarAction.Text(
                    label = "완료",
                    onClick = {},
                ),
            ),
        )
    }
}
