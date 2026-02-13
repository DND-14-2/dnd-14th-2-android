package com.smtm.pickle.presentation.designsystem.components.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.button.model.CancelWidth
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = PickleTheme.colors.primary400,
    textColor: Color = PickleTheme.colors.base0,
    textStyle: TextStyle = PickleTheme.typography.body1Bold,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(Dimensions.buttonHeight)
            .fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = PickleTheme.colors.gray100,
        ),
        shape = RoundedCornerShape(Dimensions.radius)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if (enabled) textColor else PickleTheme.colors.gray600
        )
    }
}

@Composable
fun PickleButtonWithCancel(
    confirmText: String,
    cancelText: String,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    cancelWidth: CancelWidth? = null,
    color: Color = PickleTheme.colors.primary400,
    textStyle: TextStyle = PickleTheme.typography.body1Bold
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val widthModifier = remember(cancelWidth) {
            when (cancelWidth) {
                CancelWidth.Half -> Modifier.weight(1f)
                CancelWidth.Medium -> Modifier.width(96.dp)
                CancelWidth.Small -> Modifier.width(64.dp)
                null -> Modifier
            }
        }

        Button(
            modifier = Modifier
                .then(widthModifier)
                .height(Dimensions.buttonHeight),
            onClick = onCancelClick,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.gray100,
                contentColor = PickleTheme.colors.gray600,
            ),
            shape = RoundedCornerShape(Dimensions.radius)
        ) {
            Text(
                text = cancelText,
                style = textStyle,
                color = PickleTheme.colors.gray600
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            modifier = Modifier
                .weight(1f)
                .height(Dimensions.buttonHeight),
            onClick = onConfirmClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = PickleTheme.colors.base0,
            ),
            shape = RoundedCornerShape(Dimensions.radius)
        ) {
            Text(
                text = confirmText,
                style = textStyle,
                color = PickleTheme.colors.base0
            )
        }
    }
}

@Preview
@Composable
private fun DefaultButtonPreview() {
    PickleTheme {
        Column {
            PickleButton(
                text = "Buttons",
                onClick = {}
            )

            Spacer(Modifier.height(8.dp))

            PickleButton(
                text = "Buttons",
                onClick = {},
                color = PickleTheme.colors.primary500,
            )
        }
    }
}

@Preview
@Composable
private fun ButtonWithCancelPreview() {
    PickleTheme {
        Column {
            PickleButtonWithCancel(
                confirmText = "확인",
                cancelText = "취소",
                onConfirmClick = {},
                onCancelClick = {},
                cancelWidth = CancelWidth.Small,
            )
        }
    }
}
