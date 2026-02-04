package com.smtm.pickle.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleBottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onNavigate: (Any) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PickleTheme.colors.base0)
            .navigationBarsPadding()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem.entries.forEach { item ->
            // 현재 destination이 이 탭의 Graph에 속하는지 확인
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.tabRouteClass)
            } == true

            PickleNavigationBarItem(
                isSelected = isSelected,
                onClick = { onNavigate(item.tabRoute) },
                bottomNavItem = item,
            )
        }
    }
}

@Composable
private fun RowScope.PickleNavigationBarItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    bottomNavItem: BottomNavItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = !isSelected,
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = if (isSelected) bottomNavItem.activatedIconResId else bottomNavItem.inactivatedIconResId
            ),
            contentDescription = stringResource(bottomNavItem.labelResId),
            modifier = Modifier.size(24.dp),
        )

        Text(
            text = stringResource(bottomNavItem.labelResId),
            style = PickleTheme.typography.caption1Medium,
            color = if (isSelected) PickleTheme.colors.primary500 else PickleTheme.colors.gray500,
        )
    }
}
