package com.smtm.pickle.presentation.setting.model

data class SettingItem(
    val title: String,
    val trailingType: SettingTrailingType = SettingTrailingType.Arrow,
    val onClick: () -> Unit = {}
)
