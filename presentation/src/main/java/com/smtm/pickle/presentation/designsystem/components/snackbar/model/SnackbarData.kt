package com.smtm.pickle.presentation.designsystem.components.snackbar.model

/**
 * 커스텀 스낵바 데이터
 * @param message 메시지
 * @param iconType 아이콘 타입
 * @param position 스낵바 위치
 * @param actionLabel 액션 텍스트
 * @param onActionClick 액션 클릭 콜백
 * @param duration 지속 시간
 */
data class SnackbarData(
    val message: String,
    val iconType: SnackbarIconType = SnackbarIconType.None,
    val position: SnackbarPosition = SnackbarPosition.AboveSystemNavigation,
    val actionLabel: String? = null,
    val onActionClick: (() -> Unit)? = null,
    val duration: Long = SnackbarDuration.TOAST_SHORT.duration,
)
