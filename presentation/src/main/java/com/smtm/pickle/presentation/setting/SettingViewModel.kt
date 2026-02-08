package com.smtm.pickle.presentation.setting

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.auth.LogoutUseCase
import com.smtm.pickle.domain.usecase.auth.WithdrawAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawAccountUseCase: WithdrawAccountUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<SettingEffect>(replay = 0)
    val effect: SharedFlow<SettingEffect> = _effect.asSharedFlow()

    fun onLogoutClick() {
        viewModelScope.launch {
            // TODO 로그아웃 확인 다이얼로그

            logoutUseCase()
            _effect.emit(SettingEffect.NavigateToLogin)
        }
    }

    fun onWithdrawClick() {
        viewModelScope.launch {
            // TODO 탈퇴 확인 다이얼로그

            withdrawAccountUseCase()
            _effect.emit(SettingEffect.NavigateToLogin)
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

    fun getPickleVersion(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "0.0.0"
        } catch (e: Exception) {
            Timber.e(e, "버전 정보 가져오기 실패")
            "0.0.0"
        }
    }
}

sealed interface SettingEffect {
    data object NavigateToPrivacyPolicy : SettingEffect
    data object NavigateToLogin : SettingEffect
    data object NavigateBack : SettingEffect
}
