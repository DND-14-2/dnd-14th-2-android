package com.smtm.pickle.presentation.designsystem.components.appbar.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
internal fun PickleAppBarActionIcon(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    contentDescription: String? = null,
) {
    PickleIconButton(
        painter = painterResource(iconRes),
        onClick = onClick,
        contentDescription = contentDescription,
        buttonSize = Dimensions.iconLarge,
        iconSize = Dimensions.iconMedium,
    )
}

@Preview(
    name = "PickleAppBarActionIcon - Search",
    showBackground = true,
)
@Composable
private fun PickleAppBarActionIconSearchPreview() {
    PickleTheme {
        PickleAppBarActionIcon(
            iconRes = R.drawable.ic_app_bar_search,
            onClick = {},
            contentDescription = "검색",
        )
    }
}

@Preview(
    name = "PickleAppBarActionIcon - Option",
    showBackground = true,
)
@Composable
private fun PickleAppBarActionIconOptionPreview() {
    PickleTheme {
        PickleAppBarActionIcon(
            iconRes = R.drawable.ic_app_bar_option,
            onClick = {},
            contentDescription = "검색",
        )
    }
}

@Preview(
    name = "PickleAppBarActionIcon - Close",
    showBackground = true,
)
@Composable
private fun PickleAppBarActionIconClosePreview() {
    PickleTheme {
        PickleAppBarActionIcon(
            iconRes = R.drawable.ic_app_bar_close,
            onClick = {},
            contentDescription = "검색",
        )
    }
}
