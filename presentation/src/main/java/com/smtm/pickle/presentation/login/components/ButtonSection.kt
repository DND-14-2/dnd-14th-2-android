package com.smtm.pickle.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.login.LoginUiState

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onKakaoLogin: () -> Unit,
    onGoogleLogin: () -> Unit,
    onDemoLogin: () -> Unit = {},
) {
    Column(modifier = modifier) {
        Button(
            onClick = onKakaoLogin,
            enabled = uiState !is LoginUiState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.semantic.kakao,
                disabledContainerColor = PickleTheme.semantic.kakao
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_kakao),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.login_kakao_start),
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray800
            )
        }

        Spacer(modifier = Modifier.height(11.dp))

        OutlinedButton(
            onClick = onGoogleLogin,
            enabled = uiState !is LoginUiState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            border = ButtonDefaults.outlinedButtonBorder().copy(width = 1.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                disabledContainerColor = Color.White,
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.login_google_start),
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray700
            )
        }

        Spacer(modifier = Modifier.height(11.dp))

        PickleButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.login_demo_start),
            onClick = onDemoLogin,
            enabled = uiState !is LoginUiState.Loading,
        )
    }
}

@Preview
@Composable
private fun ButtonSectionPreview() {
    PickleTheme {
        ButtonSection(
            uiState = LoginUiState.Idle,
            onGoogleLogin = {},
            onKakaoLogin = {}
        )
    }
}
