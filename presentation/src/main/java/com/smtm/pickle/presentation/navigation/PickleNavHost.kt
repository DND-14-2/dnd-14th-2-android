package com.smtm.pickle.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.ledger.navigation.ledgerDestinations
import com.smtm.pickle.presentation.main.MainScreen
import com.smtm.pickle.presentation.mypage.navigation.myPageDestinations
import com.smtm.pickle.presentation.navigation.graph.authNavGraph
import com.smtm.pickle.presentation.navigation.route.AuthGraphRoute
import com.smtm.pickle.presentation.navigation.route.MainRoute
import com.smtm.pickle.presentation.verdict.navigation.verdictDestinations

@Composable
fun PickleNavHost(
    navController: NavHostController = rememberNavController(),
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoute,
        builder = {
            // 인증 플로우 그래프
            authNavGraph(
                navController = navController,
                onNavigateToMain = {
                    navController.navigate(MainRoute) {
                        popUpTo(AuthGraphRoute) { inclusive = true }
                    }
                }
            )

            // 메인 탭 화면
            composable<MainRoute> {
                MainScreen(
                    rootNavController = navController,
                    onGlobalNavEvent = onGlobalNavEvent
                )
            }

            // 가계부
            ledgerDestinations(navController)
            // 심판하기
            verdictDestinations(navController)
            // 마이페이지
            myPageDestinations(navController)
        }
    )
}