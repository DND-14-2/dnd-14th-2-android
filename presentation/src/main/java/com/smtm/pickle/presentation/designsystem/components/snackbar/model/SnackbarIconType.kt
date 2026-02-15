package com.smtm.pickle.presentation.designsystem.components.snackbar.model

/**
 * 커스텀 스낵바 아이콘 타입
 * - Figma 디자인 상의 성공, 실패 아이콘 사용
 *
 * @property Success 성공
 * @property Error 실패
 * @property None 없음
 * @property Custom 커스텀 아이콘 (`iconRes: Int`)
 */
sealed interface SnackbarIconType {
    data object Success : SnackbarIconType
    data object Error : SnackbarIconType
    data object None : SnackbarIconType
    data class Custom(val iconRes: Int) : SnackbarIconType
}
