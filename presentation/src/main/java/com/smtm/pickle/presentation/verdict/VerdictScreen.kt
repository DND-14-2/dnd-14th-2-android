package com.smtm.pickle.presentation.verdict

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun VerdictScreen(
    viewModel: VerdictViewModel = hiltViewModel(),
    onNavigateVerdictCreate: () -> Unit,
    onNavigateVerdictRequest: () -> Unit,
    onNavigateVerdictResult: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onNavigateJurorDetail: () -> Unit,
) {

    VerdictContent(
        onNavigateVerdictCreate = onNavigateVerdictCreate,
        onNavigateVerdictRequest = onNavigateVerdictRequest,
        onNavigateVerdictResult = onNavigateVerdictResult,
        onNavigateJurorList = onNavigateJurorList,
        onNavigateJurorDetail = onNavigateJurorDetail,
    )
}

@Composable
private fun VerdictContent(
    onNavigateVerdictCreate: () -> Unit,
    onNavigateVerdictRequest: () -> Unit,
    onNavigateVerdictResult: () -> Unit,
    onNavigateJurorList: () -> Unit,
    onNavigateJurorDetail: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Blue.copy(0.5f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onNavigateVerdictCreate) {
                Text("심판하기 생성")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = onNavigateVerdictRequest) {
                Text("심판하기 요청")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = onNavigateVerdictResult) {
                Text("심판 결과")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = onNavigateJurorList) {
                Text("배심원 목록")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = onNavigateJurorDetail) {
                Text("배심원 정보")
            }
        }
    }
}
