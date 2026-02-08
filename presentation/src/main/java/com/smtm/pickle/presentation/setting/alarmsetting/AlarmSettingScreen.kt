package com.smtm.pickle.presentation.setting.alarmsetting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun AlarmSettingScreen(
    viewModel: AlarmSettingViewModel = hiltViewModel()
) {

    AlarmSettingContent()
}

@Composable
private fun AlarmSettingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Alarm Setting Screen")
        }
    }
}
