package com.smtm.pickle.presentation.designsystem.components.appbar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
internal fun PickleAppBarLayout(
    modifier: Modifier = Modifier,
    containerColor: Color = PickleTheme.colors.base0,
    start: @Composable () -> Unit = {},
    center: @Composable () -> Unit = {},
    end: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.appBarContainerModifier(containerColor)
    ) {
        Box(Modifier.align(Alignment.CenterStart)) { start() }
        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center,
        ) { center() }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.appBarActionsSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) { end() }
    }
}
