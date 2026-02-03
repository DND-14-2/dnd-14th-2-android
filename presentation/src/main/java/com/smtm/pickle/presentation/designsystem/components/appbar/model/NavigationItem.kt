package com.smtm.pickle.presentation.designsystem.components.appbar.model

import androidx.compose.runtime.Stable

@Stable
sealed interface NavigationItem {

    data class Back(val onClick: () -> Unit) : NavigationItem

    data object PickleLogo : NavigationItem

    data object None : NavigationItem
}
