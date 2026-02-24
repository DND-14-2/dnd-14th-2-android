package com.smtm.pickle.presentation.ui.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextFieldWithSupporting
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun InputInvitationCodeDialog(
    invitationCode: String,
    modifier: Modifier = Modifier,
    invitationCodeErrorMessage: String? = null,
    onInvitationCodeChange: (String) -> Unit,
    onCompleteClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    var inputErrorMessage by remember { mutableStateOf<String?>(null) }
    val displayError = inputErrorMessage ?: invitationCodeErrorMessage
    val lengthErrorMessage = stringResource(R.string.input_invitation_code_error_length)
    val uppercaseErrorMessage = stringResource(R.string.input_invitation_code_error_uppercase)

    PickleDialog(
        modifier = modifier,
        title = stringResource(R.string.input_invitation_code_dialog_title),
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = stringResource(R.string.common_complete),
            cancelText = stringResource(R.string.common_cancel),
            onConfirmClick = {
                val errorMessage = when {
                    invitationCode.length < 6 -> lengthErrorMessage
                    invitationCode.any { !it.isLetter() || !it.isUpperCase() } -> uppercaseErrorMessage
                    else -> null
                }

                if (errorMessage != null) {
                    inputErrorMessage = errorMessage
                } else {
                    inputErrorMessage = null
                    onCompleteClick(invitationCode)
                }
            },
            onCancelClick = onCancelClick,
        ),
        onDismiss = onDismiss,
        inputField = {
            InvitationCodeText(
                code = invitationCode,
                onCodeChange = { newCode ->
                    inputErrorMessage = null
                    onInvitationCodeChange(newCode)
                },
                errorMessage = displayError,
            )
        }
    )
}

@Composable
private fun InvitationCodeText(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    val state = when {
        errorMessage != null -> InputState.Error(message = errorMessage)
        code.isEmpty() -> InputState.Idle
        else -> InputState.Success(message = null)
    }

    val trailingIcon: @Composable (() -> Unit)? = when {
        state is InputState.Error && code.isNotEmpty() -> {
            {
                Icon(
                    painter = painterResource(R.drawable.ic_snackbar_fail),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        code.isNotEmpty() -> {
            {
                PickleIconButton(
                    painter = painterResource(R.drawable.ic_description_close),
                    onClick = { onCodeChange("") },
                    buttonSize = 24.dp,
                    iconSize = 20.dp,
                )
            }
        }

        else -> null
    }

    PickleTextFieldWithSupporting(
        inputState = state,
        value = code,
        onValueChange = { onCodeChange(it.take(6)) },
        modifier = modifier,
        hint = stringResource(R.string.input_invitation_code_dialog_hint),
        trailingIcon = trailingIcon,
    )
}

@Preview(
    name = "InputInvitationCodeDialog - Idle",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogIdlePreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "",
            onInvitationCodeChange = {},
            onCompleteClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Typing",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogTypingPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "ABC",
            onInvitationCodeChange = {},
            onCompleteClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Error (short)",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogErrorShortPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "AB",
            invitationCodeErrorMessage = "6자로 입력해주세요",
            onInvitationCodeChange = {},
            onCompleteClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Error (expired)",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogErrorExpiredPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "ABCDEF",
            invitationCodeErrorMessage = "만료된 초대코드입니다",
            onInvitationCodeChange = {},
            onCompleteClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Success",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogSuccessPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "ABCDEF",
            onInvitationCodeChange = {},
            onCompleteClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}
