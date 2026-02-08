package com.smtm.pickle.presentation.mypage.setting.model

data class SettingItem(
    val title: String,
    val trailingType: SettingTrailingType = SettingTrailingType.Arrow,
    val onClick: () -> Unit = {}
)
