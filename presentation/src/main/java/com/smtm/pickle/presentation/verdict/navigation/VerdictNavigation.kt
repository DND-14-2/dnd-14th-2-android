package com.smtm.pickle.presentation.verdict.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.navigation.route.JurorDetailRoute
import com.smtm.pickle.presentation.navigation.route.JurorListRoute
import com.smtm.pickle.presentation.navigation.route.VerdictCreateRoute
import com.smtm.pickle.presentation.navigation.route.VerdictRequestRoute
import com.smtm.pickle.presentation.navigation.route.VerdictResultRoute
import com.smtm.pickle.presentation.verdict.create.VerdictCreateScreen
import com.smtm.pickle.presentation.verdict.jurordetail.JurorDetailScreen
import com.smtm.pickle.presentation.verdict.jurorlist.JurorListScreen
import com.smtm.pickle.presentation.verdict.request.VerdictRequestScreen
import com.smtm.pickle.presentation.verdict.result.VerdictResultScreen

fun NavGraphBuilder.verdictDestinations(navController: NavController) {
    composable<VerdictCreateRoute> {
        VerdictCreateScreen()
    }
    composable<VerdictRequestRoute> {
        VerdictRequestScreen()
    }
    composable<VerdictResultRoute> {
        VerdictResultScreen()
    }
    composable<JurorListRoute> {
        JurorListScreen()
    }
    composable<JurorDetailRoute> {
        JurorDetailScreen()
    }
}