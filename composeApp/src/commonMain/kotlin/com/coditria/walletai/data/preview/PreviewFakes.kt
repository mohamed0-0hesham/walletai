package com.coditria.walletai.data.preview

import com.coditria.walletai.domain.model.AiInsight
import com.coditria.walletai.domain.model.AiSuggestion
import com.coditria.walletai.domain.model.BalanceSummary
import com.coditria.walletai.domain.model.CategoryBreakdown
import com.coditria.walletai.domain.model.FinancialHealth
import com.coditria.walletai.domain.model.HeatmapMonth
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.InstallmentSummary
import com.coditria.walletai.domain.model.Money
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.TransactionCategory
import com.coditria.walletai.domain.model.User
import com.coditria.walletai.domain.repository.AiSuggestionRepository
import com.coditria.walletai.domain.repository.AnalyticsRepository
import com.coditria.walletai.domain.repository.BalanceRepository
import com.coditria.walletai.domain.repository.InstallmentRepository
import com.coditria.walletai.domain.repository.TransactionRepository
import com.coditria.walletai.domain.repository.UserRepository

/**
 * Empty fake repositories for `@Preview` composables and unit tests. They
 * implement the domain contracts directly with zero-valued payloads so the
 * IDE renders the empty-state version of every screen without spinning up a
 * SQLite driver. Production code never touches this file —
 * [com.coditria.walletai.data.di.DataGraph.sqlDelight] is the production wire.
 */
internal object PreviewFakes {

    fun userRepository(): UserRepository = object : UserRepository {
        override suspend fun currentUser() = User(
            id = "", firstName = "", lastName = "",
            email = "", avatarInitial = "", tier = "",
        )
    }

    fun balanceRepository(): BalanceRepository = object : BalanceRepository {
        override suspend fun summary() = BalanceSummary(
            total = Money(0, ""),
            income = Money(0, ""),
            expenses = Money(0, ""),
            netDelta = Money(0, ""),
        )
    }

    fun transactionRepository(): TransactionRepository = object : TransactionRepository {
        override suspend fun recent(limit: Int): List<Transaction> = emptyList()
        override suspend fun add(transaction: Transaction) = Unit
    }

    fun installmentRepository(): InstallmentRepository = object : InstallmentRepository {
        override suspend fun upcoming(limit: Int): List<Installment> = emptyList()
        override suspend fun all(): List<Installment> = emptyList()
        override suspend fun summary() = InstallmentSummary(
            totalRemaining = Money(0, ""),
            monthly = Money(0, ""),
            remainingMonths = 0,
        )
        override suspend fun pressureHeatmap(year: Int): List<HeatmapMonth> = emptyList()
    }

    fun analyticsRepository(): AnalyticsRepository = object : AnalyticsRepository {
        override suspend fun expenseBreakdown(): List<CategoryBreakdown> = emptyList()
        override suspend fun financialHealth() = FinancialHealth(
            score = 0, maxScore = 0, verdict = "", description = "",
        )
        override suspend fun aiInsights(): List<AiInsight> = emptyList()
    }

    fun aiSuggestionRepository(): AiSuggestionRepository = object : AiSuggestionRepository {
        override suspend fun suggestionFor(utterance: String) = AiSuggestion(
            parsedText = "",
            amount = "0",
            currency = "",
            category = TransactionCategory.Other,
            categoryLabel = "",
            date = "",
            account = "",
            confidence = 0f,
        )
    }
}
