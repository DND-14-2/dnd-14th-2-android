package com.smtm.pickle.presentation.mypage.mybadge.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.profile.PickleProfile
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileSizeType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.mybadge.model.BadgeType
import com.smtm.pickle.presentation.mypage.mybadge.model.BadgeUiState

private const val COLUMNS = 3

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyBadgeList(
    modifier: Modifier = Modifier,
    badges: List<BadgeUiState>,
    onBadgeClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.my_page_activity_badge),
            style = PickleTheme.typography.body1Bold,
            color = PickleTheme.colors.gray800
        )
        Spacer(modifier = Modifier.height(20.dp))


        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                maxItemsInEachRow = COLUMNS
            ) {
                badges.forEach { badge ->
                    BadgeItem(
                        modifier = Modifier.weight(1f),
                        state = badge,
                        onClick = { onBadgeClick(badge.type.id) }
                    )
                }

                val remainder = badges.size % COLUMNS
                if (remainder != 0) {
                    repeat(COLUMNS - remainder) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun BadgeItem(
    modifier: Modifier = Modifier,
    state: BadgeUiState,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PickleProfile(
            iconRes = state.type.iconRes,
            sizyType = ProfileSizeType.Large,
            selected = state.isSelected,
            isNewBadge = state.showNewIcon
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = state.type.badgeName,
            style = PickleTheme.typography.body4Medium,
            color = PickleTheme.colors.gray700
        )
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=800dp,dpi=440")
@Composable
private fun MyBadgeListPreview() {
    PickleTheme {
        MyBadgeList(
            badges = listOf(
                BadgeUiState(BadgeType.DEFAULT, isSelected = true),
                BadgeUiState(BadgeType.FIRST_JUDGMENT),
                BadgeUiState(BadgeType.JUDGMENT_MASTER),
                BadgeUiState(BadgeType.RICH_MAN),
                BadgeUiState(BadgeType.SAVING_KING),
            ),
            onBadgeClick = {}
        )
    }
}
