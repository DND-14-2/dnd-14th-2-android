package com.smtm.pickle.presentation.ledger.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.smtm.pickle.presentation.ledger.create.LedgerCreateScreen
import com.smtm.pickle.presentation.ledger.detail.LedgerDetailScreen
import com.smtm.pickle.presentation.ledger.edit.LedgerEditScreen
import com.smtm.pickle.presentation.navigation.route.LedgerCreateRoute
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import com.smtm.pickle.presentation.navigation.route.LedgerEditRoute
import com.smtm.pickle.presentation.navigation.route.MainRoute

fun NavGraphBuilder.ledgerDestinations(navController: NavController) {
    composable<LedgerCreateRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<LedgerCreateRoute>()
        val initialDate = route.toLocalDate()
        LedgerCreateScreen(
            date = initialDate,
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToHome = {
                navController.navigate(MainRoute) {
                    popUpTo(MainRoute) { inclusive = false }
                    launchSingleTop = true
                }
            },
        )
    }

    composable<LedgerEditRoute> {
        LedgerEditScreen()
    }

    composable<LedgerDetailRoute> {
        LedgerDetailScreen()
    }
}
