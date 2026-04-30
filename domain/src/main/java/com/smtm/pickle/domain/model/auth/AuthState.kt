package com.smtm.pickle.domain.model.auth

sealed interface AuthState {
    data object Authenticated : AuthState
    data object Unauthenticated : AuthState
    data object SessionExpired : AuthState
}
