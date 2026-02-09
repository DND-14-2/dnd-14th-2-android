package com.smtm.pickle.presentation.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.mypage.myledger.MyLedgerScreen
import com.smtm.pickle.presentation.mypage.profile.nicknamesetting.NicknameSettingScreen
import com.smtm.pickle.presentation.mypage.profile.ProfileScreen
import com.smtm.pickle.presentation.mypage.setting.SettingScreen
import com.smtm.pickle.presentation.navigation.route.AlarmSettingRoute
import com.smtm.pickle.presentation.navigation.route.LoginRoute
import com.smtm.pickle.presentation.navigation.route.MyLedgerRoute
import com.smtm.pickle.presentation.navigation.route.MyProfileRoute
import com.smtm.pickle.presentation.navigation.route.NicknameSettingRoute
import com.smtm.pickle.presentation.navigation.route.PrivacyPolicyRoute
import com.smtm.pickle.presentation.navigation.route.SettingRoute
import com.smtm.pickle.presentation.setting.SettingScreen
import com.smtm.pickle.presentation.setting.alarmsetting.AlarmSettingScreen
import com.smtm.pickle.presentation.setting.privacypolicy.PrivacyPolicyScreen

fun NavGraphBuilder.myPageDestinations(navController: NavController) {
    composable<MyLedgerRoute> {
        MyLedgerScreen()
    }
    composable<SettingRoute> {
        SettingScreen(
            onNavigateToPrivacyPolicy = {
                navController.navigate(PrivacyPolicyRoute)
            },
            onNavigateToLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            onNavigateBack = {
                navController.popBackStack()
            },
        )
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
