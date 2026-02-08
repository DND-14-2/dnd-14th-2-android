package com.smtm.pickle.presentation.mypage.setting.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.mypage.setting.model.SettingItem
import com.smtm.pickle.presentation.mypage.setting.model.SettingTrailingType

@Composable
fun SettingRow(item: SettingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.buttonHeightSmall)
            .clickable(onClick = item.onClick)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            style = PickleTheme.typography.body3Regular,
            color = PickleTheme.colors.gray800,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        when (val trailing = item.trailingType) {
            is SettingTrailingType.Arrow -> {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_right_side),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            is SettingTrailingType.Switch -> {
                Switch(
                    checked = trailing.isChecked,
                    onCheckedChange = trailing.onCheckedChange,
                )
            }

            is SettingTrailingType.Text -> {
                Text(
                    text = trailing.value,
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.gray600,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingRowPreview() {
    PickleTheme {
        Column {
            SettingRow(
                item = SettingItem(
                    title = "설정 설정",
                    trailingType = SettingTrailingType.Arrow
                )
            )
            SettingRow(
                item = SettingItem(
                    title = "설정 설정",
                    trailingType = SettingTrailingType.Switch(
                        isChecked = false,
                        onCheckedChange = {}
                    )
                )
            )
            SettingRow(
                item = SettingItem(
                    title = "설정 설정",
                    trailingType = SettingTrailingType.Switch(
                        isChecked = true,
                        onCheckedChange = {}
                    )
                )
            )
            SettingRow(
                item = SettingItem(
                    title = "설정 설정",
                    trailingType = SettingTrailingType.Text(value = "0.0.0")
                )
            )
        }
    }
}
