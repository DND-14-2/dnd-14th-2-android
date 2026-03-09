package com.smtm.pickle.presentation.designsystem.components.appbar.model

import androidx.annotation.DrawableRes

sealed interface AppBarAction {
    data class Icon(
        @DrawableRes val icon: Int,
        val contentDescription: String,
        val onClick: () -> Unit,
    ) : AppBarAction

    data class Text(
        val label: String,
        val onClick: () -> Unit,
    ) : AppBarAction
}
