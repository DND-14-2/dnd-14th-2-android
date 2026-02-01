package com.smtm.pickle.presentation.login

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
}
