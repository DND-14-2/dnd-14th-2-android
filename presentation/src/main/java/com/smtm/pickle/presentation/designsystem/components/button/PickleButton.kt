package com.smtm.pickle.presentation.designsystem.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonSize
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonType
import com.smtm.pickle.presentation.designsystem.components.button.model.toColors
import com.smtm.pickle.presentation.designsystem.components.button.model.toSpec
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    type: PickleButtonType = PickleButtonType.Primary,
    size: PickleButtonSize = PickleButtonSize.Large,
) {
    val buttonColors = type.toColors()
    val buttonSize = size.toSpec()

    Button(
        modifier = modifier.requiredHeight(buttonSize.height),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors.containerColor,
            contentColor = buttonColors.contentColor,
            disabledContainerColor = buttonColors.disabledContainerColor,
            disabledContentColor = buttonColors.disabledContentColor,
        ),
        shape = RoundedCornerShape(Dimensions.radius),
        contentPadding = PaddingValues(horizontal = buttonSize.horizontalPadding)
    ) {
        Text(
            text = text,
            style = buttonSize.textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Primary",
    showBackground = true,
)
@Composable
private fun PickleButtonPrimaryPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Secondary",
    showBackground = true,
)
@Composable
private fun PickleButtonSecondaryPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Secondary
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Tertiary",
    showBackground = true,
)
@Composable
private fun PickleButtonTertiaryPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Tertiary
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Ghost",
    showBackground = true,
)
@Composable
private fun PickleButtonGhostPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Ghost
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Primary Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonPrimaryDisabledPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Primary
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Secondary Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonSecondaryDisabledPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Secondary
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Tertiary Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonTertiaryDisabledPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Tertiary
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Ghost Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonGhostDisabledPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Ghost
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Primary Small",
    showBackground = true,
)
@Composable
private fun PickleButtonPrimarySmallPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary,
            size = PickleButtonSize.Small
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Primary Medium",
    showBackground = true,
)
@Composable
private fun PickleButtonPrimaryMediumPreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary,
            size = PickleButtonSize.Medium
        )
    }
}

@Preview(
    name = "PickleButtonPreview - Primary Large",
    showBackground = true,
)
@Composable
private fun PickleButtonPrimaryLargePreview() {
    PickleTheme {
        PickleButton(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary,
            size = PickleButtonSize.Large
        )
    }
}
