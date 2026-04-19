package com.smtm.pickle.presentation.designsystem.components.appbar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

internal fun Modifier.appBarContainerModifier(containerColor: Color): Modifier =
    this.fillMaxWidth()
        .background(containerColor)
        .padding(horizontal = Dimensions.appBarHorizontalSpacing)
        .statusBarsPadding()
        .requiredHeight(Dimensions.appbarHeight)
