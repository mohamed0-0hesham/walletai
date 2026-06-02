package com.coditria.walletai.data.entity

/**
 * Storage-shaped data classes. They mirror the eventual SQL/Realm schema:
 * flat fields, primitive types where possible, no compose / domain coupling.
 *
 * Mapping to/from [com.coditria.walletai.domain.model] happens in
 * [com.coditria.walletai.data.mapper] so the domain layer never touches
 * an entity directly.
 */

data class UserEntity(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatarInitial: String,
    val tier: String,
)

data class TransactionEntity(
    val id: String,
    val title: String,
    val subtitle: String,
    val amountMinor: Long,
    val currency: String,
    val type: String, // "expense" | "income" | "transfer"
    val category: String, // matches TransactionCategory.name
    val createdAtEpochMs: Long,
)

data class InstallmentEntity(
    val id: String,
    val name: String,
    val provider: String,
    val totalMonths: Int,
    val paidMonths: Int,
    val monthlyAmountMinor: Long,
    val currency: String,
    val nextDueLabel: String,
    val isWarning: Boolean,
    val style: String, // "brand" | "accent"
)

data class InstallmentSummaryEntity(
    val totalRemainingMinor: Long,
    val monthlyMinor: Long,
    val currency: String,
    val remainingMonths: Int,
)

data class BalanceSummaryEntity(
    val totalMinor: Long,
    val incomeMinor: Long,
    val expensesMinor: Long,
    val netDeltaMinor: Long,
    val currency: String,
)

data class HeatmapMonthEntity(
    val year: Int,
    val monthIndex: Int, // 1..12
    val label: String,
    val pressureLevel: Int,
    val totalMinor: Long,
    val currency: String,
)

data class CategoryBreakdownEntity(
    val name: String,
    val percent: Int,
    val gradientStart: Long,
    val gradientEnd: Long,
)

data class FinancialHealthEntity(
    val score: Int,
    val maxScore: Int,
    val verdict: String,
    val description: String,
)

data class AiInsightEntity(
    val title: String,
    val description: String,
    val tone: String, // "brand" | "good" | "bad"
    val highlight: Boolean,
)
