package com.smtm.pickle.presentation.verdict.jurorlist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextFieldWithSupporting
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.login.nickname.components.TrailingIcon

@Composable
fun JurorListInputInviteCodeDialog(
    value: String,
    inputState: InputState,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onActionDone: () -> Unit,
) {
    PickleDialog(
        title = stringResource(id = R.string.juror_list_input_invite_code_title),
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = stringResource(id = R.string.juror_list_confirm),
            cancelText = stringResource(id = R.string.dialog_cancel),
            onConfirmClick = onConfirm,
            onCancelClick = onDismiss,
        ),
        onDismiss = onDismiss,
        inputField = {
            PickleTextFieldWithSupporting(
                modifier = Modifier.fillMaxWidth(),
                inputState = inputState,
                value = value,
                onValueChange = onValueChange,
                hint = stringResource(id = R.string.juror_list_input_invite_code_hint),
                trailingIcon = {
                    when {
                        inputState is InputState.Error -> {
                            TrailingIcon(R.drawable.ic_snackbar_fail)
                        }

                        value.isNotEmpty() -> {
                            IconButton(
                                onClick = { onValueChange("") },
                                modifier = Modifier.size(24.dp)
                            ) {
                                TrailingIcon(R.drawable.ic_search_close)
                            }
                        }

                        else -> Unit
                    }
                },
                imeAction = ImeAction.Done,
                onImeAction = onActionDone
            )
        }
    )
}

@Preview
@Composable
private fun JurorListInputInviteCodeDialogPreview() {
    PickleTheme {
        JurorListInputInviteCodeDialog(
            value = "ABCDEF",
            inputState = InputState.Success(null),
            onValueChange = {},
            onConfirm = {},
            onDismiss = {},
            onActionDone = {},
        )
    }
}
