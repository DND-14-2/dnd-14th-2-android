package com.smtm.pickle.presentation.mypage.mybadge.model

data class BadgeUiState(
    val type: BadgeType,
    val isNew: Boolean = false,
    val isRead: Boolean = false,
    val isSelected: Boolean = false,
) {
    val showNewIcon: Boolean
        get() = isNew && !isRead
}
