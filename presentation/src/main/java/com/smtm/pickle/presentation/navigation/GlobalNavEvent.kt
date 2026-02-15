package com.smtm.pickle.presentation.navigation

sealed interface GlobalNavEvent {

    data object Logout : GlobalNavEvent
    data class SessionExpired(val message: String) : GlobalNavEvent
}