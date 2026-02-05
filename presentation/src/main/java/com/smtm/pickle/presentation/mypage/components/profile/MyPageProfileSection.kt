package com.smtm.pickle.presentation.mypage.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageProfileSection(
    modifier: Modifier = Modifier,
    nickname: String,
    badgeName: String,
    onNicknameEditClick: () -> Unit,
    onMyBadgeClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        ProfileInfoRow(
            nickname = nickname,
            badge = badgeName,
            onNicknameEditClick = onNicknameEditClick,
        )
        Spacer(modifier = Modifier.height(20.dp))

        ProfileMyBadgeCard(onMyBadgeClick = onMyBadgeClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageProfileSectionPreview() {
    PickleTheme {
        MyPageProfileSection(
            nickname = "유저 닉네임",
            badgeName = "배지명",
            onNicknameEditClick = {},
            onMyBadgeClick = {},
        )
    }
}
