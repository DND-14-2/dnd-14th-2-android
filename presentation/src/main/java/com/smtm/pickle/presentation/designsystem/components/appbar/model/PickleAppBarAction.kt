package com.smtm.pickle.presentation.designsystem.components.appbar.model

import androidx.annotation.DrawableRes

sealed interface PickleAppBarAction {
    data class Icon(
        @DrawableRes val icon: Int,
        val onClick: () -> Unit,
        val contentDescription: String? = null,
    ) : PickleAppBarAction

    data class Text(
        val label: String,
        val onClick: () -> Unit,
    ) : PickleAppBarAction
}
