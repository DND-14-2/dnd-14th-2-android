package com.smtm.pickle.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.home.HomeScreen
import com.smtm.pickle.presentation.main.component.DimOverlay
import com.smtm.pickle.presentation.main.component.HomeExpandableFab
import com.smtm.pickle.presentation.mypage.MyPageScreen
import com.smtm.pickle.presentation.navigation.GlobalNavEvent
import com.smtm.pickle.presentation.navigation.PickleBottomNavigationBar
import com.smtm.pickle.presentation.navigation.route.AlarmSettingRoute
import com.smtm.pickle.presentation.navigation.route.HomeTabRoute
import com.smtm.pickle.presentation.navigation.route.JurorDetailRoute
import com.smtm.pickle.presentation.navigation.route.JurorListRoute
import com.smtm.pickle.presentation.navigation.route.LedgerCreateRoute
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import com.smtm.pickle.presentation.navigation.route.MyLedgerRoute
import com.smtm.pickle.presentation.navigation.route.MyPageTabRoute
import com.smtm.pickle.presentation.navigation.route.MyProfileRoute
import com.smtm.pickle.presentation.navigation.route.SettingRoute
import com.smtm.pickle.presentation.navigation.route.VerdictCreateRoute
import com.smtm.pickle.presentation.navigation.route.VerdictRequestRoute
import com.smtm.pickle.presentation.navigation.route.VerdictResultRoute
import com.smtm.pickle.presentation.navigation.route.VerdictTabRoute
import com.smtm.pickle.presentation.verdict.VerdictScreen
import java.time.LocalDate

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
) {
    val tabNavController = rememberNavController()
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isHomeScreen = currentDestination?.hasRoute(HomeTabRoute::class) == true

    var isFabExpanded by remember { mutableStateOf(false) }
    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val density = LocalDensity.current

    LaunchedEffect(isHomeScreen) {
        if (!isHomeScreen) isFabExpanded = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MainContent(
            rootNavController = rootNavController,
            tabNavController = tabNavController,
            currentDestination = currentDestination,
            isFabExpanded = isFabExpanded,
            onFabClose = { isFabExpanded = false },
            onBottomBarHeightChange = { height ->
                with(density) {
                    bottomBarHeight = height.toDp()
                }
            },
            onSelectedDateChange = { date ->
                selectedDate = date
            }
        )

        if (isHomeScreen) {
            DimOverlay(
                isVisible = isFabExpanded,
                onClick = { isFabExpanded = false },
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = bottomBarHeight + 9.dp,
                        end = 16.dp
                    )
            ) {
                HomeExpandableFab(
                    isExpanded = isFabExpanded,
                    onToggleClick = { isFabExpanded = !isFabExpanded },
                    onCreateClick = {
                        isFabExpanded = false
                        rootNavController.navigate(LedgerCreateRoute.from(selectedDate))
                    },
                )
            }
        }
    }
}

@Composable
private fun MainContent(
    rootNavController: NavHostController,
    tabNavController: NavHostController,
    currentDestination: NavDestination?,
    isFabExpanded: Boolean,
    onFabClose: () -> Unit,
    onBottomBarHeightChange: (Int) -> Unit,
    onSelectedDateChange: (LocalDate) -> Unit,
) {
    val navigateToTab: (Any) -> Unit = { route ->
        tabNavController.navigate(route) {
            popUpTo(tabNavController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        bottomBar = {
            PickleBottomNavigationBar(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    onBottomBarHeightChange(coordinates.size.height)
                },
                currentDestination = currentDestination,
                onNavigate = navigateToTab
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = tabNavController,
            startDestination = HomeTabRoute,
            builder = {
                composable<HomeTabRoute> {
                    HomeScreen(
                        onSelectedDateChange = onSelectedDateChange,
                        isFabExpanded = isFabExpanded,
                        onNavigateToMyPage = { navigateToTab(MyPageTabRoute) },
                        onNavigateToLedgerDetail = { ledgerId ->
                            rootNavController.navigate(LedgerDetailRoute(ledgerId.value))
                        },
                        onFabClose = onFabClose,
                    )
                }

                composable<VerdictTabRoute> {
                    VerdictScreen(
                        onNavigateVerdictCreate = {
                            rootNavController.navigate(VerdictCreateRoute)
                        },
                        onNavigateVerdictRequest = {
                            rootNavController.navigate(VerdictRequestRoute)
                        },
                        onNavigateVerdictResult = {
                            rootNavController.navigate(VerdictResultRoute)
                        },
                        onNavigateJurorList = {
                            rootNavController.navigate(JurorListRoute)
                        },
                        onNavigateJurorDetail = {
                            rootNavController.navigate(JurorDetailRoute)
                        }
                    )
                }

                composable<MyPageTabRoute> {
                    MyPageScreen(
                        onNavigateMyLedger = {
                            rootNavController.navigate(MyLedgerRoute)
                        },
                        onNavigateSetting = {
                            rootNavController.navigate(SettingRoute)
                        },
                        onNavigateAlarmSetting = {
                            rootNavController.navigate(AlarmSettingRoute)
                        },
                        onNavigateMyProfile = {
                            rootNavController.navigate(MyProfileRoute)
                        }
                    )
                }
            }
        )
    }
}
