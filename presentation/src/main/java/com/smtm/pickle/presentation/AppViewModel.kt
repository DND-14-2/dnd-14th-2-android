package com.smtm.pickle.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.auth.AuthState
import com.smtm.pickle.domain.provider.TokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val tokenProvider: TokenProvider,
) : ViewModel() {

    private val _authNavEvent = Channel<AuthNavEvent>(Channel.CONFLATED)
    val authNavEvent = _authNavEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            tokenProvider.getAuthStateFlow().collect { state ->
                when (state) {
                    AuthState.SessionExpired -> _authNavEvent.trySend(AuthNavEvent.ToLoginWithMessage)
                    AuthState.Unauthenticated -> _authNavEvent.trySend(AuthNavEvent.ToLogin)
                    else -> {}
                }
            }
        }
    }
}

sealed interface AuthNavEvent {
    data object ToLogin : AuthNavEvent
    data object ToLoginWithMessage : AuthNavEvent
}
