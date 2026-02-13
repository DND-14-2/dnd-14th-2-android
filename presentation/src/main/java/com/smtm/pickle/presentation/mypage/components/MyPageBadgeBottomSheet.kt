package com.smtm.pickle.presentation.mypage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonV2
import com.smtm.pickle.presentation.designsystem.components.profile.PickleProfile
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun MyPageBadgeBottomSheetContent(
    badgeName: String,
    description: String,
    onBadgeChangeClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PickleProfile(type = ProfileType.InSetting)
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = badgeName,
            style = PickleTheme.typography.head3Bold,
            color = PickleTheme.colors.gray800,
        )
        Spacer(modifier = Modifier.height(20.dp))

        PickleCard(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            color = PickleTheme.colors.background50
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray700,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        PickleButtonV2(
            modifier = Modifier.fillMaxWidth(),
            text = "배지 변경하기",
            onClick = onBadgeChangeClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageBadgeBottomSheetContentPreview() {
    PickleTheme {
        MyPageBadgeBottomSheetContent(
            badgeName = "배지명",
            description = "배지 설명 2줄 배지 설명 2줄 배지 설명 2줄 배지 설명 2줄 배지 설명 2줄 배지 설명 2줄",
            onBadgeChangeClick = {}
        )
    }
}
