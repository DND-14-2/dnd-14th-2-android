package com.smtm.pickle.presentation.designsystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleIconButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonSize: Dp = Dimensions.iconMedium,
    iconSize: Dp = Dimensions.iconMedium,
    contentDescription: String? = null,
) {
    IconButton(
        modifier = modifier.size(buttonSize),
        enabled = enabled,
        onClick = onClick,
    ) {
        Image(
            modifier = Modifier.size(iconSize),
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}