package com.smtm.pickle.presentation.designsystem.components.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonGroup
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonV2
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonGroupLayout
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonSize
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonType
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleDialogV2(
    title: String,
    subtitle: String,
    buttonLayout: PickleDialogButtonLayout,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int? = null,
    inputField: (@Composable () -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(Dimensions.radiusModal),
            color = PickleTheme.colors.base0,
        ) {
            Column(
                modifier = Modifier.padding(
                    top = Dimensions.dialogPaddingTop,
                    start = Dimensions.dialogPaddingContent,
                    end = Dimensions.dialogPaddingContent,
                    bottom = Dimensions.dialogPaddingContent,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (imageRes != null) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.dialogImageSize),
                    )
                    Spacer(modifier = Modifier.height(Dimensions.dialogImageTitleSpacing))
                }

                Text(
                    text = title,
                    style = PickleTheme.typography.head3Bold,
                    color = PickleTheme.colors.gray800,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(Dimensions.dialogTitleSubtitleSpacing))

                Text(
                    text = subtitle,
                    style = PickleTheme.typography.body2Medium,
                    color = PickleTheme.colors.gray600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (inputField != null) {
                    Spacer(modifier = Modifier.height(Dimensions.dialogSubtitleInputSpacing))
                    inputField()
                }

                Spacer(modifier = Modifier.height(Dimensions.dialogContentButtonSpacing))

                DialogButtons(
                    buttonLayout = buttonLayout,
                    modifier = Modifier.fillMaxWidth(),
                )

            }
        }
    }
}

@Composable
private fun DialogButtons(
    buttonLayout: PickleDialogButtonLayout,
    modifier: Modifier = Modifier,
) {
    when (buttonLayout) {
        is PickleDialogButtonLayout.Single -> {
            PickleButtonV2(
                text = buttonLayout.text,
                onClick = buttonLayout.onClick,
                modifier = modifier,
                type = PickleButtonType.Primary,
                size = PickleButtonSize.Large,
            )
        }

        is PickleDialogButtonLayout.Horizontal -> {
            PickleButtonGroup(
                modifier = modifier,
                layout = PickleButtonGroupLayout.RowEqual,
                buttonSize = PickleButtonSize.Large,
                leadingButton = { buttonModifier, buttonSize ->
                    PickleButtonV2(
                        text = buttonLayout.cancelText,
                        onClick = buttonLayout.onCancelClick,
                        modifier = buttonModifier,
                        type = PickleButtonType.Secondary,
                        size = buttonSize,
                    )
                },
                trailingButton = { buttonModifier, buttonSize ->
                    PickleButtonV2(
                        text = buttonLayout.confirmText,
                        onClick = buttonLayout.onConfirmClick,
                        modifier = buttonModifier,
                        type = PickleButtonType.Primary,
                        size = buttonSize,
                    )
                },
            )
        }

        is PickleDialogButtonLayout.Vertical -> {
            PickleButtonGroup(
                modifier = modifier,
                layout = PickleButtonGroupLayout.Column,
                buttonSize = PickleButtonSize.Large,
                leadingButton = { buttonModifier, buttonSize ->
                    PickleButtonV2(
                        text = buttonLayout.primaryText,
                        onClick = buttonLayout.onPrimaryClick,
                        modifier = buttonModifier,
                        type = PickleButtonType.Primary,
                        size = buttonSize,
                    )
                },
                trailingButton = { buttonModifier, buttonSize ->
                    PickleButtonV2(
                        text = buttonLayout.ghostText,
                        onClick = buttonLayout.onGhostClick,
                        modifier = buttonModifier,
                        type = PickleButtonType.Ghost,
                        size = buttonSize,
                    )
                },
            )

            if (buttonLayout.action != null) {
                Text(
                    text = buttonLayout.action.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { buttonLayout.action.onClick() }
                        .padding(vertical = Dimensions.dialogButtonActionTextSpacing),
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.primary500,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                )
            }
        }
    }
}

@Preview(name = "Type 1 - Single Button")
@Composable
private fun PickleDialogV2SinglePreview() {
    PickleTheme {
        PickleDialogV2(
            title = "타이틀",
            subtitle = "서브타이틀 텍스트가 들어갑니다",
            buttonLayout = PickleDialogButtonLayout.Single(
                text = "확인",
                onClick = {},
            ),
            onDismiss = {},
        )
    }
}

@Preview(name = "Type 2 - Horizontal Buttons")
@Composable
private fun PickleDialogV2HorizontalPreview() {
    PickleTheme {
        PickleDialogV2(
            title = "타이틀",
            subtitle = "서브타이틀 텍스트가 들어갑니다",
            buttonLayout = PickleDialogButtonLayout.Horizontal(
                confirmText = "확인",
                cancelText = "취소",
                onConfirmClick = {},
                onCancelClick = {},
            ),
            onDismiss = {},
        )
    }
}

@Preview(name = "Type 3 - Vertical Buttons")
@Composable
private fun PickleDialogV2VerticalPreview() {
    PickleTheme {
        PickleDialogV2(
            title = "타이틀",
            subtitle = "서브타이틀 텍스트가 들어갑니다",
            buttonLayout = PickleDialogButtonLayout.Vertical(
                primaryText = "시작하기",
                ghostText = "다음에 하기",
                onPrimaryClick = {},
                onGhostClick = {},
            ),
            onDismiss = {},
        )
    }
}

@Preview(name = "Type 4 - With Image")
@Composable
private fun PickleDialogV2WithImagePreview() {
    PickleTheme {
        PickleDialogV2(
            title = "타이틀",
            subtitle = "서브타이틀 텍스트가 들어갑니다",
            buttonLayout = PickleDialogButtonLayout.Single(
                text = "확인",
                onClick = {},
            ),
            onDismiss = {},
            imageRes = R.drawable.illust_profile_default,
        )
    }
}

@Preview(name = "Type 5 - With Input Field")
@Composable
private fun PickleDialogV2WithInputFieldPreview() {
    PickleTheme {
        PickleDialogV2(
            title = "타이틀",
            subtitle = "서브타이틀 텍스트가 들어갑니다",
            buttonLayout = PickleDialogButtonLayout.Horizontal(
                confirmText = "확인",
                cancelText = "취소",
                onConfirmClick = {},
                onCancelClick = {},
            ),
            onDismiss = {},
            inputField = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimensions.inputHeight),
                    color = PickleTheme.colors.gray100,
                    shape = RoundedCornerShape(Dimensions.radius),
                ) {}
            },
        )
    }
}

@Preview(name = "Type 6 - Vertical Buttons with HyperText")
@Composable
private fun PickleDialogV2VerticalWithHyperTextPreview() {
    PickleTheme {
        PickleDialogV2(
            title = "타이틀",
            subtitle = "서브타이틀 텍스트가 들어갑니다",
            buttonLayout = PickleDialogButtonLayout.Vertical(
                primaryText = "시작하기",
                ghostText = "다음에 하기",
                onPrimaryClick = {},
                onGhostClick = {},
                action = PickleDialogButtonLayout.Action(
                    text = "하이퍼 텍스트",
                    onClick = {},
                ),
            ),
            onDismiss = {},
        )
    }
}
