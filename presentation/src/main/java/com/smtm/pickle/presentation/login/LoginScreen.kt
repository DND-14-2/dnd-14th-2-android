package com.smtm.pickle.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.auth.KakaoLoginManager
import com.smtm.pickle.presentation.designsystem.components.PickleLogo
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarPosition
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.components.tooltip.PickleTooltip
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.login.components.ButtonSection
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator

@Composable
fun LoginScreen(
    navigator: AuthNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val kakaoLoginManager = remember(context) { KakaoLoginManager(context) }
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    LoginViewModel.LoginEffect.NavigateToNickname -> {
                        navigator.navigateToNickname()
                    }

                    LoginViewModel.LoginEffect.NavigateToMain -> {
                        navigator.navigateToMain()
                    }

                    is LoginViewModel.LoginEffect.ShowSnackbar -> {
                        snackbarState.show(
                            PickleSnackbar.toastError(
                                message = effect.message,
                                position = SnackbarPosition.BelowStatusBar
                            )
                        )
                    }
                }
            }
        }
    }

    LoginContent(
        uiState = uiState,
        onGoogleLogin = viewModel::loginWithGoogle,
        onKakaoLogin = {
            kakaoLoginManager.login(
                onSuccess = { token ->
                    viewModel.loginWithKakao(token)
                },
                onFailure = { message ->
                    viewModel.handleLoginError(message)
                }
            )
        }
    )

    SnackbarHost(snackbarState)
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onGoogleLogin: () -> Unit,
    onKakaoLogin: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PickleLogo(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-40).dp)
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PickleTooltip(
                message = stringResource(R.string.login_tooltip_fast),
                isVisible = true
            )
            Spacer(modifier = Modifier.height(15.dp))

            ButtonSection(
                uiState = uiState,
                onGoogleLogin = onGoogleLogin,
                onKakaoLogin = onKakaoLogin,
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
private fun LoginContentPreview() {
    PickleTheme {
        LoginContent(
            uiState = LoginUiState.Idle,
            onGoogleLogin = {},
            onKakaoLogin = {},
        )
    }
}
