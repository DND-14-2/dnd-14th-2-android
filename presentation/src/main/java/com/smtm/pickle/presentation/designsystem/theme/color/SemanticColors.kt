package com.smtm.pickle.presentation.designsystem.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SemanticColors(
    // 소셜 로그인
    val kakao: Color,
    val google: Color,

    // 소비 심판
    val guilty: Color,
    val innocent: Color,
    val guiltyBackground: Color,
    val innocentBackground: Color,

    // 지출 카테고리
    val ledgerCategorySavingFinance: Color,
    val ledgerCategoryFood: Color,
    val ledgerCategoryShopping: Color,
    val ledgerCategoryTransport: Color,
    val ledgerCategoryLeisureHobby: Color,
    val ledgerCategoryHousing: Color,
    val ledgerCategoryHealthMedical: Color,
    val ledgerCategoryEducationSelfDevelopment: Color,

    // 수입 카테고리
    val ledgerCategorySalary: Color,
    val ledgerCategorySideIncome: Color,
    val ledgerCategoryBonus: Color,
    val ledgerCategoryAllowance: Color,
    val ledgerCategoryPartTimeIncome: Color,
    val ledgerCategoryFinancialIncome: Color,
    val ledgerCategorySplitBill: Color,
    val ledgerCategoryTransfer: Color,

    val ledgerCategoryOther: Color,
)

val LightSemanticColors = SemanticColors(
    kakao = Color(0xFFFFE812),
    google = ColorPalette.base0,

    guilty = ColorPalette.error50,
    guiltyBackground = Color(0xFFFFEAEA),
    innocent = Color(0xFF5C95FF),
    innocentBackground = Color(0xFFEAF1FF),

    ledgerCategoryFood = Color(0xFF2BC4C1),
    ledgerCategoryTransport = Color(0xFFFFDD52),
    ledgerCategoryHousing = Color(0xFF4493FF),
    ledgerCategoryShopping = Color(0xFFFF70A7),
    ledgerCategoryHealthMedical = Color(0xFF63C3FF),
    ledgerCategoryEducationSelfDevelopment = Color(0xFF75C375),
    ledgerCategoryLeisureHobby = Color(0xFFB362FF),
    ledgerCategorySavingFinance = Color(0xFFFF9429),

    ledgerCategorySalary = Color(0xFF2BC4C1),
    ledgerCategorySideIncome = Color(0xFFFFDD52),
    ledgerCategoryBonus = Color(0xFF4493FF),
    ledgerCategoryAllowance = Color(0xFFFF70A7),
    ledgerCategoryPartTimeIncome = Color(0xFF63C3FF),
    ledgerCategoryFinancialIncome = Color(0xFF75C375),
    ledgerCategorySplitBill = Color(0xFFB362FF),
    ledgerCategoryTransfer = Color(0xFFFF9429),

    ledgerCategoryOther = Color(0xFFAAAAAA),
)

val DarkSemanticColors = LightSemanticColors

val LocalSemanticColors = staticCompositionLocalOf { LightSemanticColors }
