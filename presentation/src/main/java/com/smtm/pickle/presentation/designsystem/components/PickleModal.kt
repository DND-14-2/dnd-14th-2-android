package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/** @sample com.smtm.pickle.presentation.designsystem.components.PickleBottomSheetPreview */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickleBottomSheet(
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    gesturesEnabled: Boolean = true,
    hasDragHandle: Boolean = true,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        sheetGesturesEnabled = gesturesEnabled,
        containerColor = PickleTheme.colors.base0,
        shape = RoundedCornerShape(
            topStart = Dimensions.radiusModal,
            topEnd = Dimensions.radiusModal
        ),
        dragHandle = {
            if (hasDragHandle) {
                Surface(
                    modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                    color = PickleTheme.colors.gray200,
                    shape = RoundedCornerShape(Dimensions.radiusFull),
                ) {
                    Box(Modifier.size(width = 48.dp, height = 4.dp))
                }
            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 14.dp),
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PickleBottomSheetPreview() {
    PickleTheme {
        PickleBottomSheet(
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { true },
            ),
            onDismiss = {},
            hasDragHandle = true
        ) {
            Text("즐겨찾는 내역")
            Spacer(modifier = Modifier.height(12.dp))
            PickleButton(
                text = "삭제하기",
                onClick = {}
            )
        }
    }
}
