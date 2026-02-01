package com.smtm.pickle.presentation.designsystem.components.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarData
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarDuration
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarIconType
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarPosition
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import kotlinx.coroutines.delay

@Composable
fun PickleSnackbar(
    snackbarData: SnackbarData,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconRes = remember(snackbarData.iconType) {
        when (snackbarData.iconType) {
            SnackbarIconType.Success -> R.drawable.ic_snackbar_success
            SnackbarIconType.Error -> R.drawable.ic_snackbar_fail
            is SnackbarIconType.Custom -> snackbarData.iconType.iconRes
            SnackbarIconType.None -> null
        }
    }

    LaunchedEffect(snackbarData) {
        delay(snackbarData.duration)
        onDismiss()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(Dimensions.snackbarHeight)
            .clip(RoundedCornerShape(Dimensions.radius))
            .background(PickleTheme.colors.base60)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            iconRes?.let {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(Dimensions.iconMedium)
                )
            }

            Text(
                text = snackbarData.message,
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.base0,
                modifier = Modifier.weight(1f)
            )

            snackbarData.actionLabel?.let { label ->
                TextButton(
                    onClick = {
                        snackbarData.onActionClick?.invoke()
                        onDismiss()
                    },
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = label,
                        style = PickleTheme.typography.body2Medium,
                        color = PickleTheme.colors.base0
                    )
                }
            }
        }
    }
}

/**
 * 스낵바 호출 헬퍼
 *
 * ``` kotlin
 * SnackbarState.show(
 *      PickleSnackbar.toastSuccess(
 *          message = "메시지에 마침표를 찍어요",
 *          position = SnackbarPosition.BOTTOM
 *      )
 * )
 * ```
 */
object PickleSnackbar {

    /** 토스트 메시지 - 성공 아이콘 */
    fun toastSuccess(
        message: String,
        position: SnackbarPosition = SnackbarPosition.BelowTopAppBar,
        duration: Long = SnackbarDuration.TOAST_SHORT.duration
    ) = SnackbarData(
        message = message,
        iconType = SnackbarIconType.Success,
        position = position,
        duration = duration,
    )

    /** 토스트 메시지 - 실패 아이콘 */
    fun toastError(
        message: String,
        position: SnackbarPosition = SnackbarPosition.BelowTopAppBar,
        duration: Long = SnackbarDuration.TOAST_SHORT.duration
    ) = SnackbarData(
        message = message,
        iconType = SnackbarIconType.Error,
        position = position,
        duration = duration,
    )


    fun snackbarShort(
        message: String,
        position: SnackbarPosition = SnackbarPosition.BelowTopAppBar,
        actionLabel: String? = null,
        onActionClick: (() -> Unit)? = null,
        duration: Long = SnackbarDuration.SNACKBAR_SHORT.duration
    ) = SnackbarData(
        message = message,
        iconType = SnackbarIconType.None,
        position = position,
        actionLabel = actionLabel,
        onActionClick = onActionClick,
        duration = duration,
    )

    fun custom(
        message: String,
        iconType: SnackbarIconType = SnackbarIconType.None,
        position: SnackbarPosition = SnackbarPosition.BelowTopAppBar,
        actionLabel: String? = null,
        onActionClick: (() -> Unit)? = null,
        duration: Long = SnackbarDuration.SNACKBAR_SHORT.duration
    ) = SnackbarData(
        message = message,
        iconType = iconType,
        position = position,
        actionLabel = actionLabel,
        onActionClick = onActionClick,
        duration = duration,
    )
}

@Preview(showBackground = true)
@Composable
private fun PickleSnackbarPreview() {
    PickleTheme {
        Column {
            PickleSnackbar(
                snackbarData = SnackbarData(message = "메시지에 마침표를 찍어요"),
                onDismiss = {}
            )

            PickleSnackbar(
                snackbarData = SnackbarData(
                    message = "메시지에 마침표를 찍어요",
                    actionLabel = "확인"
                ),
                onDismiss = {},
            )
        }
    }
}
