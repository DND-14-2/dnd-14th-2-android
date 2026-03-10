package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
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
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor)
            .padding(horizontal = Dimensions.appBarHorizontalSpacing)
            .statusBarsPadding()
            .requiredHeight(Dimensions.appbarHeight)
    ) {
        Box(Modifier.align(Alignment.CenterStart)) { start() }
        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center,
        ) { center() }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
        ) { end() }
    }
}