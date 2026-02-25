package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun JudgementDialog(
    onDismiss: () -> Unit,
    onGuiltyClick: () -> Unit,
    onInnocentClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(Dimensions.radiusModal))
                .background(PickleTheme.colors.base0)
                .padding(top = 40.dp, bottom = 20.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.illust_verdict_guilty_small),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(Dimensions.dialogImageTitleSpacing))

            Text(
                text = "소비의 판결을 내려주세요",
                style = PickleTheme.typography.head3Bold,
                color = PickleTheme.colors.gray800,
            )
            Spacer(modifier = Modifier.height(Dimensions.dialogTitleSubtitleSpacing))

            Text(
                text = "친구에게 판결을 전달하게요",
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray600,
            )
            Spacer(modifier = Modifier.height(Dimensions.dialogContentButtonSpacing))

            Row {
                Button(
                    onClick = onInnocentClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimensions.buttonHeightLarge),
                    shape = RoundedCornerShape(Dimensions.radius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PickleTheme.semantic.innocentBackground,
                        contentColor = PickleTheme.semantic.innocent
                    )
                ) {
                    Text(
                        text = "무죄",
                        style = PickleTheme.typography.body4Medium,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = onGuiltyClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimensions.buttonHeightLarge),
                    shape = RoundedCornerShape(Dimensions.radius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PickleTheme.semantic.guiltyBackground,
                        contentColor = PickleTheme.semantic.guilty
                    )
                ) {
                    Text(
                        text = "유죄",
                        style = PickleTheme.typography.body4Medium,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun JudgementDialogPreview() {
    PickleTheme {
        JudgementDialog(
            onDismiss = {},
            onGuiltyClick = {},
            onInnocentClick = {},
        )
    }
}
