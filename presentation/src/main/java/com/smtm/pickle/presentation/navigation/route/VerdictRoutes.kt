package com.smtm.pickle.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
data object VerdictCreateRoute

@Serializable
data object VerdictRequestRoute

@Serializable
data object VerdictResultRoute

@Serializable
data object JurorListRoute

@Serializable
data class JurorDetailRoute(val jurorId: Long)

@Serializable
data object MateRequestRoute
