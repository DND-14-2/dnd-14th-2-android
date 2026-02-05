package com.smtm.pickle.data.source.remote.model.ledger

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RemoteLedgerType {
    @SerialName("INCOME")
    Income,

    @SerialName("EXPENSE")
    Expense,
}

@Serializable
enum class RemoteLedgerCategory {
    @SerialName("FOOD")
    Food,

    @SerialName("TRANSPORT")
    Transport,

    @SerialName("HOUSING")
    Housing,

    @SerialName("SHOPPING")
    Shopping,

    @SerialName("HEALTH_MEDICAL")
    HealthMedical,

    @SerialName("EDUCATION_SELF_DEVELOPMENT")
    EducationSelfDevelopment,

    @SerialName("LEISURE_HOBBY")
    LeisureHobby,

    @SerialName("SAVINGS_FINANCE")
    SavingFinance,

    @SerialName("SALARY")
    Salary,

    @SerialName("SIDE_INCOME")
    SideIncome,

    @SerialName("BONUS")
    Bonus,

    @SerialName("ALLOWANCE")
    Allowance,

    @SerialName("PART_TIME")
    PartTimeIncome,

    @SerialName("FINANCIAL_INCOME")
    FinancialIncome,

    @SerialName("DUTCH_PAY")
    SplitBill,

    @SerialName("TRANSFER")
    Transfer,

    @SerialName("OTHER")
    Other
}

@Serializable
enum class RemotePaymentMethod {
    @SerialName("BANK_TRANSFER")
    BankTransfer,

    @SerialName("CREDIT_CARD")
    CreditCard,

    @SerialName("CASH")
    Cash,

    @SerialName("DEBIT_CARD")
    DebitCard,
}
