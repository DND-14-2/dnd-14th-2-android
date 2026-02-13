package com.smtm.pickle.presentation.designsystem.components.button.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

enum class PickleButtonSize {
    Small,
    Medium,
    Large,
}

data class PickleButtonSizeSpec(
    val height: Dp,
    val textStyle: TextStyle,
    val horizontalPadding: Dp,
)

@Composable
fun PickleButtonSize.toSpec(): PickleButtonSizeSpec {
    return when (this) {
        PickleButtonSize.Small -> PickleButtonSizeSpec(
            height = Dimensions.buttonHeightSmall,
            textStyle = PickleTheme.typography.body4Medium,
            horizontalPadding = Dimensions.buttonPaddingSmall,
        )

        PickleButtonSize.Medium -> PickleButtonSizeSpec(
            height = Dimensions.buttonHeightMedium,
            textStyle = PickleTheme.typography.body4Medium,
            horizontalPadding = Dimensions.buttonPaddingMedium,
        )

        PickleButtonSize.Large -> PickleButtonSizeSpec(
            height = Dimensions.buttonHeightLarge,
            textStyle = PickleTheme.typography.body1Bold,
            horizontalPadding = Dimensions.buttonPaddingLarge,
        )
    }
}
