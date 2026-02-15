package com.smtm.pickle.presentation.designsystem.theme.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
object Dimensions {
    // 버튼
    /** 수입/지출 버튼 */
    val buttonHeightLarge = 60.dp
    val buttonHeight = 52.dp
    /** 소셜 로그인, 다이얼로그 내 버튼 */
    val buttonHeightSmall = 48.dp

    // 입력 필드
    val inputHeight = 48.dp
    val searchHeight = 40.dp

    // 아이콘
    val iconLarge = 32.dp
    val iconMedium = 24.dp
    val iconSmall = 20.dp

    // 스낵바
    val snackbarHeight = 56.dp

    // 앱바
    val appbarHeight = 56.dp

    // 칩
    /** 바텀시트, 다이얼로그 등 내부 칩 */
    val chipHeightBadge = 24.dp
    val chipHeight = 32.dp
    val chipHeightInModal= 36.dp
    val chipHeightWithClickable = 44.dp

    // 심판 물건 아이콘
    val judgementIcon = 40.dp
    val judgementIconSmall = 32.dp

    // 프로필 이미지
    val profileSizeSmall = 40.dp
    val profileSizeCircle = 50.dp
    val profileSize = 60.dp
    val profileSizeLarge = 80.dp
    val profileSizeInSetting = 96.dp

    val profileRadiusSmall = 14.dp
    val profileRadius = 20.dp
    val profileRadiusLarge = 24.dp
    val profileRadiusInSetting = 32.dp

    // Corner Radius
    val radiusSmall = 10.dp
    val radius = 12.dp
    /** 컨텐츠를 포함하는 카드 */
    val radiusSurface = 16.dp
    /** 바텀시트, 다이얼로그 등 */
    val radiusModal = 24.dp
    val radiusFull = 999.dp

    val bottomContentHeight = 72.dp
}
