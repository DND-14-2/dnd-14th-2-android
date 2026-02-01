package com.smtm.pickle.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.usecase.auth.InitTokenUseCase
import com.smtm.pickle.domain.usecase.auth.IsLoggedInUseCase
import com.smtm.pickle.domain.usecase.onboarding.GetOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val initTokenUseCase: InitTokenUseCase,
) : ViewModel() {
    private val _effect = MutableSharedFlow<SplashEffect>(replay = 0)
    val effect: SharedFlow<SplashEffect> = _effect.asSharedFlow()


    fun initialize() {
        viewModelScope.launch {
            runCatching { initTokenUseCase() }
                .onFailure { e ->
                    if (e is CancellationException) throw e
                    Timber.e(e)
                }
            checkInitialDestination()
        }
    }

    private suspend fun checkInitialDestination() {
        coroutineScope {
            val isOnboardingCompletedDeferred = async {
                runCatching { getOnboardingStatusUseCase().first() }
                    .getOrElse { e ->
                        if (e is CancellationException) throw e
                        false
                    }
            }

            val isLoggedInDeferred = async {
                runCatching { isLoggedInUseCase().first() }
                    .getOrElse { e ->
                        if (e is CancellationException) throw e
                        false
                    }
            }

            delay(1500L)

            val navEffect = when {
                !isOnboardingCompletedDeferred.await() -> SplashEffect.NavigateToOnboarding

                !isLoggedInDeferred.await() -> SplashEffect.NavigateToLogin

                else -> SplashEffect.NavigateToMain
            }

            _effect.emit(navEffect)
        }
    }

    sealed interface SplashEffect {
        data object NavigateToOnboarding : SplashEffect
        data object NavigateToLogin : SplashEffect
        data object NavigateToMain : SplashEffect
    }
}
