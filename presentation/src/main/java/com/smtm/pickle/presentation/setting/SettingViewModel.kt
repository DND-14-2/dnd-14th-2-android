package com.smtm.pickle.presentation.setting

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.auth.LogoutUseCase
import com.smtm.pickle.domain.usecase.auth.WithdrawAccountUseCase
import com.smtm.pickle.domain.usecase.service.GetPickleVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawAccountUseCase: WithdrawAccountUseCase,
    private val getPickleVersionUseCase: GetPickleVersionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SettingEffect>(replay = 0)
    val effect: SharedFlow<SettingEffect> = _effect.asSharedFlow()


    init {
        _uiState.update { it.copy(version = getPickleVersionUseCase()) }
    }


    fun onLogoutClick() {
        _uiState.update {
            it.copy(dialogState = SettingDialogState.Logout)
        }
    }

    fun onWithdrawClick() {
        _uiState.update {
            it.copy(dialogState = SettingDialogState.Withdraw)
        }
    }

    fun dismissDialog() {
        _uiState.update {
            it.copy(dialogState = SettingDialogState.None)
        }
    }

    fun confirmLogout() {
        viewModelScope.launch {
            logoutUseCase()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            dialogState = SettingDialogState.None
                        )
                    }
                    _effect.emit(SettingEffect.NavigateToLogin)
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            dialogState = SettingDialogState.None
                        )
                    }
                    _effect.emit(SettingEffect.ShowSnackBar("로그아웃에 실패했습니다."))
                }
        }
    }

    fun confirmWithdraw() {
        viewModelScope.launch {
            withdrawAccountUseCase()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            dialogState = SettingDialogState.None
                        )
                    }
                    _effect.emit(SettingEffect.NavigateToLogin)
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            dialogState = SettingDialogState.None
                        )
                    }
                    _effect.emit(SettingEffect.ShowSnackBar("회원탈퇴에 실패했습니다."))
                }
        }
    }

    fun navigateToPrivacyPolicy() {
        viewModelScope.launch {
            _effect.emit(SettingEffect.NavigateToPrivacyPolicy)
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _effect.emit(SettingEffect.NavigateBack)
        }
    }

    fun onVersionClick() {
        viewModelScope.launch {
            _effect.emit(SettingEffect.OpenGooglePlay)
        }
    }

    fun openGooglePlay(context: Context) {
        val packageName = context.packageName

        // Google Play Store Uri
        val storeUri = "market://details?id=$packageName".toUri()
        val storeIntent = Intent(Intent.ACTION_VIEW, storeUri)

        // 웹 브라우저 Uri
        val webUri = "https://play.google.com/store/apps/details?id=$packageName".toUri()
        val webIntent = Intent(Intent.ACTION_VIEW, webUri)

        try {
            context.startActivity(storeIntent)
        } catch (_: Exception) {
            context.startActivity(webIntent)
        }
    }
}

data class SettingUiState(
    val version: String = "0.0.0",
    val isLoading: Boolean = false,
    val dialogState: SettingDialogState = SettingDialogState.None
)

sealed interface SettingEffect {
    data object NavigateToPrivacyPolicy : SettingEffect
    data object NavigateToLogin : SettingEffect
    data object NavigateBack : SettingEffect
    data object OpenGooglePlay : SettingEffect
    data class ShowSnackBar(val msg: String) : SettingEffect
}

sealed interface SettingDialogState {
    data object None : SettingDialogState
    data object Logout : SettingDialogState
    data object Withdraw : SettingDialogState
}
