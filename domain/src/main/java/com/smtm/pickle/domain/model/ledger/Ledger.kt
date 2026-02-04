package com.smtm.pickle.domain.model.ledger

import java.time.LocalDate

@JvmInline
value class LedgerId(val value: Long)

@JvmInline
value class Money(val value: Long) {
    init {
        require(value >= 0) { "Money must be >= 0" }
    }
}

data class Ledger(
    val id: LedgerId,
    val type: LedgerType,
    val amount: Money,
    val category: LedgerCategory,
    val description: String,
    val occurredOn: LocalDate,
    val paymentMethod: PaymentMethod,
    val memo: String? = null,
)

data class LedgerSummary(
    val totalIncome: Long,
    val totalExpense: Long,
)

enum class LedgerType { Income, Expense }

enum class PaymentMethod {
    BankTransfer, CreditCard, Cash, DebitCard
}

enum class LedgerCategory {
    // 지출
    Food,
    Transport,
    Housing,
    Shopping,
    HealthMedical,
    EducationSelfDevelopment,
    LeisureHobby,
    SavingFinance,

    // 수입
    Salary,
    SideIncome,
    Bonus,
    Allowance,
    PartTimeIncome,
    FinancialIncome,
    SplitBill,
    Transfer,

    // 기타
    Other,
}

fun List<Ledger>.summarize(): LedgerSummary = LedgerSummary(
    totalIncome = filter { it.type == LedgerType.Income }.sumOf { it.amount.value },
    totalExpense = filter { it.type == LedgerType.Expense }.sumOf { it.amount.value }
)