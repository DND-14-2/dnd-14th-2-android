package com.smtm.pickle.presentation.common.model.ledger

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.presentation.R

enum class CategoryUiModel(
    @StringRes val stringResId: Int,
    @DrawableRes val iconResId: Int,
    val chartColorHex: Long,
) {
    Food(
        stringResId = R.string.ledger_category_food,
        iconResId = R.drawable.ic_ledger_category_food,
        chartColorHex = 0xFF2BC4C1,
    ),
    Transport(
        stringResId = R.string.ledger_category_transport,
        iconResId = R.drawable.ic_ledger_category_transport,
        chartColorHex = 0xFFFFDD52,
    ),
    Housing(
        stringResId = R.string.ledger_category_housing,
        iconResId = R.drawable.ic_ledger_category_housing,
        chartColorHex = 0xFF4493FF,
    ),
    Shopping(
        stringResId = R.string.ledger_category_shopping,
        iconResId = R.drawable.ic_ledger_category_shopping,
        chartColorHex = 0xFFFF70A7,
    ),
    HealthMedical(
        stringResId = R.string.ledger_category_health_medical,
        iconResId = R.drawable.ic_ledger_category_health_medical,
        chartColorHex = 0xFF63C3FF,
    ),
    EducationSelfDevelopment(
        stringResId = R.string.ledger_category_education_self_development,
        iconResId = R.drawable.ic_ledger_category_education_self_development,
        chartColorHex = 0xFF75C375,
    ),
    LeisureHobby(
        stringResId = R.string.ledger_category_leisure_hobby,
        iconResId = R.drawable.ic_ledger_category_leisure_hobby,
        chartColorHex = 0xFFB362FF,
    ),
    SavingFinance(
        stringResId = R.string.ledger_category_saving_finance,
        iconResId = R.drawable.ic_ledger_category_saving_finance,
        chartColorHex = 0xFFFF9429,
    ),
    Salary(
        stringResId = R.string.ledger_category_salary,
        iconResId = R.drawable.ic_ledger_category_salary,
        chartColorHex = 0xFF2BC4C1,
    ),
    SideIncome(
        stringResId = R.string.ledger_category_side_income,
        iconResId = R.drawable.ic_ledger_category_side_income,
        chartColorHex = 0xFFFFDD52,
    ),
    Bonus(
        stringResId = R.string.ledger_category_bonus,
        iconResId = R.drawable.ic_ledger_category_bonus,
        chartColorHex = 0xFF4493FF,
    ),
    Allowance(
        stringResId = R.string.ledger_category_allowance,
        iconResId = R.drawable.ic_ledger_category_allowance,
        chartColorHex = 0xFFFF70A7,
    ),
    PartTimeIncome(
        stringResId = R.string.ledger_category_part_time_income,
        iconResId = R.drawable.ic_ledger_category_part_time_income,
        chartColorHex = 0xFF64BEFF,
    ),
    FinancialIncome(
        stringResId = R.string.ledger_category_financial_income,
        iconResId = R.drawable.ic_ledger_category_financial_income,
        chartColorHex = 0xFF75C375,
    ),
    SplitBill(
        stringResId = R.string.ledger_category_split_bill,
        iconResId = R.drawable.ic_ledger_category_split_bill,
        chartColorHex = 0xFFB362FF,
    ),
    Transfer(
        stringResId = R.string.ledger_category_transfer,
        iconResId = R.drawable.ic_ledger_category_transfer,
        chartColorHex = 0xFFFF9429,
    ),
    Other(
        stringResId = R.string.ledger_category_other,
        iconResId = R.drawable.ic_ledger_category_other,
        chartColorHex = 0xFFAAAAAA,
    ),
}

fun LedgerCategory.toUiModel(): CategoryUiModel = when (this) {
    LedgerCategory.Food -> CategoryUiModel.Food
    LedgerCategory.Transport -> CategoryUiModel.Transport
    LedgerCategory.Housing -> CategoryUiModel.Housing
    LedgerCategory.Shopping -> CategoryUiModel.Shopping
    LedgerCategory.HealthMedical -> CategoryUiModel.HealthMedical
    LedgerCategory.EducationSelfDevelopment -> CategoryUiModel.EducationSelfDevelopment
    LedgerCategory.LeisureHobby -> CategoryUiModel.LeisureHobby
    LedgerCategory.SavingFinance -> CategoryUiModel.SavingFinance
    LedgerCategory.Salary -> CategoryUiModel.Salary
    LedgerCategory.SideIncome -> CategoryUiModel.SideIncome
    LedgerCategory.Bonus -> CategoryUiModel.Bonus
    LedgerCategory.Allowance -> CategoryUiModel.Allowance
    LedgerCategory.PartTimeIncome -> CategoryUiModel.PartTimeIncome
    LedgerCategory.FinancialIncome -> CategoryUiModel.FinancialIncome
    LedgerCategory.SplitBill -> CategoryUiModel.SplitBill
    LedgerCategory.Transfer -> CategoryUiModel.Transfer
    LedgerCategory.Other -> CategoryUiModel.Other
}

fun CategoryUiModel.toDomain(): LedgerCategory = when (this) {
    CategoryUiModel.Food -> LedgerCategory.Food
    CategoryUiModel.Transport -> LedgerCategory.Transport
    CategoryUiModel.Housing -> LedgerCategory.Housing
    CategoryUiModel.Shopping -> LedgerCategory.Shopping
    CategoryUiModel.HealthMedical -> LedgerCategory.HealthMedical
    CategoryUiModel.EducationSelfDevelopment -> LedgerCategory.EducationSelfDevelopment
    CategoryUiModel.LeisureHobby -> LedgerCategory.LeisureHobby
    CategoryUiModel.SavingFinance -> LedgerCategory.SavingFinance
    CategoryUiModel.Salary -> LedgerCategory.Salary
    CategoryUiModel.SideIncome -> LedgerCategory.SideIncome
    CategoryUiModel.Bonus -> LedgerCategory.Bonus
    CategoryUiModel.Allowance -> LedgerCategory.Allowance
    CategoryUiModel.PartTimeIncome -> LedgerCategory.PartTimeIncome
    CategoryUiModel.FinancialIncome -> LedgerCategory.FinancialIncome
    CategoryUiModel.SplitBill -> LedgerCategory.SplitBill
    CategoryUiModel.Transfer -> LedgerCategory.Transfer
    CategoryUiModel.Other -> LedgerCategory.Other
}
