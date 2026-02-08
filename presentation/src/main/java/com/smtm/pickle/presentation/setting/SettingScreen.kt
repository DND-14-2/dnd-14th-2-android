package com.smtm.pickle.presentation.setting

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.setting.components.SettingGroup
import com.smtm.pickle.presentation.setting.model.SettingItem
import com.smtm.pickle.presentation.setting.model.SettingSection
import com.smtm.pickle.presentation.setting.model.SettingTrailingType
import timber.log.Timber

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val version = getPickleVersion(context)

    SettingContent(
        version = version
    )
}

@Composable
private fun SettingContent(version: String) {

    val settings = listOf(
        SettingSection(
            title = stringResource(R.string.setting_account_title),
            items = listOf(
                SettingItem(stringResource(R.string.setting_account_logout)) { /* 로그아웃 */ },
                SettingItem(stringResource(R.string.setting_account_secession)) { /* 탈퇴 */ }
            )
        ),
        SettingSection(
            title = stringResource(R.string.setting_service_title),
            items = listOf(
                SettingItem(stringResource(R.string.setting_service_privacy_policy)),
                SettingItem(
                    title = stringResource(R.string.setting_service_version),
                    trailingType = SettingTrailingType.Text(version)
                )
            )
        )
    )

    Scaffold(
        containerColor = PickleTheme.colors.background50,
        topBar = {
            PickleAppBar(
                color = PickleTheme.colors.transparent,
                navigationItem = NavigationItem.Back(onClick = {})
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            settings.forEach { section ->
                SettingGroup(section = section)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

private fun getPickleVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "0.0.0"
    } catch (e: Exception) {
        Timber.e(e, "버전 정보 가져오기 실패")
        "0.0.0"
    }
}

@Preview
@Composable
private fun SettingContentPreview() {
    PickleTheme {
        SettingContent(
            version = "1.0.0"
        )
    }
}
