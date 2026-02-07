package com.smtm.pickle.presentation.mypage.mybadge

import androidx.lifecycle.ViewModel
import com.smtm.pickle.presentation.mypage.mybadge.model.BadgeType
import com.smtm.pickle.presentation.mypage.mybadge.model.BadgeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyBadgeViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<List<BadgeUiState>>(emptyList())
    val uiState: StateFlow<List<BadgeUiState>> = _uiState.asStateFlow()

    init {
        // TODO: 사용자가 보유한 배지 ID 목록을 가져오기
        val ownedBadgeIds = listOf(0, 1, 2, 4, 6)

        _uiState.value = BadgeType.entries
            .filter { it.id in ownedBadgeIds }
            .map { type ->
                BadgeUiState(
                    type = type,
                    isNew = false,
                    isRead = false,
                    isSelected = type == BadgeType.DEFAULT
                )
            }
    }

    fun changeBadge(badgeId: Int) {
        val selectedBadge = BadgeType.fromId(badgeId)
        _uiState.update { currentList ->
            currentList.map { state ->
                state.copy(isSelected = state.type == selectedBadge)
            }
        }
    }

    // TODO: 뱃지 변경 Flow 함수 
}
