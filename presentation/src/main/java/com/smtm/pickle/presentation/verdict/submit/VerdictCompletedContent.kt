package com.smtm.pickle.presentation.verdict.submit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun VerdictCompletedContent(
    defendantNickname: String,
    onDismiss: () -> Unit
) {
    Scaffold(
        topBar = {
            PickleAppBar(
                actions = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            painter = painterResource(R.drawable.ic_description_close),
                            contentDescription = "닫기",
                            tint = PickleTheme.colors.gray700,
                        )
                    }
                }
            )
        },
        bottomBar = {
            PickleButton(
                text = "홈화면으로 돌아가기",
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 14.dp)
                    .navigationBarsPadding()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = PickleTheme.colors.primary500)) {
                        append(defendantNickname)
                    }
                    append("님에게\n판결을 보냈어요!")
                },
                style = PickleTheme.typography.head2SemiBold,
                color = PickleTheme.colors.gray800,
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "피고인에게 알림이 전송돼요",
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray600,
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.illust_common_confetti),
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 34.dp)
                )

                Image(
                    painter = painterResource(R.drawable.illust_common_gavel),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun VerdictCompletedContentPreview() {
    PickleTheme {
        VerdictCompletedContent(
            defendantNickname = "김철수",
            onDismiss = {}
        )
    }
}
