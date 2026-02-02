package com.smtm.pickle.presentation.common.utils

import java.text.DecimalFormat

private val moneyFormat = DecimalFormat("#,###")

fun Int.toMoneyFormat(): String = moneyFormat.format(this)

fun Long.toMoneyFormat(): String = moneyFormat.format(this)