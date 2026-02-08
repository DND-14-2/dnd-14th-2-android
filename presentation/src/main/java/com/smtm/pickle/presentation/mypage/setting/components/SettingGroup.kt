package com.smtm.pickle.presentation.mypage.setting.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.mypage.setting.model.SettingItem
import com.smtm.pickle.presentation.mypage.setting.model.SettingSection

@Composable
fun SettingGroup(section: SettingSection) {
    Column {
        Text(
            text = section.title,
            style = PickleTheme.typography.body1Bold,
            color = PickleTheme.colors.gray700,
            modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
        )

        PickleCard(
            conerRadius = Dimensions.radius,
            contentPadding = PaddingValues(0.dp)
        ) {
            Column {
                section.items.forEach { item ->
                    SettingRow(item = item)
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingGroupPreview() {
    PickleTheme {
        SettingGroup(
            section = SettingSection(
                title = "계정 설정",
                items = listOf(
                    SettingItem(title = "로그아웃"),
                    SettingItem(title = "계정탈퇴")
                )
            )
        )
    }
}
