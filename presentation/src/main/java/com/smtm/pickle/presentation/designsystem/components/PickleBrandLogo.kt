package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
        verticalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        PickleLogo(
            modifier = Modifier
                .size(width = 296.dp, height = 113.dp)
        )

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
