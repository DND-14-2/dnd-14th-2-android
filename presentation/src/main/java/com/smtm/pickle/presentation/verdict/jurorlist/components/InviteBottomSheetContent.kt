package com.smtm.pickle.presentation.verdict.jurorlist.components

import android.content.ClipData
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import kotlinx.coroutines.launch

@Composable
fun InviteBottomSheetContent(
    invitationCode: String
) {
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarState() }
    val context = LocalContext.current
    val clipBoardManager = LocalClipboard.current
    val clipData = ClipData.newPlainText("invitationCode", invitationCode)

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "smsto:".toUri()
        putExtra("sms_body", invitationCode) // TODO: 문자 메시지 내용 정하기
    }

    Column {
        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "친구와 함께\n소비심판을 받아보세요",
            style = PickleTheme.typography.head2SemiBold,
            color = PickleTheme.colors.gray800,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "내 초대 코드를 공유해요",
            style = PickleTheme.typography.body3Regular,
            color = PickleTheme.colors.gray700,
        )
        Spacer(modifier = Modifier.height(23.dp))

        PickleCard(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp),
            color = PickleTheme.colors.gray50,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "내 초대 코드",
                    style = PickleTheme.typography.body2Medium,
                    color = PickleTheme.colors.gray800,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = invitationCode,
                        style = PickleTheme.typography.head2SemiBold,
                        color = PickleTheme.colors.gray800,
                    )

                    IconButton(
                        onClick = {
                            scope.launch { clipBoardManager.setClipEntry(ClipEntry(clipData)) }
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_common_copy),
                            contentDescription = "복사하기",
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(26.dp))

        PickleButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            text = "문자로 공유하기",
            onClick = {
                try {
                    context.startActivity(intent)
                } catch (_: Exception) {
                    snackbarState.show(
                        PickleSnackbar.toastError(
                            message = "문자를 전송할 수 없습니다.",
                        )
                    )
                }
            }
        )
    }

    SnackbarHost(snackbarState)
}

@Preview(showBackground = true)
@Composable
private fun InviteBottomSheetContentPreview() {
    PickleTheme {
        InviteBottomSheetContent("A123450")
    }
}
