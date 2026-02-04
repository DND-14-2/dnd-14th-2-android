package com.smtm.pickle.presentation.common.model.ledger

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

sealed class CategoryUiModel(
    @StringRes val stringResId: Int,
    @DrawableRes val iconResId: Int,
) {
    data object Food : CategoryUiModel(
        stringResId = R.string.ledger_category_food,
        iconResId = R.drawable.ic_ledger_category_food,
    )

    data object Transport : CategoryUiModel(
        stringResId = R.string.ledger_category_transport,
        iconResId = R.drawable.ic_ledger_category_transport,
    )

    data object Housing : CategoryUiModel(
        stringResId = R.string.ledger_category_housing,
        iconResId = R.drawable.ic_ledger_category_housing,
    )

    data object Shopping : CategoryUiModel(
        stringResId = R.string.ledger_category_shopping,
        iconResId = R.drawable.ic_ledger_category_shopping,
    )

    data object HealthMedical : CategoryUiModel(
        stringResId = R.string.ledger_category_health_medical,
        iconResId = R.drawable.ic_ledger_category_health_medical,
    )

    data object EducationSelfDevelopment : CategoryUiModel(
        stringResId = R.string.ledger_category_education_self_development,
        iconResId = R.drawable.ic_ledger_category_education_self_development,
    )

    data object LeisureHobby : CategoryUiModel(
        stringResId = R.string.ledger_category_leisure_hobby,
        iconResId = R.drawable.ic_ledger_category_leisure_hobby,
    )

    data object SavingFinance : CategoryUiModel(
        stringResId = R.string.ledger_category_saving_finance,
        iconResId = R.drawable.ic_ledger_category_saving_finance,
    )

    data object Salary : CategoryUiModel(
        stringResId = R.string.ledger_category_salary,
        iconResId = R.drawable.ic_ledger_category_salary,
    )

    data object SideIncome : CategoryUiModel(
        stringResId = R.string.ledger_category_side_income,
        iconResId = R.drawable.ic_ledger_category_side_income,
    )

    data object Bonus : CategoryUiModel(
        stringResId = R.string.ledger_category_bonus,
        iconResId = R.drawable.ic_ledger_category_bonus,
    )

    data object Allowance : CategoryUiModel(
        stringResId = R.string.ledger_category_allowance,
        iconResId = R.drawable.ic_ledger_category_allowance,
    )

    data object PartTimeIncome : CategoryUiModel(
        stringResId = R.string.ledger_category_part_time_income,
        iconResId = R.drawable.ic_ledger_category_part_time_income,
    )

    data object FinancialIncome : CategoryUiModel(
        stringResId = R.string.ledger_category_financial_income,
        iconResId = R.drawable.ic_ledger_category_financial_income,
    )

    data object SplitBill : CategoryUiModel(
        stringResId = R.string.ledger_category_split_bill,
        iconResId = R.drawable.ic_ledger_category_split_bill,
    )

    data object Transfer : CategoryUiModel(
        stringResId = R.string.ledger_category_transfer,
        iconResId = R.drawable.ic_ledger_category_transfer,
    )

    data object Other : CategoryUiModel(
        stringResId = R.string.ledger_category_other,
        iconResId = R.drawable.ic_ledger_category_other,
    )
}