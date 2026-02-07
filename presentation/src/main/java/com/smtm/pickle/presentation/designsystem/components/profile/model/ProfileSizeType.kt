package com.smtm.pickle.presentation.designsystem.components.profile.model

import androidx.compose.ui.unit.Dp
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileSizeType.InSetting
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileSizeType.Large
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileSizeType.NORMAL
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileSizeType.SMALL
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/**
 * 프로필 크기와 코너 타입
 * @property InSetting 닉네임 설정에서 사용. `size: 96 dp`, `cornerRadius: 32 dp`
 * @property Large 마이페이지 또는 심판 내역에서 사용. `size: 80 dp`, `cornerRadius: 24 dp`
 * @property NORMAL 홈이나 리스트에서 사용. `size: 60 dp`, `cornerRadius: 20 dp`
 * @property SMALL 디자인상 선언 되었으나 아직 사용처 없음. `size: 40 dp`, `cornerRadius: 14 dp`
 */
enum class ProfileSizeType(val size: Dp, val cornerRadius: Dp) {
    InSetting(Dimensions.profileSizeInSetting, Dimensions.profileRadiusInSetting),
    Large(Dimensions.profileSizeLarge, Dimensions.profileRadiusLarge),
    NORMAL(Dimensions.profileSize, Dimensions.profileRadius),
    SMALL(Dimensions.profileSizeSmall, Dimensions.profileRadiusSmall),
}
