package com.smtm.pickle.presentation.verdict.jurorlist

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleBottomSheet
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.verdict.jurorlist.components.EmptyJurorContent
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListItem
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListDeleteMateBottomSheetContent
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListDeleteMateConfirmDialog
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListMateRequestBanner
import com.smtm.pickle.presentation.common.components.ShareInviteCodeDialog
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListInputInviteCodeDialog
import com.smtm.pickle.presentation.verdict.model.MateUiModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JurorListScreen(
    viewModel: JurorListViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToMateRequest: () -> Unit = {},
    onNavigateToJurorDetail: (Long) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val snackbarState = remember { SnackbarState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val shareInviteCode = { code: String ->
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "smsto:".toUri()
                putExtra("sms_body", code)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Timber.e(e, "문자 전송 시도 실패")
        }
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is JurorListEffect.ShowSnackBar -> {
                        snackbarState.show(PickleSnackbar.toastSuccess(message = effect.msg))
                    }
                }
            }
        }
    }

    when (val state = uiState.bottomSheetState) {
        is JurorListBottomSheetState.JurorDelete -> {
            PickleBottomSheet(
                sheetState = sheetState,
                onDismiss = viewModel::dismissBottomSheet,
            ) {
                JurorListDeleteMateBottomSheetContent(
                    onDeleteClick = { viewModel.onDeleteJurorClick(state.jurorId) }
                )
            }
        }

        JurorListBottomSheetState.None -> Unit
    }

    when (uiState.dialogState) {
        JurorListDialogState.CopyInviteCode -> {
            ShareInviteCodeDialog(
                invitationCode = uiState.myInviteCode,
                onPrimaryClick = { shareInviteCode(uiState.myInviteCode) },
                onDismiss = viewModel::dismissDialog,
            )
        }

        is JurorListDialogState.DeleteConfirm -> {
            JurorListDeleteMateConfirmDialog(
                onConfirmClick = viewModel::confirmDeleteJuror,
                onDismiss = viewModel::dismissDialog
            )
        }

        is JurorListDialogState.InputInviteCode -> {
            JurorListInputInviteCodeDialog(
                value = uiState.inputInviteCode,
                inputState = uiState.inputInviteCodeState,
                onValueChange = viewModel::onInputInviteCodeChanged,
                onConfirm = viewModel::onInputInviteConfirmClick,
                onDismiss = viewModel::dismissDialog,
                onActionDone = viewModel::onInputInviteActionDone,
            )
        }

        JurorListDialogState.None -> Unit
    }

    JurorListContent(
        jurors = uiState.jurors,
        onNavigateBack = onNavigateBack,
        onJurorInviteClick = viewModel::onJurorInviteClick,
        onAddJuryClick = viewModel::onAddJurorClick,
        onMateRequestClick = onNavigateToMateRequest,
        onJurorClick = onNavigateToJurorDetail,
        onJurorMoreClick = viewModel::onJurorMoreClick,
    )

    SnackbarHost(snackbarState)
}

@Composable
private fun JurorListContent(
    jurors: List<MateUiModel>,
    onNavigateBack: () -> Unit,
    onJurorInviteClick: () -> Unit,
    onAddJuryClick: () -> Unit,
    onMateRequestClick: () -> Unit,
    onJurorClick: (Long) -> Unit,
    onJurorMoreClick: (Long) -> Unit,
) {
    Scaffold(
        topBar = {
            PickleAppBar(
                title = stringResource(id = R.string.juror_list_title),
                navigationItem = NavigationItem.Back(onNavigateBack),
            ) {
                PickleIconButtonWithTouchCustom(
                    iconRes = R.drawable.ic_verdict_mate_request,
                    onClick = onAddJuryClick,
                    tint = PickleTheme.colors.gray700,
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PickleTheme.colors.base0)
                .padding(paddingValues)
        ) {
            if (jurors.isEmpty()) {
                EmptyJurorContent(onInviteClick = onJurorInviteClick)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // TODO: 메이트 요청 여부에 따라 보이도록 조절
                    item {
                        JurorListMateRequestBanner(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 10.dp),
                            onClick = onMateRequestClick
                        )
                    }

                    items(
                        items = jurors,
                        key = { it.id }
                    ) { juror ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            JurorListItem(
                                nickname = juror.nickname,
                                onClick = { onJurorClick(juror.id) },
                                enabled = false,
                                togetherVerdictCount = juror.verdictCount,
                                code = juror.invitationCode,
                                modifier = Modifier.weight(1f)
                            )

                            PickleIconButtonWithTouchCustom(
                                iconRes = R.drawable.ic_appbar_dot_menu,
                                onClick = { onJurorMoreClick(juror.id) },
                                tint = PickleTheme.colors.gray500,
                                iconSize = 20.dp,
                                touchSize = 32.dp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JurorListContentPreview() {
    PickleTheme {
        JurorListContent(
            jurors = (1..5).map {
                MateUiModel(it.toLong(), "지인닉네임", "${it * 111}", it)
            },
            onNavigateBack = {},
            onJurorClick = {},
            onJurorInviteClick = {},
            onAddJuryClick = {},
            onMateRequestClick = {},
            onJurorMoreClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JurorListEmptyPreview() {
    PickleTheme {
        JurorListContent(
            jurors = emptyList(),
            onNavigateBack = {},
            onJurorClick = {},
            onJurorInviteClick = {},
            onAddJuryClick = {},
            onMateRequestClick = {},
            onJurorMoreClick = {}
        )
    }
}
