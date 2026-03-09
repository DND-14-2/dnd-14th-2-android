package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleAppBarBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentDescription: String = "뒤로가기",
) {
    PickleIconButton(
        painter = painterResource(R.drawable.ic_app_bar_back),
        onClick = onClick,
        modifier = modifier,
        contentDescription = contentDescription,
    )
}

@Preview(
    name = "PickleAppBarBackButton",
    showBackground = true,
)
@Composable
private fun PickleAppBarBackButtonPreview() {
    PickleTheme {
        PickleAppBarBackButton(onClick = {})
    }
}
