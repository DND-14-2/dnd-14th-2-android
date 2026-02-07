package com.smtm.pickle.presentation.ledger.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun LedgerCreateAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PickleTheme.colors.base0)
            .statusBarsPadding()
            .height(Dimensions.appbarHeight),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        PickleIconButton(
            modifier = Modifier.padding(start = 4.dp),
            painter = painterResource(R.drawable.ic_appbar_back),
            onClick = onNavigationClick,
            buttonSize = 48.dp,
            contentDescription = "back"
        )

        Text(text = title, style = PickleTheme.typography.head4SemiBold)
    }
}
