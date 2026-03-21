package com.smtm.pickle.presentation.verdict.jurorlist.materequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleTitleAppBar
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.verdict.jurorlist.materequest.components.MateRequestItem
import com.smtm.pickle.presentation.verdict.model.MateRequestUiModel

@Composable
fun MateRequestScreen(
    viewModel: MateRequestViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is MateRequestEffect.ShowSnackBar -> {
                        snackbarState.show(PickleSnackbar.toastSuccess(message = effect.msg))
                    }
                }
            }
        }
    }

    MateRequestContent(
        requests = uiState.requests,
        onBackClick = onNavigateBack,
        onAcceptClick = viewModel::onAcceptClick,
        onRejectClick = viewModel::onRejectClick,
    )

    SnackbarHost(snackbarState)
}

@Composable
private fun MateRequestContent(
    requests: List<MateRequestUiModel>,
    onBackClick: () -> Unit,
    onAcceptClick: (Long) -> Unit,
    onRejectClick: (Long) -> Unit,
) {
    Scaffold(
        topBar = {
            PickleTitleAppBar(
                title = stringResource(id = R.string.juror_list_mate_request_title),
                onBack = onBackClick,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.base0)
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    items = requests,
                    key = { it.id }
                ) { request ->
                    MateRequestItem(
                        nickname = request.nickname,
                        invitationCode = request.invitationCode,
                        onAcceptClick = { onAcceptClick(request.id) },
                        onRejectClick = { onRejectClick(request.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MateRequestContentPreview() {
    PickleTheme {
        MateRequestContent(
            requests = (1..2).map { MateRequestUiModel(it.toLong(), "지인닉네임", "ABCDEF") },
            onBackClick = {},
            onAcceptClick = {},
            onRejectClick = {},
        )
    }
}
