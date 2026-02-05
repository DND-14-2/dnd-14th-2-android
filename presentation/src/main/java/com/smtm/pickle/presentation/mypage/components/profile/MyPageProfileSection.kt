package com.smtm.pickle.presentation.mypage.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.PickleBottomSheet
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.components.MyPageInviteBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageProfileSection(
    modifier: Modifier = Modifier,
    nickname: String,
    badgeName: String,
    invitationCode: String,
    onNicknameEditClick: () -> Unit,
    onMyJuryClick: () -> Unit,
    onMyBadgeClick: () -> Unit,
) {
    var showInviteSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        ProfileInfoRow(
            nickname = nickname,
            badge = badgeName,
            onNicknameEditClick = onNicknameEditClick,
            onInviteClick = { showInviteSheet = true }
        )
        Spacer(modifier = Modifier.height(20.dp))
        ProfileQuickMenuCard(
            onMyJuryClick = onMyJuryClick,
            onMyBadgeClick = onMyBadgeClick,
        )
    }

    if (showInviteSheet) {
        PickleBottomSheet(
            sheetState = sheetState,
            onDismiss = { showInviteSheet = false }
        ) {
            MyPageInviteBottomSheetContent(invitationCode = invitationCode)
        }
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
            onMyJuryClick = {},
            onMyBadgeClick = {},
            invitationCode = ""
        )
    }
}
