package com.smtm.pickle.presentation.setting.privacypolicy

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PrivacyPolicyScreen(
    onNavigateBack: () -> Unit
) {
    PrivacyPolicyContent(onBackClick = onNavigateBack)
}

@Composable
fun PrivacyPolicyContent(onBackClick: () -> Unit) {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.base0)
        ) {
            stickyHeader("top_bar") {
                PickleAppBar(
                    title = stringResource(R.string.privacy_policy_title),
                    navigationItem = NavigationItem.Back(onClick = onBackClick),
                )
            }

            item("privacy_policy") {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    PrivacyPolicySubTitle()

                    Spacer(modifier = Modifier.height(16.dp))

                    PrivacyPolicyContent(stringResource(R.string.privacy_policy_description))

                    Spacer(modifier = Modifier.height(16.dp))

                    PrivacyPolicyContent(stringResource(R.string.privacy_policy_contents))
                }
            }
        }
    }
}

@Composable
private fun PrivacyPolicySubTitle() {
    Text(
        text = stringResource(R.string.privacy_policy_subtitle),
        style = PickleTheme.typography.body2Medium,
        color = PickleTheme.colors.gray800
    )
}

@Composable
private fun PrivacyPolicyContent(content: String) {
    Text(
        text = content,
        style = PickleTheme.typography.body5Regular,
        color = PickleTheme.colors.gray700
    )
}

@Preview
@Composable
private fun PrivacyPolicyScreenPreview() {
    PickleTheme {
        PrivacyPolicyContent {}
    }
}
