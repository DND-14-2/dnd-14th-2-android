package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleAppBarV2(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    centerContent: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = modifier
            .requiredHeight(Dimensions.appbarHeight)
            .background(color = PickleTheme.colors.base0)
            .padding(horizontal = Dimensions.appBarHorizontalSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        navigationIcon()
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            centerContent()
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimensions.appBarActionsSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
    }
}

@Preview(
    name = "PickleAppBarType1",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarType1Preview() {
    PickleTheme {
        PickleAppBarV2(
            navigationIcon = {
                PickleAppBarLogo()
            },
            actions = {
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_option,
                    onClick = {},
                    contentDescription = "옵션",
                )
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_search,
                    onClick = {},
                    contentDescription = "검색",
                )
            }
        )
    }
}

@Preview(
    name = "PickleAppBarType2",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarType2Preview() {
    PickleTheme {
        PickleAppBarV2(
            navigationIcon = {
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_back,
                    onClick = {},
                    contentDescription = "뒤로가기",
                )
            },
            centerContent = {
                PickleAppBarTitle(text = "타이틀")
            },
            actions = {
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_option,
                    onClick = {},
                    contentDescription = "옵션",
                )
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_search,
                    onClick = {},
                    contentDescription = "검색",
                )
            }
        )
    }
}

@Preview(
    name = "PickleAppBarType3",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarType3Preview() {
    PickleTheme {
        PickleAppBarV2(
            navigationIcon = {
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_back,
                    onClick = {},
                    contentDescription = "뒤로가기",
                )
            },
            actions = {
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_option,
                    onClick = {},
                    contentDescription = "옵션",
                )
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_search,
                    onClick = {},
                    contentDescription = "검색",
                )
            }
        )
    }
}

@Preview(
    name = "PickleAppBarType4",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarType4Preview() {
    PickleTheme {
        PickleAppBarV2(
            actions = {
                PickleAppBarActionIcon(
                    iconRes = R.drawable.ic_app_bar_close,
                    onClick = {},
                    contentDescription = "닫기",
                )
            }
        )
    }
}

@Preview(
    name = "PickleAppBarType5",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarType5Preview() {
    PickleTheme {
        PickleAppBarV2(
            centerContent = {
                PickleAppBarSearchField(
                    value = "",
                    onValueChange = {},
                    hint = "검색어를 입력해주세요",
                    onSearch = {},
                )
            },
            actions = {
                PickleAppBarTextAction(
                    text = "취소",
                    onClick = {},
                )
            }
        )
    }
}
