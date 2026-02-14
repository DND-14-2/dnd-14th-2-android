package com.smtm.pickle.presentation.designsystem.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonGroupLayout
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonSize
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonType
import com.smtm.pickle.presentation.designsystem.components.button.model.groupSpacing
import com.smtm.pickle.presentation.designsystem.components.button.model.leadingFixedWidth
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleButtonGroup(
    modifier: Modifier = Modifier,
    layout: PickleButtonGroupLayout,
    buttonSize: PickleButtonSize = PickleButtonSize.Large,
    leadingButton: @Composable (Modifier, PickleButtonSize) -> Unit,
    trailingButton: @Composable (Modifier, PickleButtonSize) -> Unit,
) {
    when (layout) {
        is PickleButtonGroupLayout.RowFixedLeading -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(buttonSize.groupSpacing),
            ) {
                leadingButton(
                    Modifier.width(buttonSize.leadingFixedWidth),
                    buttonSize
                )
                trailingButton(
                    Modifier.weight(1f),
                    buttonSize
                )
            }
        }

        is PickleButtonGroupLayout.RowEqual -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(buttonSize.groupSpacing),
            ) {
                leadingButton(
                    Modifier.weight(1f),
                    buttonSize
                )
                trailingButton(
                    Modifier.weight(1f),
                    buttonSize
                )
            }
        }

        is PickleButtonGroupLayout.Column -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(buttonSize.groupSpacing),
            ) {
                leadingButton(
                    Modifier.fillMaxWidth(),
                    buttonSize
                )
                trailingButton(
                    Modifier.fillMaxWidth(),
                    buttonSize
                )
            }
        }
    }
}

@Preview(
    name = "PickleButtonGroup - RowFixedLeading Large",
    showBackground = true,
)
@Composable
private fun PickleButtonGroupRowFixedLeadingLargePreview() {
    PickleTheme {
        PickleButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            layout = PickleButtonGroupLayout.RowFixedLeading,
            buttonSize = PickleButtonSize.Large,
            leadingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "취소",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Secondary,
                    size = buttonSize,
                )
            },
            trailingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "확인",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Primary,
                    size = buttonSize,
                )
            },
        )
    }
}

@Preview(
    name = "PickleButtonGroup - RowFixedLeading Small",
    showBackground = true,
)
@Composable
private fun PickleButtonGroupRowFixedLeadingSmallPreview() {
    PickleTheme {
        PickleButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            layout = PickleButtonGroupLayout.RowFixedLeading,
            buttonSize = PickleButtonSize.Small,
            leadingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "취소",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Secondary,
                    size = buttonSize,
                )
            },
            trailingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "확인",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Primary,
                    size = buttonSize,
                )
            },
        )
    }
}

@Preview(
    name = "PickleButtonGroup - RowEqual",
    showBackground = true,
)
@Composable
private fun PickleButtonGroupRowEqualPreview() {
    PickleTheme {
        PickleButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            layout = PickleButtonGroupLayout.RowEqual,
            buttonSize = PickleButtonSize.Large,
            leadingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "아니오",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Secondary,
                    size = buttonSize,
                )
            },
            trailingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "네",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Primary,
                    size = buttonSize,
                )
            },
        )
    }
}

@Preview(
    name = "PickleButtonGroup - Column",
    showBackground = true,
)
@Composable
private fun PickleButtonGroupColumnPreview() {
    PickleTheme {
        PickleButtonGroup(
            layout = PickleButtonGroupLayout.Column,
            buttonSize = PickleButtonSize.Large,
            leadingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "시작하기",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Primary,
                    size = buttonSize,
                )
            },
            trailingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    text = "다음에 하기",
                    onClick = {},
                    modifier = modifier,
                    type = PickleButtonType.Ghost,
                    size = buttonSize,
                )
            },
        )
    }
}
