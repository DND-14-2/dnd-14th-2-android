package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleLogo(
    modifier: Modifier = Modifier,
    width: Dp = 280.dp,
    height: Dp = 130.dp
) {
    Image(
        painter = painterResource(R.drawable.img_typo_logo),
        contentDescription = "Pickle 로고",
        modifier = modifier.size(width = width, height = height),
    )
}


@Preview(
    name = "Pickle Logo",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleLogoPreview() {
    PickleTheme {
        PickleLogo()
    }
}
