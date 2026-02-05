package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.local.database.entity.LedgerEntity
import com.smtm.pickle.data.source.remote.model.ledger.RemoteLedger
import com.smtm.pickle.data.source.remote.model.ledger.RemoteLedgerCategory
import com.smtm.pickle.data.source.remote.model.ledger.RemoteLedgerType
import com.smtm.pickle.data.source.remote.model.ledger.RemotePaymentMethod
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.Money
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import java.time.LocalDate

fun LedgerEntity.toDomain(): Ledger = Ledger(
    id = LedgerId(id),
    type = LedgerType.valueOf(type),
    amount = Money(amount),
    category = LedgerCategory.valueOf(category),
    description = description ?: category,
    occurredOn = LocalDate.ofEpochDay(occurredOn),
    paymentMethod = PaymentMethod.valueOf(paymentMethod),
    memo = memo
)

fun Ledger.toEntity(): LedgerEntity = LedgerEntity(
    id = id.value,
    type = type.name,
    amount = amount.value,
    category = category.name,
    description = description,
    occurredOn = occurredOn.toEpochDay(),
    paymentMethod = paymentMethod.name,
    memo = memo
)

fun RemoteLedger.toEntity() = LedgerEntity(
    id = ledgerId,
    amount = amount,
    type = type.name,
    category = category.name,
    paymentMethod = paymentMethod.name,
    description = description,
    occurredOn = LocalDate.parse(occurredOn).toEpochDay(),
    memo = memo,
)

fun RemoteLedger.toDomain() = Ledger(
    id = LedgerId(ledgerId),
    type = type.toDomain(),
    amount = Money(amount),
    category = category.toDomain(),
    description = description ?: category.name,
    occurredOn = LocalDate.parse(occurredOn),
    paymentMethod = paymentMethod.toDomain(),
    memo = memo,
)

fun RemoteLedgerType.toDomain(): LedgerType = when (this) {
    RemoteLedgerType.Income -> LedgerType.Income
    RemoteLedgerType.Expense -> LedgerType.Expense
}

fun RemoteLedgerCategory.toDomain(): LedgerCategory = when (this) {
    RemoteLedgerCategory.Food -> LedgerCategory.Food
    RemoteLedgerCategory.Transport -> LedgerCategory.Transport
    RemoteLedgerCategory.Housing -> LedgerCategory.Housing
    RemoteLedgerCategory.Shopping -> LedgerCategory.Shopping
    RemoteLedgerCategory.HealthMedical -> LedgerCategory.HealthMedical
    RemoteLedgerCategory.EducationSelfDevelopment -> LedgerCategory.EducationSelfDevelopment
    RemoteLedgerCategory.LeisureHobby -> LedgerCategory.LeisureHobby
    RemoteLedgerCategory.SavingFinance -> LedgerCategory.SavingFinance
    RemoteLedgerCategory.Salary -> LedgerCategory.Salary
    RemoteLedgerCategory.SideIncome -> LedgerCategory.SideIncome
    RemoteLedgerCategory.Bonus -> LedgerCategory.Bonus
    RemoteLedgerCategory.Allowance -> LedgerCategory.Allowance
    RemoteLedgerCategory.PartTimeIncome -> LedgerCategory.PartTimeIncome
    RemoteLedgerCategory.FinancialIncome -> LedgerCategory.FinancialIncome
    RemoteLedgerCategory.SplitBill -> LedgerCategory.SplitBill
    RemoteLedgerCategory.Transfer -> LedgerCategory.Transfer
    RemoteLedgerCategory.Other -> LedgerCategory.Other
}

fun RemotePaymentMethod.toDomain(): PaymentMethod = when (this) {
    RemotePaymentMethod.BankTransfer -> PaymentMethod.BankTransfer
    RemotePaymentMethod.CreditCard -> PaymentMethod.CreditCard
    RemotePaymentMethod.Cash -> PaymentMethod.Cash
    RemotePaymentMethod.DebitCard -> PaymentMethod.DebitCard
}
