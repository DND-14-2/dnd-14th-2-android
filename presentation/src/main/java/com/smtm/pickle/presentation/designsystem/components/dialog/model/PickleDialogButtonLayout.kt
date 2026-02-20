package com.smtm.pickle.presentation.designsystem.components.dialog.model

/**
 * 다이얼로그 버튼 레이아웃 타입
 *
 * @property Single 단일 버튼 (Primary Large, fillMaxWidth)
 * @property Horizontal 가로 버튼 2개 (좌=Secondary, 우=Primary, 동일 너비)
 * @property Vertical 세로 버튼 2개 + 액션 텍스트(상=Primary, 하=Ghost, 하단 액션 텍스트(옵셔널), fillMaxWidth)
 */
sealed interface PickleDialogButtonLayout {

    data class Single(
        val text: String,
        val onClick: () -> Unit,
    ) : PickleDialogButtonLayout

    data class Horizontal(
        val confirmText: String,
        val cancelText: String,
        val onConfirmClick: () -> Unit,
        val onCancelClick: () -> Unit,
    ) : PickleDialogButtonLayout

    data class Vertical(
        val primaryText: String,
        val ghostText: String,
        val onPrimaryClick: () -> Unit,
        val onGhostClick: () -> Unit,
        val action: Action? = null,
    ) : PickleDialogButtonLayout

    data class Action(
        val text: String,
        val onClick: () -> Unit,
    )
}
