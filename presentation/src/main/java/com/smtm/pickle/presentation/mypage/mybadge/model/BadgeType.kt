package com.smtm.pickle.presentation.mypage.mybadge.model

import androidx.annotation.DrawableRes
import com.smtm.pickle.presentation.R

enum class BadgeType(
    val id: Int, // TODO: 뱃지 정렬 및 구분용 아이디
    val badgeName: String, // TODO: 이름 정해지면 String 리소스로 변경
    val description: String? = null, // TODO: 위와 동일
    @get:DrawableRes val iconRes: Int
) {
    DEFAULT(
        id = 0,
        badgeName = "기본 배지",
        iconRes = R.drawable.illust_profile_default
    ),
    FIRST_JUDGMENT(
        id = 1,
        badgeName = "첫 판결",
        iconRes = R.drawable.illust_profile_default
    ),
    JUDGMENT_MASTER(
        id = 2,
        badgeName = "판결 마스터",
        iconRes = R.drawable.illust_profile_default
    ),
    RICH_MAN(
        id = 3,
        badgeName = "부자 되세요",
        iconRes = R.drawable.illust_profile_default
    ),
    SAVING_KING(
        id = 4,
        badgeName = "저축왕",
        iconRes = R.drawable.illust_profile_default
    ),
    SPENDING_KING(
        id = 5,
        badgeName = "소비왕",
        iconRes = R.drawable.illust_profile_default
    ),
    PICKLE_LOVER(
        id = 6,
        badgeName = "피클 러버",
        iconRes = R.drawable.illust_profile_default
    ),
    NEW_BIE(
        id = 7,
        badgeName = "뉴비",
        iconRes = R.drawable.illust_profile_default
    );

    companion object {
        fun fromId(id: Int) = entries.find { it.id == id } ?: DEFAULT
    }
}
