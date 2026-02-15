package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun SentRequestTitle() {
    Text(
        text = "내가 보낸 심판 요청",
        style = PickleTheme.typography.head3Bold,
        color = PickleTheme.colors.gray700,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}
