package com.smtm.pickle.presentation.verdict.complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleTitleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.PickleAppBarAction
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun VerdictCompletedContent(
    defendantNickname: String,
    onDismiss: () -> Unit
) {
    Scaffold(
        topBar = {
            PickleTitleAppBar(
                title = "",
                actions = listOf(
                    PickleAppBarAction.Icon(
                        icon = R.drawable.ic_description_close,
                        contentDescription = "닫기",
                        onClick = onDismiss,
                    )
                ),
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
                    append(stringResource(R.string.verdict_request_send))
                },
                style = PickleTheme.typography.head2SemiBold,
                color = PickleTheme.colors.gray800,
            )
            Spacer(modifier = Modifier.height(10.dp))

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
