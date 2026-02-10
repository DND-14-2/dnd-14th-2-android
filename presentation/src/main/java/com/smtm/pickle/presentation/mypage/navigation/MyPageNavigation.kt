package com.smtm.pickle.presentation.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.mypage.alarmsetting.AlarmSettingScreen
import com.smtm.pickle.presentation.mypage.myledger.MyLedgerScreen
import com.smtm.pickle.presentation.mypage.profile.ProfileScreen
import com.smtm.pickle.presentation.mypage.profile.nicknamesetting.NicknameSettingScreen
import com.smtm.pickle.presentation.mypage.setting.SettingScreen
import com.smtm.pickle.presentation.navigation.route.AlarmSettingRoute
import com.smtm.pickle.presentation.navigation.route.LedgerCreateRoute
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import com.smtm.pickle.presentation.navigation.route.MyLedgerRoute
import com.smtm.pickle.presentation.navigation.route.MyProfileRoute
import com.smtm.pickle.presentation.navigation.route.NicknameSettingRoute
import com.smtm.pickle.presentation.navigation.route.SettingRoute

fun NavGraphBuilder.myPageDestinations(navController: NavController) {
    composable<MyLedgerRoute> {
        MyLedgerScreen(
            onNavigateToLedgerDetail = { ledgerId ->
                navController.navigate(LedgerDetailRoute(ledgerId.value))
            },
            onNavigateToLedgerCreate = { date ->
                navController.navigate(LedgerCreateRoute.from(date))
            },
            onNavigateBack = {
                navController.popBackStack()
            },
        )
    }
    composable<SettingRoute> {
        SettingScreen()
    }
    composable<AlarmSettingRoute> {
        AlarmSettingScreen()
    }
    composable<MyProfileRoute> {
        ProfileScreen(
            onNicknameEditClick = {
                navController.navigate(NicknameSettingRoute)
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
    composable<NicknameSettingRoute> {
        NicknameSettingScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
