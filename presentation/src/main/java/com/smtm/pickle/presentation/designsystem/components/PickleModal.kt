package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonGroup
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonV2
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonLayout
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonSize
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleDialog(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(top = 40.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(Dimensions.radiusModal),
            color = PickleTheme.colors.base0
        ) {
            Column(
                modifier = Modifier.padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content,
            )
        }
    }
}

object PickleDialog {
    @Composable
    fun WithRowButton(
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(top = 40.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
        buttonSpace: Dp = 30.dp,
        confirmText: String,
        cancelText: String,
        onConfirmClick: () -> Unit,
        onCancelClick: () -> Unit,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        PickleDialog(
            modifier = modifier,
            contentPadding = contentPadding,
            onDismiss = onCancelClick,
        ) {
            content()

            Spacer(modifier = Modifier.height(buttonSpace))

            PickleButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                layout = PickleButtonLayout.RowEqual,
                buttonSize = PickleButtonSize.Large,
                leadingButton = { modifier, buttonSize ->
                    PickleButtonV2(
                        modifier = modifier,
                        text = cancelText,
                        onClick = onCancelClick,
                        type = PickleButtonType.Secondary,
                        size = buttonSize,
                    )
                },
                trailingButton = { modifier, buttonSize ->
                    PickleButtonV2(
                        modifier = modifier,
                        text = confirmText,
                        onClick = onConfirmClick,
                        type = PickleButtonType.Primary,
                        size = buttonSize,
                    )
                },
            )
        }
    }

    @Composable
    fun WithColumnButton(
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(top = 40.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
        buttonSpace: Dp = 30.dp,
        confirmText: String,
        cancelText: String,
        onConfirmClick: () -> Unit,
        onCancelClick: () -> Unit,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        PickleDialog(
            modifier = modifier,
            contentPadding = contentPadding,
            onDismiss = onCancelClick,
        ) {
            content()

            Spacer(modifier = Modifier.height(buttonSpace))

            PickleButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                layout = PickleButtonLayout.Column,
                buttonSize = PickleButtonSize.Large,
                leadingButton = { modifier, buttonSize ->
                    PickleButtonV2(
                        modifier = modifier,
                        text = confirmText,
                        onClick = onConfirmClick,
                        type = PickleButtonType.Primary,
                        size = buttonSize,
                    )
                },
                trailingButton = { modifier, buttonSize ->
                    PickleButtonV2(
                        modifier = modifier,
                        text = cancelText,
                        onClick = onCancelClick,
                        type = PickleButtonType.Ghost,
                        size = buttonSize,
                    )
                }
            )
        }
    }
}

/** @sample com.smtm.pickle.presentation.designsystem.components.PickleBottomSheetPreview */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickleBottomSheet(
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    gesturesEnabled: Boolean = true,
    hasDragHandle: Boolean = true,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        sheetGesturesEnabled = gesturesEnabled,
        containerColor = PickleTheme.colors.base0,
        shape = RoundedCornerShape(
            topStart = Dimensions.radiusModal,
            topEnd = Dimensions.radiusModal
        ),
        dragHandle = {
            if (hasDragHandle) {
                Surface(
                    modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                    color = PickleTheme.colors.gray200,
                    shape = RoundedCornerShape(Dimensions.radiusFull),
                ) {
                    Box(Modifier.size(width = 48.dp, height = 4.dp))
                }
            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 14.dp),
            content = content
        )
    }
}


@Preview(name = "PickleDialog - Single Button", showBackground = true)
@Composable
private fun PickleDialogSingleButtonPreview() {
    PickleTheme {
        PickleDialog(
            onDismiss = {}
        ) {
            Text(
                text = "단일 버튼 다이얼로그",
                style = PickleTheme.typography.body1Bold,
            )
            Spacer(modifier = Modifier.height(30.dp))
            PickleButtonV2(
                modifier = Modifier.fillMaxWidth(),
                text = "확인",
                onClick = {},
            )
        }
    }
}

@Preview(name = "PickleDialog - WithRowButton", showBackground = true)
@Composable
private fun PickleDialogWithRowButtonPreview() {
    PickleTheme {
        PickleDialog.WithRowButton(
            confirmText = "확인",
            cancelText = "취소",
            onConfirmClick = {},
            onCancelClick = {},
        ) {
            Text(
                text = "정말 로그아웃 하시겠어요?",
                style = PickleTheme.typography.body1Bold,
            )
        }
    }
}

@Preview(name = "PickleDialog - WithColumnButton", showBackground = true)
@Composable
private fun PickleDialogWithColumnButtonPreview() {
    PickleTheme {
        PickleDialog.WithColumnButton(
            confirmText = "시작하기",
            cancelText = "다음에 하기",
            onConfirmClick = {},
            onCancelClick = {},
        ) {
            Text(
                text = "프로필을 설정해볼까요?",
                style = PickleTheme.typography.body1Bold,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PickleBottomSheetPreview() {
    PickleTheme {
        PickleBottomSheet(
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { true },
            ),
            onDismiss = {},
            hasDragHandle = true
        ) {
            Text("즐겨찾는 내역")
            Spacer(modifier = Modifier.height(12.dp))
            PickleButtonV2(
                modifier = Modifier.fillMaxWidth(),
                text = "삭제하기",
                onClick = {},
            )
        }
    }
}
