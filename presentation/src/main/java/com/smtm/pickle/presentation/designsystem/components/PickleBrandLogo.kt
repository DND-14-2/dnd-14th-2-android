package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleBrandLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        PickleLogo()

        Text(
            text = stringResource(R.string.common_typo),
            style = PickleTheme.typography.head4SemiBold,
            color = PickleTheme.colors.primary400,
        )
    }
}

@Preview(
    name = "Pickle Brand Logo Preview",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleBrandLogoPreview() {
    PickleTheme {
        PickleBrandLogo()
    }
}
