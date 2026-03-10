package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarActionIcon
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarLayout
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarLogo
import com.smtm.pickle.presentation.designsystem.components.appbar.component.PickleAppBarTextAction
import com.smtm.pickle.presentation.designsystem.components.appbar.model.PickleAppBarAction
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleLogoAppBar(
    actions: List<PickleAppBarAction> = emptyList(),
    containerColor: Color = PickleTheme.colors.base0,
) {
    PickleAppBarLayout(
        containerColor = containerColor,
        start = { PickleAppBarLogo() },
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
    name = "PickleLogoAppBar - 액션 없음",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleLogoAppBarNoActionsPreview() {
    PickleTheme {
        PickleLogoAppBar()
    }
}

@Preview(
    name = "PickleLogoAppBar - 아이콘 액션",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleLogoAppBarIconActionsPreview() {
    PickleTheme {
        PickleLogoAppBar(
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
            ),
        )
    }
}

@Preview(
    name = "PickleLogoAppBar - 텍스트 액션",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleLogoAppBarTextActionPreview() {
    PickleTheme {
        PickleLogoAppBar(
            actions = listOf(
                PickleAppBarAction.Text(
                    label = "편집",
                    onClick = {},
                ),
            ),
        )
    }
}
