package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleLogo(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(R.drawable.img_typo_logo),
        contentDescription = "Pickle 로고",
        modifier = modifier,
    )
}


@Preview(
    name = "Pickle Logo",
    showBackground = true,
)
@Composable
private fun PickleLogoPreview() {
    PickleTheme {
        PickleLogo()
    }
}
