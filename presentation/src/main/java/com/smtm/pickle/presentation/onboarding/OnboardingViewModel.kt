package com.smtm.pickle.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.onboarding.SetOnboardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingCompleteUseCase: SetOnboardingCompleteUseCase,
) : ViewModel() {

    private val _effect = MutableSharedFlow<OnboardingEffect>(replay = 0)
    val effect: SharedFlow<OnboardingEffect> = _effect.asSharedFlow()


    fun completeOnboarding() {
        viewModelScope.launch {
            runCatching {
                setOnboardingCompleteUseCase()
            }.onSuccess {
                _effect.emit(OnboardingEffect.NavigateToLogin)
            }
        }
    }

}

sealed interface OnboardingEffect {
    object NavigateToLogin : OnboardingEffect
}
