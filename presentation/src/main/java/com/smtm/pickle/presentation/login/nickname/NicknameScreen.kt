package com.smtm.pickle.presentation.login.nickname

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.extension.clearFocusOnBackgroundTab
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextFieldWithSupporting
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.login.nickname.components.CheckDuplicateButton
import com.smtm.pickle.presentation.login.nickname.components.TrailingIcon
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator

@Composable
fun NicknameScreen(
    navigator: AuthNavigator,
    viewModel: NicknameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { event ->
            when (event) {
                NicknameViewModel.NicknameEffect.NavigateToMain -> {
                    navigator.navigateToMain()
                }
            }
        }
    }

    BackHandler {
        viewModel.onBackClick()
    }

    NicknameContent(
        uiState = uiState,
        onNicknameChanged = viewModel::onNicknameChanged,
        onCheckDuplicate = viewModel::checkDuplicate,
        onSaveNickname = viewModel::saveNickname,
        onBackClick = viewModel::onBackClick,
    )
}

@Composable
fun NicknameContent(
    uiState: NicknameUiState,
    modifier: Modifier = Modifier,
    onNicknameChanged: (String) -> Unit = {},
    onCheckDuplicate: () -> Unit = {},
    onSaveNickname: () -> Unit = {},
    onBackClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.clearFocusOnBackgroundTab(focusManager),
        topBar = {
            PickleAppBar(
                title = "닉네임 입력",
                navigationItem = NavigationItem.Back(onBackClick),
            )
        },
        bottomBar = {
            PickleButton(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(bottom = 14.dp)
                    .padding(horizontal = 16.dp),
                text = "다음",
                onClick = onSaveNickname,
                enabled = uiState.canSubmit,
                textColor = PickleTheme.colors.base0
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "닉네임을 설정해주세요",
                style = PickleTheme.typography.body1Bold,
                color = PickleTheme.colors.gray800
            )

            Spacer(modifier = Modifier.height(16.dp))
            PickleTextFieldWithSupporting(
                inputState = uiState.inputState,
                value = uiState.nickname,
                onValueChange = { newNickname ->
                    onNicknameChanged(newNickname)
                },
                hint = "최대 5자까지 입력 가능해요",
                defaultSupportingText = "특수 문자 및 영어 대문자는 사용할 수 없어요.",
                trailingIcon = {
                    when {
                        uiState.isAvailable == true -> {
                            TrailingIcon(R.drawable.ic_textfield_success)
                        }

                        uiState.inputState is InputState.Error -> {
                            TrailingIcon(R.drawable.ic_snackbar_fail)
                        }

                        uiState.inputState is InputState.Success -> {
                            CheckDuplicateButton(
                                modifier = Modifier.offset(x = (-12).dp),
                                onClick = onCheckDuplicate,
                                enabled = !uiState.isCheckingDuplicate && uiState.isAvailable == null
                            )
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknamePreview() {
    PickleTheme {
        NicknameContent(
            uiState = NicknameUiState(
                nickname = "name",
                inputState = InputState.Success("사용 가능한 닉네임이에요!"),
            ),
            onNicknameChanged = {},
            onCheckDuplicate = {},
            onSaveNickname = {},
            onBackClick = {}
        )
    }
}
