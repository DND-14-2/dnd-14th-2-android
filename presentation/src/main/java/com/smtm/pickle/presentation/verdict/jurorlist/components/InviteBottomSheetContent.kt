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
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun InviteBottomSheetContent(
    invitationCode: String
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipBoardManager = LocalClipboard.current
    val clipData = ClipData.newPlainText("invitationCode", invitationCode)

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "smsto:".toUri()
        putExtra("sms_body", invitationCode) // TODO: 문자 메시지 내용 정하기
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = stringResource(R.string.invite_bottom_sheet_title),
            style = PickleTheme.typography.head2SemiBold,
            color = PickleTheme.colors.gray800,
        )
        Spacer(modifier = Modifier.height(16.dp))

        PickleCard(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp),
            color = PickleTheme.colors.gray50,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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
            text = stringResource(R.string.invite_bottom_sheet_share),
            onClick = {
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Timber.e(e, "문자 전송 시도 실패")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InviteBottomSheetContentPreview() {
    PickleTheme {
        InviteBottomSheetContent("A123450")
    }
}
