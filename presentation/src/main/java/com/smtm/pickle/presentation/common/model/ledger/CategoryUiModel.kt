package com.smtm.pickle.presentation.common.model.ledger

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.presentation.R

enum class CategoryUiModel(
    @StringRes val stringResId: Int,
    @DrawableRes val iconResId: Int,
) {
    Food(
        stringResId = R.string.ledger_category_food,
        iconResId = R.drawable.ic_ledger_category_food,
    ),
    Transport(
        stringResId = R.string.ledger_category_transport,
        iconResId = R.drawable.ic_ledger_category_transport,
    ),
    Housing(
        stringResId = R.string.ledger_category_housing,
        iconResId = R.drawable.ic_ledger_category_housing,
    ),
    Shopping(
        stringResId = R.string.ledger_category_shopping,
        iconResId = R.drawable.ic_ledger_category_shopping,
    ),
    HealthMedical(
        stringResId = R.string.ledger_category_health_medical,
        iconResId = R.drawable.ic_ledger_category_health_medical,
    ),
    EducationSelfDevelopment(
        stringResId = R.string.ledger_category_education_self_development,
        iconResId = R.drawable.ic_ledger_category_education_self_development,
    ),
    LeisureHobby(
        stringResId = R.string.ledger_category_leisure_hobby,
        iconResId = R.drawable.ic_ledger_category_leisure_hobby,
    ),
    SavingFinance(
        stringResId = R.string.ledger_category_saving_finance,
        iconResId = R.drawable.ic_ledger_category_saving_finance,
    ),
    Salary(
        stringResId = R.string.ledger_category_salary,
        iconResId = R.drawable.ic_ledger_category_salary,
    ),
    SideIncome(
        stringResId = R.string.ledger_category_side_income,
        iconResId = R.drawable.ic_ledger_category_side_income,
    ),
    Bonus(
        stringResId = R.string.ledger_category_bonus,
        iconResId = R.drawable.ic_ledger_category_bonus,
    ),
    Allowance(
        stringResId = R.string.ledger_category_allowance,
        iconResId = R.drawable.ic_ledger_category_allowance,
    ),
    PartTimeIncome(
        stringResId = R.string.ledger_category_part_time_income,
        iconResId = R.drawable.ic_ledger_category_part_time_income,
    ),
    FinancialIncome(
        stringResId = R.string.ledger_category_financial_income,
        iconResId = R.drawable.ic_ledger_category_financial_income,
    ),
    SplitBill(
        stringResId = R.string.ledger_category_split_bill,
        iconResId = R.drawable.ic_ledger_category_split_bill,
    ),
    Transfer(
        stringResId = R.string.ledger_category_transfer,
        iconResId = R.drawable.ic_ledger_category_transfer,
    ),
    Other(
        stringResId = R.string.ledger_category_other,
        iconResId = R.drawable.ic_ledger_category_other,
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