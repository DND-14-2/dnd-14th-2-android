package com.smtm.pickle.presentation.setting.model

sealed interface SettingTrailingType {
    data object Arrow : SettingTrailingType

    data class Switch(
        val isChecked: Boolean,
        val onCheckedChange: (Boolean) -> Unit
    ) : SettingTrailingType

    data class Text(val value: String) : SettingTrailingType
}
