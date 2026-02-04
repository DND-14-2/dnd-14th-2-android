package com.smtm.pickle.presentation.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun HomeExpandableFab(
    isExpanded: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCreateClick: () -> Unit
) {
    if (isExpanded) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.End,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.home_fab_create_ledger),
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.base0,
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = onCreateClick,
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_fab_edit),
                        contentDescription = stringResource(R.string.home_fab_create_ledger),
                        modifier = Modifier.size(52.dp),
                        tint = Color.Unspecified,
                    )
                }
            }

            IconButton(
                onClick = onCloseClick,
                modifier = Modifier.size(52.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_fab_close),
                    contentDescription = "close",
                    modifier = Modifier.size(52.dp),
                    tint = Color.Unspecified,
                )
            }
        }
    } else {
        IconButton(
            onClick = onOpenClick,
            modifier = Modifier.size(52.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_fab_add),
                contentDescription = "open",
                modifier = Modifier.size(52.dp),
                tint = Color.Unspecified,
            )
        }
    }
}