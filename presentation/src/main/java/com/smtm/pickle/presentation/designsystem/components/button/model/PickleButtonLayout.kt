package com.smtm.pickle.presentation.designsystem.components.button.model

/**
 * 버튼 그룹 레이아웃 타입
 * @property RowFixedLeading Row: 좌측 고정 너비 + 우측 fillMaxWidth
 * @property RowEqual Row: 1:1 동일 너비
 * @property Column Column: 세로 배치
 */
sealed interface PickleButtonLayout {

    data object RowFixedLeading : PickleButtonLayout

    data object RowEqual : PickleButtonLayout

    data object Column : PickleButtonLayout
}
