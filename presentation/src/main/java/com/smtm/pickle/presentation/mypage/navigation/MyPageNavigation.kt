package com.smtm.pickle.presentation.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.mypage.alarmsetting.AlarmSettingScreen
import com.smtm.pickle.presentation.mypage.mybadge.MyBadgeScreen
import com.smtm.pickle.presentation.mypage.myledger.MyLedgerScreen
import com.smtm.pickle.presentation.mypage.setting.SettingScreen
import com.smtm.pickle.presentation.navigation.route.AlarmSettingRoute
import com.smtm.pickle.presentation.navigation.route.MyBadgeRoute
import com.smtm.pickle.presentation.navigation.route.MyLedgerRoute
import com.smtm.pickle.presentation.navigation.route.SettingRoute

fun NavGraphBuilder.myPageDestinations(navController: NavController) {
    composable<MyLedgerRoute> {
        MyLedgerScreen()
    }
    composable<MyBadgeRoute> {
        MyBadgeScreen()
    }
    composable<SettingRoute> {
        SettingScreen()
    }
    composable<AlarmSettingRoute> {
        AlarmSettingScreen()
    }
}
