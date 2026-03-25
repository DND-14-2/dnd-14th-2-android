package com.smtm.pickle.presentation.mypage.navigation

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.mypage.myledger.MyLedgerScreen
import com.smtm.pickle.presentation.mypage.profile.ProfileScreen
import com.smtm.pickle.presentation.mypage.profile.nicknamesetting.NicknameSettingScreen
import com.smtm.pickle.presentation.navigation.route.AlarmSettingRoute
import com.smtm.pickle.presentation.navigation.route.LedgerCreateRoute
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import com.smtm.pickle.presentation.navigation.route.MyLedgerRoute
import com.smtm.pickle.presentation.navigation.route.MyProfileRoute
import com.smtm.pickle.presentation.navigation.route.NicknameSettingRoute
import com.smtm.pickle.presentation.navigation.route.SettingRoute
import com.smtm.pickle.presentation.setting.SettingScreen
import com.smtm.pickle.presentation.setting.alarmsetting.AlarmSettingScreen

private const val PrivacyPolicyUrl = "https://www.notion.so/303e42cd9924802abd39eabb3685ca3b"

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
        val context = LocalContext.current
        SettingScreen(
            onNavigateToPrivacyPolicy = {
                runCatching {
                    val intent = Intent(Intent.ACTION_VIEW, PrivacyPolicyUrl.toUri())
                    context.startActivity(intent)
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
