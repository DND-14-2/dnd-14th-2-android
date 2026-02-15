package com.smtm.pickle.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.designsystem.components.PickleBrandLogo
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator

@Composable
fun SplashScreen(
    navigator: AuthNavigator,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { event ->
            when (event) {
                SplashViewModel.SplashEffect.NavigateToOnboarding -> navigator.navigateToOnboarding()
                SplashViewModel.SplashEffect.NavigateToLogin -> navigator.navigateToLogin()
                SplashViewModel.SplashEffect.NavigateToMain -> navigator.navigateToMain()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    SplashContent()
}

@Composable
private fun SplashContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PickleTheme.colors.base0),
    ) {
        PickleBrandLogo(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-40).dp)
        )
    }
}

@Preview(
    name = "Splash Content Preview",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun SplashContentPreview() {
    PickleTheme {
        SplashContent()
    }
}
