package com.smtm.pickle.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smtm.pickle.presentation.login.LoginScreen
import com.smtm.pickle.presentation.login.nickname.NicknameScreen
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator
import com.smtm.pickle.presentation.navigation.route.AuthGraphRoute
import com.smtm.pickle.presentation.navigation.route.LoginRoute
import com.smtm.pickle.presentation.navigation.route.NicknameRoute
import com.smtm.pickle.presentation.navigation.route.OnboardingRoute
import com.smtm.pickle.presentation.navigation.route.SplashRoute
import com.smtm.pickle.presentation.onboarding.OnboardingScreen
import com.smtm.pickle.presentation.splash.SplashScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onNavigateToMain: () -> Unit,
) {
    navigation<AuthGraphRoute>(startDestination = SplashRoute) {
        composable<SplashRoute> {
            val navigator = rememberAuthNavigator(navController, onNavigateToMain)
            SplashScreen(navigator = navigator)
        }

        composable<OnboardingRoute> {
            val navigator = rememberAuthNavigator(navController, onNavigateToMain)
            OnboardingScreen(navigator = navigator)
        }

        composable<LoginRoute> {
            val navigator = rememberAuthNavigator(navController, onNavigateToMain)
            LoginScreen(navigator = navigator)
        }

        composable<NicknameRoute> {
            val navigator = rememberAuthNavigator(navController, onNavigateToMain)
            NicknameScreen(navigator = navigator)
        }
    }
}

@Composable
private fun rememberAuthNavigator(
    navController: NavController,
    onNavigateToMain: () -> Unit,
): AuthNavigator = remember(navController) {
    object : AuthNavigator {
        override fun navigateToOnboarding() {
            navController.navigate(OnboardingRoute) {
                popUpTo(SplashRoute) { inclusive = true }
            }
        }

        override fun navigateToLogin() {
            navController.navigate(LoginRoute) {
                popUpTo(OnboardingRoute) { inclusive = true }
            }
        }

        override fun navigateToNickname() {
            navController.navigate(NicknameRoute)
        }

        override fun navigateToMain() {
            onNavigateToMain()
        }
    }
}
