package com.smtm.pickle.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.setting.components.SettingGroup
import com.smtm.pickle.presentation.setting.model.SettingItem
import com.smtm.pickle.presentation.setting.model.SettingSection
import com.smtm.pickle.presentation.setting.model.SettingTrailingType

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onNavigateToPrivacyPolicy: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val version = viewModel.getPickleVersion(context)

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    SettingEffect.NavigateToPrivacyPolicy -> {
                        onNavigateToPrivacyPolicy()
                    }

                    SettingEffect.NavigateToLogin -> {
                        onNavigateToLogin()
                    }

                    SettingEffect.NavigateBack -> {
                        onNavigateBack()
                    }
                }
            }
        }
    }

    SettingContent(
        version = version,
        onLogoutClick = viewModel::onLogoutClick,
        onWithdrawClick = viewModel::onWithdrawClick,
        onPrivacyPolicyClick = viewModel::navigateToPrivacyPolicy,
        onBackClick = viewModel::onBackClick,
        onVersionClick = { viewModel.openGooglePlay(context) }
    )
}

@Composable
private fun SettingContent(
    version: String,
    onLogoutClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onBackClick: () -> Unit,
    onVersionClick: () -> Unit
) {
    val settings = listOf(
        SettingSection(
            title = stringResource(R.string.setting_account_title),
            items = listOf(
                SettingItem(
                    title = stringResource(R.string.setting_account_logout),
                    onClick = onLogoutClick
                ),
                SettingItem(
                    title = stringResource(R.string.setting_account_secession),
                    onClick = onWithdrawClick
                )
            )
        ),
        SettingSection(
            title = stringResource(R.string.setting_service_title),
            items = listOf(
                SettingItem(
                    title = stringResource(R.string.setting_service_privacy_policy),
                    onClick = onPrivacyPolicyClick
                ),
                SettingItem(
                    title = stringResource(R.string.setting_service_version),
                    trailingType = SettingTrailingType.Text(version),
                    onClick = onVersionClick
                )
            )
        )
    )

    Scaffold(
        containerColor = PickleTheme.colors.background50,
        topBar = {
            PickleAppBar(
                color = PickleTheme.colors.transparent,
                navigationItem = NavigationItem.Back(onClick = onBackClick)
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

@Preview
@Composable
private fun SettingContentPreview() {
    PickleTheme {
        SettingContent(
            version = "1.0.0",
            onLogoutClick = {},
            onWithdrawClick = {},
            onPrivacyPolicyClick = {},
            onBackClick = {},
            onVersionClick = {}
        )
    }
}
