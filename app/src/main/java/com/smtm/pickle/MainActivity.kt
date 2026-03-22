package com.smtm.pickle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.domain.event.SessionEventBus
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.navigation.GlobalNavEvent
import com.smtm.pickle.presentation.navigation.PickleNavHost
import com.smtm.pickle.presentation.navigation.route.LoginRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionEventBus: SessionEventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )

        setContent {
            PickleTheme {
                val navController = rememberNavController()
                val snackbarState = remember { SnackbarState() }
                val scope = rememberCoroutineScope()

                val handleGlobalNavEvent: (GlobalNavEvent) -> Unit = remember(navController) {
                    { event ->
                        when (event) {
                            is GlobalNavEvent.Logout -> {
                                navController.navigate(LoginRoute) {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                }
                            }

                            is GlobalNavEvent.SessionExpired -> {
                                navController.navigate(LoginRoute) {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                }
                                scope.launch {
                                    snackbarState.show(
                                        PickleSnackbar.snackbarShort(
                                            message = event.message
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    sessionEventBus.sessionExpired.collect {
                        handleGlobalNavEvent(GlobalNavEvent.SessionExpired("세션이 만료되었습니다. 다시 로그인해주세요."))
                    }
                }

                PickleNavHost(
                    navController = navController,
                    onGlobalNavEvent = handleGlobalNavEvent,
                )

                SnackbarHost(snackbarState)
            }
        }
    }
}
