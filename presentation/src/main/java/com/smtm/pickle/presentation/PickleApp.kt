package com.smtm.pickle.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.navigation.PickleNavHost
import com.smtm.pickle.presentation.navigation.route.LoginRoute

@Composable
fun PickleApp(
    appViewModel: AppViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val snackbarState = remember { SnackbarState() }
    val sessionExpiredMessage = stringResource(R.string.global_session_expired)

    LaunchedEffect(Unit) {
        appViewModel.authNavEvent.collect { event ->
            navController.navigate(LoginRoute) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            if (event is AuthNavEvent.ToLoginWithMessage) {
                snackbarState.show(
                    PickleSnackbar.snackbarShort(message = sessionExpiredMessage)
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PickleNavHost(navController = navController)
        SnackbarHost(snackbarState)
    }
}
