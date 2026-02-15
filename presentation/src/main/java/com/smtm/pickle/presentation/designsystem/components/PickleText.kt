package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleSupportingText(
    message: String,
    inputState: InputState,
) {
    val color = when (inputState) {
        is InputState.Error -> PickleTheme.colors.error50
        is InputState.Success -> PickleTheme.colors.primary400
        InputState.Idle -> PickleTheme.colors.gray600
    }

    Text(
        text = message,
        style = PickleTheme.typography.caption2Regular,
        color = color,
    )
}
