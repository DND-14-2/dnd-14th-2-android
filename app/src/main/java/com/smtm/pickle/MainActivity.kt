package com.smtm.pickle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.navigation.GlobalNavEvent
import com.smtm.pickle.presentation.navigation.PickleNavHost
import com.smtm.pickle.presentation.navigation.route.LoginRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )

        setContent {
            PickleTheme {
                val navController = rememberNavController()
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
                            }
                        }
                    }
                }

                PickleNavHost(
                    navController = navController,
                    onGlobalNavEvent = handleGlobalNavEvent,
                )
            }
        }
    }
}
