package com.smtm.pickle.presentation.mypage.profile.nicknamesetting

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonV2
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextFieldWithSupporting
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.login.nickname.components.CheckDuplicateButton
import com.smtm.pickle.presentation.login.nickname.components.TrailingIcon
import com.smtm.pickle.presentation.mypage.profile.components.NicknameSettingBaseContent

@Composable
fun NicknameSettingScreen(
    onBackClick: () -> Unit,
    viewModel: NicknameSettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                NicknameSettingEffect.NavigateToBack -> onBackClick()
            }
        }
    }

    NicknameSettingContent(
        uiState = uiState,
        onNicknameChange = viewModel::onNicknameChanged,
        onCheckDuplicate = viewModel::checkDuplicate,
        onSaveClick = viewModel::saveNickname,
        onBackClick = viewModel::onBackClick,
    )
}

@Composable
fun NicknameSettingContent(
    uiState: NicknameSettingUiState,
    onNicknameChange: (String) -> Unit,
    onCheckDuplicate: () -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NicknameSettingBaseContent(
        title = stringResource(R.string.nickname_edit_title),
        instruction = stringResource(R.string.nickname_change_instruction),
        onBackClick = onBackClick,
        modifier = modifier,
        bottomBar = {
            PickleButtonV2(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(bottom = 12.dp)
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.nickname_edit_button),
                onClick = onSaveClick,
                enabled = uiState.canSubmit,
            )
        }
    ) {
        PickleTextFieldWithSupporting(
            inputState = uiState.inputState,
            value = uiState.editingNickname,
            onValueChange = onNicknameChange,
            hint = stringResource(R.string.nickname_hint),
            defaultSupportingText = stringResource(R.string.nickname_helper),
            trailingIcon = {
                if (uiState.isNicknameModified) {
                    when {
                        uiState.isAvailable == true -> {
                            TrailingIcon(R.drawable.ic_textfield_success)
                        }

                        uiState.inputState is InputState.Error -> {
                            TrailingIcon(R.drawable.ic_snackbar_fail)
                        }

                        uiState.inputState is InputState.Success -> {
                            CheckDuplicateButton(
                                onClick = onCheckDuplicate,
                                enabled = !uiState.isCheckingDuplicate && uiState.isAvailable == null
                            )
                        }
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknameSettingPreview() {
    PickleTheme {
        NicknameSettingContent(
            uiState = NicknameSettingUiState(),
            onNicknameChange = {},
            onCheckDuplicate = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}
