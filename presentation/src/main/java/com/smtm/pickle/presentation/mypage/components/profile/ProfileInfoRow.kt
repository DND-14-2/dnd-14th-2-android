package com.smtm.pickle.presentation.mypage.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleBadge
import com.smtm.pickle.presentation.designsystem.components.button.PickleChip
import com.smtm.pickle.presentation.designsystem.components.profile.PickleProfile
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun ProfileInfoRow(
    nickname: String,
    badge: String,
    onNicknameEditClick: () -> Unit,
    onInviteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PickleProfile(type = ProfileType.Large)
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = nickname,
                    style = PickleTheme.typography.head3Bold,
                    color = PickleTheme.colors.gray800,
                )
                Spacer(modifier = Modifier.width(4.dp))

                PickleBadge(badge)

                IconButton(
                    onClick = onNicknameEditClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_mypage_arrow_right),
                        contentDescription = "닉네임 설정",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            PickleChip(
                text = "초대하기",
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_mypage_invitation),
                        contentDescription = "초대하기",
                        tint = Color.Unspecified,
                        modifier = Modifier.scale(1.2f)
                    )
                },
                onClick = onInviteClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileInfoRowPreview() {
    PickleTheme {
        ProfileInfoRow(
            nickname = "유저 닉네임",
            badge = "배지명",
            onNicknameEditClick = {},
            onInviteClick = {}
        )
    }
}
