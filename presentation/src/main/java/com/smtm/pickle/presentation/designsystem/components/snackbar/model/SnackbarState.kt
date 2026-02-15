package com.smtm.pickle.presentation.designsystem.components.snackbar.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * 커스텀 스낵바 상태
 * @property currentSnackbar 현재 보여지는 스낵바 데이터
 * @property show 스낵바 보여주기
 * @property dismiss 스낵바 숨기기
 * ---
 * ```
 * val snackbarState = remember { SnackbarState() }
 *
 * // scaffold snackbarState 주입
 * Box(modifier = Modifier.fillMaxSize()) {
 *     Scaffold { padding ->
 *         MainScreen(
 *             modifier = Modifier.padding(padding),
 *             snackbarState = snackbarState
 *         )
 *     }
 *
 *     SnackbarHost(
 *         snackbarState = snackbarState
 *     )
 * }
 *
 * // Screen
 * Button(
 *     onClick = {
 *         snackbarState.show(
 *             PickleSnackbar.toastSuccess(
 *                 message = "메시지에 마침표를 찍어요",
 *                 actionLabel = "확인"
 *             )
 *         )
 *     }
 * )
 * ```
 */
@Stable
class SnackbarState {

    private val snackbarQueue = ArrayDeque<SnackbarData>()

    var currentSnackbar by mutableStateOf<SnackbarData?>(null)
        private set

    fun show(snackbarData: SnackbarData) {
        if (currentSnackbar == null) {
            currentSnackbar = snackbarData
        } else {
            snackbarQueue.add(snackbarData)
        }
    }

    fun dismiss() {
        currentSnackbar = snackbarQueue.removeFirstOrNull()
    }
}
