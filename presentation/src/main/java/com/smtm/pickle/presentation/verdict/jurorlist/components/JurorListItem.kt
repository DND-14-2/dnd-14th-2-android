package com.smtm.pickle.presentation.verdict.jurorlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.profile.PickleProfile
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun JurorListItem(
    nickname: String,
    togetherVerdictCount: Int,
    code: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PickleProfile(type = ProfileType.NORMAL)

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = nickname,
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.gray800,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.juror_list_together_verdict_count, togetherVerdictCount),
                    style = PickleTheme.typography.caption1Medium,
                    color = PickleTheme.colors.gray600,
                )

                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .clip(CircleShape)
                        .background(PickleTheme.colors.gray200)
                )
                Text(
                    text = code,
                    style = PickleTheme.typography.caption1Medium,
                    color = PickleTheme.colors.gray600,
                )
            }
        }
    }
}

@Preview
@Composable
fun JurorListItemPreview() {
    PickleTheme {
        JurorListItem(
            nickname = "juror_nickname",
            togetherVerdictCount = 3,
            code = "#1234",
            onClick = {}
        )
    }
}
