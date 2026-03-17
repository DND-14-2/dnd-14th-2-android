package com.smtm.pickle.presentation.verdict.jurorlist.materequest.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.profile.PickleProfile
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun MateRequestItem(
    nickname: String,
    invitationCode: String,
    onAcceptClick: () -> Unit,
    onRejectClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PickleProfile(type = ProfileType.NORMAL)
        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = nickname,
                style = PickleTheme.typography.body1Bold,
                color = PickleTheme.colors.gray800
            )
            Text(
                text = invitationCode,
                style = PickleTheme.typography.caption1Medium,
                color = PickleTheme.colors.gray600
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Row {
            ActionButton(
                text = stringResource(id = R.string.juror_list_accept),
                containerColor = PickleTheme.colors.gray100,
                contentColor = PickleTheme.colors.gray700,
                onClick = onAcceptClick,
            )
            Spacer(modifier = Modifier.width(8.dp))

            ActionButton(
                text = stringResource(id = R.string.juror_list_delete_confirm),
                containerColor = PickleTheme.semantic.guiltyBackground,
                contentColor = PickleTheme.semantic.guilty,
                onClick = onRejectClick,
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(45.dp, 32.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text,
            style = PickleTheme.typography.body4Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MateRequestItemPreview() {
    PickleTheme {
        MateRequestItem(
            nickname = "지인닉네임",
            invitationCode = "CODE23",
            onAcceptClick = {},
            onRejectClick = {}
        )
    }
}
