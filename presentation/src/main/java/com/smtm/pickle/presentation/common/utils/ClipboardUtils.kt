package com.smtm.pickle.presentation.common.utils

import android.content.ClipData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import kotlinx.coroutines.launch

@Composable
fun rememberCopyToClipboard(label: String = ""): (String) -> Unit {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    return remember(label) {
        { text ->
            scope.launch {
                clipboard.setClipEntry(
                    ClipData.newPlainText(label, text).toClipEntry()
                )
            }
        }
    }
}
