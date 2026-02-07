package com.smtm.pickle.presentation.mypage.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextField
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.mypage.MyPageViewModel
import com.smtm.pickle.presentation.mypage.profile.components.NicknameSettingBaseContent

@Composable
fun ProfileScreen(
    onNicknameEditClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileContent(
        nickname = uiState.profile.nickname,
        onNicknameEditClick = onNicknameEditClick,
        onBackClick = onBackClick
    )
}

@Composable
fun ProfileContent(
    nickname: String,
    onNicknameEditClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NicknameSettingBaseContent(
        title = stringResource(R.string.profile_title),
        instruction = stringResource(R.string.nickname_label),
        onBackClick = onBackClick,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(Dimensions.radius))
        ) {
            PickleTextField.Static(
                value = nickname,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Image(
                        painter = painterResource(R.drawable.ic_mypage_arrow_right),
                        contentDescription = stringResource(R.string.nickname_edit_title),
                    )
                }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(onClick = onNicknameEditClick)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    PickleTheme {
        ProfileContent(
            nickname = "김가난",
            onNicknameEditClick = {},
            onBackClick = {}
        )
    }
}
