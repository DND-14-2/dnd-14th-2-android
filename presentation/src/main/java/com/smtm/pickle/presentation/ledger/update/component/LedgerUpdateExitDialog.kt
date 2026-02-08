package com.smtm.pickle.presentation.ledger.update.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleDialog
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun LedgerUpdateExitDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onExitButtonClick: () -> Unit,
) {
    PickleDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 20.dp),
    ) {
        Text(
            text = stringResource(R.string.ledger_update_exit_dialog_title),
            style = PickleTheme.typography.head3Bold,
            color = PickleTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.ledger_update_exit_dialog_description),
            style = PickleTheme.typography.body2Medium,
            color = PickleTheme.colors.gray600
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(Dimensions.buttonHeight),
                shape = RoundedCornerShape(Dimensions.radius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PickleTheme.colors.gray100,
                    contentColor = PickleTheme.colors.gray600
                ),
                onClick = onExitButtonClick
            ) {
                Text(
                    text = stringResource(R.string.ledger_update_exit_dialog_negative),
                    style = PickleTheme.typography.body1Bold,
                )
            }

            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(Dimensions.buttonHeight),
                shape = RoundedCornerShape(Dimensions.radius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PickleTheme.colors.primary400,
                    contentColor = PickleTheme.colors.base0
                ),
                onClick = onDismiss,
            ) {
                Text(
                    text = stringResource(R.string.ledger_update_exit_dialog_positive),
                    style = PickleTheme.typography.body1Bold,
                )
            }
        }
    }
}

@Preview
@Composable
private fun LedgerUpdateExitDialogPreview() {
    PickleTheme {
        LedgerUpdateExitDialog(
            onDismiss = {},
            onExitButtonClick = {}
        )
    }
}
