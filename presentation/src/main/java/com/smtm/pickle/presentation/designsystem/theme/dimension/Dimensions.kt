package com.smtm.pickle.presentation.designsystem.theme.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
object Dimensions {
    // 버튼
    val buttonHeightSmall = 32.dp
    val buttonHeightMedium = 40.dp
    val buttonHeightLarge = 52.dp
    val buttonPaddingSmall = 6.dp
    val buttonPaddingMedium = 8.dp
    val buttonPaddingLarge = 14.dp
    val buttonLeadingWidthSmall = 80.dp
    val buttonLeadingWidthMedium = 90.dp
    val buttonLeadingWidthLarge = 100.dp
    val buttonSpacingSmall = 4.dp
    val buttonSpacingMedium = 6.dp
    val buttonSpacingLarge = 10.dp

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
    val appBarHorizontalSpacing = 12.dp
    val appBarActionsSpacing = 4.dp
    val appBarSearchSpacing = 6.dp
    val appBarLogoWidth = 84.dp
    val appBarLogoHeight = 32.dp

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

    // 다이얼로그
    val dialogPaddingTop = 40.dp
    val dialogPaddingContent = 20.dp
    val dialogTitleSubtitleSpacing = 6.dp
    val dialogContentButtonSpacing = 24.dp
    val dialogImageSize = 140.dp
    val dialogImageTitleSpacing = 24.dp
    val dialogInputTopSpacing = 20.dp
    val dialogButtonActionTextSpacing = 10.dp
}
