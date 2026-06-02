package com.coditria.walletai.domain.model

enum class TransactionType { Expense, Income, Transfer }

enum class TransactionCategory(val displayKey: String) {
    Transport("transport"),
    Food("food"),
    Income("income"),
    Bills("bills"),
    Installments("installments"),
    Other("other"),
}

data class Money(val amount: Long, val currency: String = "EGP")

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatarInitial: String,
    val tier: String = "Pro",
)

data class Transaction(
    val id: String,
    val title: String,
    val subtitle: String,
    val amount: Money,
    val type: TransactionType,
    val category: TransactionCategory,
)

data class BalanceSummary(
    val total: Money,
    val income: Money,
    val expenses: Money,
    val netDelta: Money,
)

data class Installment(
    val id: String,
    val name: String,
    val provider: String,
    val totalMonths: Int,
    val paidMonths: Int,
    val monthlyAmount: Money,
    val nextDueLabel: String,
    val isWarning: Boolean = false,
    val style: InstallmentVisualStyle = InstallmentVisualStyle.Brand,
)

enum class InstallmentVisualStyle { Brand, Accent }

data class InstallmentSummary(
    val totalRemaining: Money,
    val monthly: Money,
    val remainingMonths: Int,
)

data class HeatmapMonth(
    val label: String,
    val amountLabel: String,
    val pressure: Int, // 0..4
)

data class CategoryBreakdown(
    val name: String,
    val percent: Int,
    val gradientStart: Long,
    val gradientEnd: Long,
)

data class FinancialHealth(
    val score: Int,
    val maxScore: Int = 100,
    val verdict: String,
    val description: String,
)

enum class InsightTone { Brand, Bad, Good }

data class AiInsight(
    val title: String,
    val description: String,
    val tone: InsightTone,
    val highlight: Boolean = false,
)

data class AiSuggestion(
    val parsedText: String,
    val amount: String,
    val currency: String,
    val category: TransactionCategory,
    val categoryLabel: String,
    val date: String,
    val account: String,
    val confidence: Float, // 0..1
)
