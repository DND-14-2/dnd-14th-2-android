package com.smtm.pickle.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.usecase.auth.DemoLoginUseCase
import com.smtm.pickle.domain.usecase.auth.GoogleLoginUseCase
import com.smtm.pickle.domain.usecase.auth.KakaoLoginUseCase
import com.smtm.pickle.domain.usecase.user.GetFirstLoginUseCase
import com.smtm.pickle.domain.usecase.user.SetFirstLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val demoLoginUseCase: DemoLoginUseCase,
    private val getFirstLoginUseCase: GetFirstLoginUseCase,
    private val setFirstLoginUseCase: SetFirstLoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>(replay = 0)
    val effect: SharedFlow<LoginEffect> = _effect.asSharedFlow()


    fun loginWithGoogle() {
        handleLogin {
            googleLoginUseCase()
        }
    }

    fun loginWithKakao(token: String) {
        handleLogin {
            kakaoLoginUseCase(token = token)
        }
    }

    fun loginWithDemo() {
        handleLogin { demoLoginUseCase() }
    }

    fun handleLoginError(message: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Idle
            _effect.emit(LoginEffect.ShowSnackbar(message))
        }
    }

    private fun handleLogin(loginAction: suspend () -> Result<AuthToken>) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            loginAction()
                .onSuccess {
                    _uiState.value = LoginUiState.Idle
                    Timber.d("로그인 성공")

                    val isFirstLogin = getFirstLoginUseCase().getOrDefault(false)

                    if (isFirstLogin) {
                        setFirstLoginUseCase(false)
                            .onFailure { Timber.e(it, "setFirstLoginUseCase 실패") }
                    }

                    val destination = if (isFirstLogin) LoginEffect.NavigateToNickname
                    else LoginEffect.NavigateToMain

                    _effect.emit(destination)
                }.onFailure { error ->
                    _uiState.value = LoginUiState.Idle
                    _effect.emit(LoginEffect.ShowSnackbar("로그인에 실패했습니다. 잠시 후 다시 시도해주세요."))
                    Timber.e(error, "로그인 실패")
                }
        }
    }

    sealed interface LoginEffect {
        data object NavigateToMain : LoginEffect
        data object NavigateToNickname : LoginEffect

        data class ShowSnackbar(val message: String) : LoginEffect
    }
}
