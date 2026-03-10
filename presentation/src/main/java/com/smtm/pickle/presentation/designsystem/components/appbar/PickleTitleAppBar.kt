package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarActionIcon
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarBackButton
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarLayout
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarTextAction
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarTitle
import com.smtm.pickle.presentation.designsystem.components.appbar.model.PickleAppBarAction
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleTitleAppBar(
    title: String,
    onBack: () -> Unit,
    actions: List<PickleAppBarAction> = emptyList(),
    containerColor: Color = PickleTheme.colors.base0,
) {
    PickleAppBarLayout(
        containerColor = containerColor,
        start = { PickleAppBarBackButton(onClick = onBack) },
        center = { PickleAppBarTitle(text = title) },
        end = {
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
                PickleAppBarAction.Icon(
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
                PickleAppBarAction.Text(
                    label = "완료",
                    onClick = {},
                ),
            ),
        )
    }
}
