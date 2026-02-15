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
fun PickleButtonV2(
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
    name = "PickleButtonV2Preview - Primary",
    showBackground = true,
)
@Composable
private fun PickleButtonV2PrimaryPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Secondary",
    showBackground = true,
)
@Composable
private fun PickleButtonV2SecondaryPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Secondary
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Tertiary",
    showBackground = true,
)
@Composable
private fun PickleButtonV2TertiaryPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Tertiary
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Ghost",
    showBackground = true,
)
@Composable
private fun PickleButtonV2GhostPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Ghost
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Primary Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonV2PrimaryDisabledPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Primary
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Secondary Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonV2SecondaryDisabledPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Secondary
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Tertiary Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonV2TertiaryDisabledPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Tertiary
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Ghost Disabled",
    showBackground = true,
)
@Composable
private fun PickleButtonV2GhostDisabledPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            enabled = false,
            type = PickleButtonType.Ghost
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Primary Small",
    showBackground = true,
)
@Composable
private fun PickleButtonV2PrimarySmallPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary,
            size = PickleButtonSize.Small
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Primary Medium",
    showBackground = true,
)
@Composable
private fun PickleButtonV2PrimaryMediumPreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary,
            size = PickleButtonSize.Medium
        )
    }
}

@Preview(
    name = "PickleButtonV2Preview - Primary Large",
    showBackground = true,
)
@Composable
private fun PickleButtonV2PrimaryLargePreview() {
    PickleTheme {
        PickleButtonV2(
            text = "Buttons",
            onClick = {},
            type = PickleButtonType.Primary,
            size = PickleButtonSize.Large
        )
    }
}
