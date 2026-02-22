package com.smtm.pickle.presentation.verdict.jurorlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun EmptyJurorContent(
    onInviteClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.illust_verdict_empty_juror),
            contentDescription = null
        )

        Text(
            text = stringResource(id = R.string.juror_list_empty),
            style = PickleTheme.typography.body3Regular,
            color = PickleTheme.colors.gray600,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Button(
            onClick = onInviteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.primary50,
                contentColor = PickleTheme.colors.primary500
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(Dimensions.buttonHeightMedium)
        ) {
            Text(
                text = stringResource(id = R.string.juror_list_invite_friend),
                style = PickleTheme.typography.body4Medium,
            )
        }
    }
}

@Preview
@Composable
fun EmptyJurorContentPreview() {
    PickleTheme {
        EmptyJurorContent(onInviteClick = {})
    }
}
