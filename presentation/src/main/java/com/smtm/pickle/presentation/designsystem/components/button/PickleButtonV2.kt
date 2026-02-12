package com.smtm.pickle.presentation.designsystem.components.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonPairDistribution
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonStyle
import com.smtm.pickle.presentation.designsystem.components.button.model.toColors
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions.buttonCompactWidth

@Composable
fun PickleButtonV2(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: PickleButtonStyle = PickleButtonStyle.Primary,
) {
    val buttonColors = style.toColors()

    Button(
        modifier = modifier.height(Dimensions.buttonHeight),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors.containerColor,
            contentColor = buttonColors.contentColor,
            disabledContainerColor = buttonColors.disabledContainerColor,
            disabledContentColor = buttonColors.disabledContentColor,
        ),
        shape = RoundedCornerShape(Dimensions.radius)
    ) {
        Text(
            text = text,
            style = PickleTheme.typography.body1Bold,
        )
    }
}

@Composable
fun PickleButtonPairRow(
    positiveText: String,
    negativeText: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    modifier: Modifier = Modifier,
    distribution: PickleButtonPairDistribution = PickleButtonPairDistribution.Equal,
    positiveButtonStyle: PickleButtonStyle = PickleButtonStyle.Primary,
    negativeButtonStyle: PickleButtonStyle = PickleButtonStyle.Secondary,
    positiveEnabled: Boolean = true,
    negativeEnabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val negativeModifier = when (distribution) {
            PickleButtonPairDistribution.Equal -> Modifier.weight(1f)
            PickleButtonPairDistribution.Compact -> Modifier.width(buttonCompactWidth)
        }

        PickleButtonV2(
            modifier = negativeModifier,
            text = negativeText,
            onClick = onNegativeClick,
            style = negativeButtonStyle,
            enabled = negativeEnabled,
        )

        Spacer(modifier = Modifier.width(12.dp))

        PickleButtonV2(
            modifier = Modifier.weight(1f),
            text = positiveText,
            onClick = onPositiveClick,
            style = positiveButtonStyle,
            enabled = positiveEnabled,
        )
    }
}

@Composable
fun PickleButtonPairColumn(
    positiveText: String,
    negativeText: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    modifier: Modifier = Modifier,
    positiveButtonStyle: PickleButtonStyle = PickleButtonStyle.Primary,
    negativeButtonStyle: PickleButtonStyle = PickleButtonStyle.Ghost,
    positiveEnabled: Boolean = true,
    negativeEnabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PickleButtonV2(
            modifier = Modifier.fillMaxWidth(),
            text = positiveText,
            onClick = onPositiveClick,
            style = positiveButtonStyle,
            enabled = positiveEnabled,
        )

        Spacer(modifier = Modifier.height(4.dp))

        PickleButtonV2(
            modifier = Modifier.fillMaxWidth(),
            text = negativeText,
            onClick = onNegativeClick,
            style = negativeButtonStyle,
            enabled = negativeEnabled,
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
            style = PickleButtonStyle.Primary
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
            style = PickleButtonStyle.Secondary
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
            style = PickleButtonStyle.Tertiary
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
            style = PickleButtonStyle.Ghost
        )
    }
}

@Preview(
    name = "PickleButtonPairRowPreview - Equal",
    showBackground = true,
)
@Composable
private fun PickleButtonPairRowEqualPreview() {
    PickleTheme {
        PickleButtonPairRow(
            positiveText = "확인",
            negativeText = "취소",
            onPositiveClick = {},
            onNegativeClick = {},
            distribution = PickleButtonPairDistribution.Equal
        )
    }
}

@Preview(
    name = "PickleButtonPairRowPreview - Compact",
    showBackground = true,
)
@Composable
private fun PickleButtonPairRowCompactPreview() {
    PickleTheme {
        PickleButtonPairRow(
            positiveText = "확인",
            negativeText = "취소",
            onPositiveClick = {},
            onNegativeClick = {},
            distribution = PickleButtonPairDistribution.Compact
        )
    }
}

@Preview(
    name = "PickleButtonPairColumnPreview",
    showBackground = true,
)
@Composable
private fun PickleButtonPairColumnPreview() {
    PickleTheme {
        PickleButtonPairColumn(
            positiveText = "확인",
            negativeText = "취소",
            onPositiveClick = {},
            onNegativeClick = {},
        )
    }
}
