package com.coditria.walletai.data.mapper

import com.coditria.walletai.data.entity.AiInsightEntity
import com.coditria.walletai.data.entity.BalanceSummaryEntity
import com.coditria.walletai.data.entity.CategoryBreakdownEntity
import com.coditria.walletai.data.entity.FinancialHealthEntity
import com.coditria.walletai.data.entity.HeatmapMonthEntity
import com.coditria.walletai.data.entity.InstallmentEntity
import com.coditria.walletai.data.entity.InstallmentSummaryEntity
import com.coditria.walletai.data.entity.TransactionEntity
import com.coditria.walletai.data.entity.UserEntity
import com.coditria.walletai.domain.model.AiInsight
import com.coditria.walletai.domain.model.BalanceSummary
import com.coditria.walletai.domain.model.CategoryBreakdown
import com.coditria.walletai.domain.model.FinancialHealth
import com.coditria.walletai.domain.model.HeatmapMonth
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.InstallmentSummary
import com.coditria.walletai.domain.model.InstallmentVisualStyle
import com.coditria.walletai.domain.model.InsightTone
import com.coditria.walletai.domain.model.Money
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.TransactionCategory
import com.coditria.walletai.domain.model.TransactionType
import com.coditria.walletai.domain.model.User

/**
 * One central place to translate between storage rows ([com.coditria.walletai.data.entity])
 * and pure domain models ([com.coditria.walletai.domain.model]). Keeping
 * conversion in one file makes schema changes localised.
 */

fun UserEntity.toDomain(): User = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    avatarInitial = avatarInitial,
    tier = tier,
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    avatarInitial = avatarInitial,
    tier = tier,
)

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    title = title,
    subtitle = subtitle,
    amount = Money(amountMinor, currency),
    type = type.toTransactionType(),
    category = category.toTransactionCategory(),
)

fun Transaction.toEntity(createdAtEpochMs: Long = 0L): TransactionEntity = TransactionEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    amountMinor = amount.amount,
    currency = amount.currency,
    type = type.persisted,
    category = category.name,
    createdAtEpochMs = createdAtEpochMs,
)

private val TransactionType.persisted: String
    get() = when (this) {
        TransactionType.Expense -> "expense"
        TransactionType.Income -> "income"
        TransactionType.Transfer -> "transfer"
    }

private fun String.toTransactionType(): TransactionType = when (this) {
    "income" -> TransactionType.Income
    "transfer" -> TransactionType.Transfer
    else -> TransactionType.Expense
}

private fun String.toTransactionCategory(): TransactionCategory =
    TransactionCategory.entries.firstOrNull { it.name == this } ?: TransactionCategory.Other

fun InstallmentEntity.toDomain(): Installment = Installment(
    id = id,
    name = name,
    provider = provider,
    totalMonths = totalMonths,
    paidMonths = paidMonths,
    monthlyAmount = Money(monthlyAmountMinor, currency),
    nextDueLabel = nextDueLabel,
    isWarning = isWarning,
    style = if (style == "accent") InstallmentVisualStyle.Accent else InstallmentVisualStyle.Brand,
)

fun InstallmentSummaryEntity.toDomain(): InstallmentSummary = InstallmentSummary(
    totalRemaining = Money(totalRemainingMinor, currency),
    monthly = Money(monthlyMinor, currency),
    remainingMonths = remainingMonths,
)

fun BalanceSummaryEntity.toDomain(): BalanceSummary = BalanceSummary(
    total = Money(totalMinor, currency),
    income = Money(incomeMinor, currency),
    expenses = Money(expensesMinor, currency),
    netDelta = Money(netDeltaMinor, currency),
)

fun HeatmapMonthEntity.toDomain(): HeatmapMonth = HeatmapMonth(
    label = label,
    amountLabel = totalMinor.toString(),
    pressure = pressureLevel,
)

fun CategoryBreakdownEntity.toDomain(): CategoryBreakdown = CategoryBreakdown(
    name = name,
    percent = percent,
    gradientStart = gradientStart,
    gradientEnd = gradientEnd,
)

fun FinancialHealthEntity.toDomain(): FinancialHealth = FinancialHealth(
    score = score,
    maxScore = maxScore,
    verdict = verdict,
    description = description,
)

fun AiInsightEntity.toDomain(): AiInsight = AiInsight(
    title = title,
    description = description,
    tone = when (tone) {
        "good" -> InsightTone.Good
        "bad" -> InsightTone.Bad
        else -> InsightTone.Brand
    },
    highlight = highlight,
)
