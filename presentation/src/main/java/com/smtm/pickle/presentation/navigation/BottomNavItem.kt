package com.smtm.pickle.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.navigation.route.HomeTabRoute
import com.smtm.pickle.presentation.navigation.route.MyPageTabRoute
import kotlin.reflect.KClass

/**
 * @param tabRoute 탭 클릭 시 이동할 Route
 * @param tabRouteClass 현재 선택 상태 비교용
 * @param labelResId 탭 라벨
 * @param activatedIconResId 선택된 탭 아이콘
 * @param inActiveIconResId 미선택 아이콘
 */
enum class BottomNavItem(
    val tabRoute: Any,
    val tabRouteClass: KClass<*>,
    @StringRes val labelResId: Int,
    @DrawableRes val activatedIconResId: Int,
    @DrawableRes val inActiveIconResId: Int,
) {
    HOME(
        tabRoute = HomeTabRoute,
        tabRouteClass = HomeTabRoute::class,
        labelResId = R.string.nav_home,
        activatedIconResId = R.drawable.ic_nav_home_activated,
        inActiveIconResId = R.drawable.ic_nav_home_inactivated,
    ),
    MY_PAGE(
        tabRoute = MyPageTabRoute,
        tabRouteClass = MyPageTabRoute::class,
        labelResId = R.string.nav_mypage,
        activatedIconResId = R.drawable.ic_nav_mypage_activated,
        inActiveIconResId = R.drawable.ic_nav_mypage_inactivated,
    ),
}
