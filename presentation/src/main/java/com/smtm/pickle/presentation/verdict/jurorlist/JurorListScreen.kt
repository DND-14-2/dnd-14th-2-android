package com.smtm.pickle.presentation.verdict.jurorlist

import android.content.ClipData
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.verdict.jurorlist.components.EmptyJurorContent
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListItem
import com.smtm.pickle.presentation.verdict.jurorlist.components.JurorListMateRequestBanner
import com.smtm.pickle.presentation.verdict.model.MateUiModel
import kotlinx.coroutines.launch
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
    val clipBoardManager = LocalClipboard.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val clipData = ClipData.newPlainText("invitationCode", uiState.inviteCode)
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "smsto:".toUri()
        putExtra("sms_body", uiState.inviteCode) // TODO: 문자 메시지 내용 정하기
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
        is JurorListBottomSheetState.JurorAction -> {
            PickleBottomSheet(
                sheetState = sheetState,
                onDismiss = viewModel::dismissBottomSheet,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onDeleteJurorClick(state.jurorId) }
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_ledger_detail_trashcan), // using trashcan as generic delete
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "친구 삭제하기",
                        style = PickleTheme.typography.body1Bold,
                        color = PickleTheme.colors.gray800
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        JurorListBottomSheetState.None -> Unit
    }

    when (uiState.dialogState) {
        JurorListDialogState.Invite -> {
            PickleDialog(
                title = stringResource(R.string.invite_dialog_title),
                subtitle = "",
                buttonLayout = PickleDialogButtonLayout.Vertical(
                    primaryText = stringResource(R.string.invite_primary_click),
                    ghostText = stringResource(R.string.invite_ghost_click),
                    onPrimaryClick = {
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Timber.e(e, "문자 전송 시도 실패")
                        }
                    },
                    onGhostClick = viewModel::dismissDialog
                ),
                onDismiss = viewModel::dismissDialog,
                inputField = {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimensions.inputHeight),
                        color = PickleTheme.colors.background50,
                        shape = RoundedCornerShape(Dimensions.radius)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = uiState.inviteCode,
                                style = PickleTheme.typography.body2Medium,
                                color = PickleTheme.colors.gray800
                            )
                            PickleIconButtonWithTouchCustom(
                                iconRes = R.drawable.ic_common_copy,
                                onClick = {
                                    scope.launch { clipBoardManager.setClipEntry(ClipEntry(clipData)) }
                                    viewModel.dismissDialog()
                                }
                            )
                        }
                    }
                }
            )
        }

        is JurorListDialogState.DeleteConfirm -> {
            PickleDialog(
                title = "친구를 삭제할까요?",
                subtitle = "삭제 후에는 복구할 수 없어요",
                buttonLayout = PickleDialogButtonLayout.Horizontal(
                    confirmText = "삭제",
                    cancelText = "취소",
                    onConfirmClick = viewModel::confirmDeleteJuror,
                    onCancelClick = viewModel::dismissDialog
                ),
                onDismiss = viewModel::dismissDialog
            )
        }

        JurorListDialogState.None -> Unit
    }

    JurorListContent(
        jurors = uiState.jurors,
        onNavigateBack = onNavigateBack,
        onInviteClick = viewModel::onInviteClick,
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
    onInviteClick: () -> Unit,
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
                    onClick = onInviteClick,
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
                EmptyJurorContent(onInviteClick = onInviteClick)
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
            onInviteClick = {},
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
            onInviteClick = {},
            onMateRequestClick = {},
            onJurorMoreClick = {}
        )
    }
}
