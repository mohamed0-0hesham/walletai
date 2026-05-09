package com.coditria.walletai.data.source.local

import com.coditria.walletai.data.entity.AiInsightEntity
import com.coditria.walletai.data.entity.BalanceSummaryEntity
import com.coditria.walletai.data.entity.CategoryBreakdownEntity
import com.coditria.walletai.data.entity.FinancialHealthEntity
import com.coditria.walletai.data.entity.HeatmapMonthEntity
import com.coditria.walletai.data.entity.InstallmentEntity
import com.coditria.walletai.data.entity.InstallmentSummaryEntity
import com.coditria.walletai.data.entity.TransactionEntity
import com.coditria.walletai.data.entity.UserEntity

/**
 * Low-level CRUD-style contracts that the data layer depends on. The repository
 * implementations talk to these — never directly to a database driver. Swap the
 * implementation (in-memory / SQLDelight / Room / Realm / network cache) without
 * touching the domain layer or the repositories' callers.
 *
 * Every method is `suspend` so a real driver (which is async) drops in cleanly.
 */

interface UserLocalDataSource {
    suspend fun currentUser(): UserEntity
}

interface BalanceLocalDataSource {
    suspend fun summary(): BalanceSummaryEntity
}

interface TransactionLocalDataSource {
    suspend fun recent(limit: Int): List<TransactionEntity>
    suspend fun insert(entity: TransactionEntity)
}

interface InstallmentLocalDataSource {
    suspend fun upcoming(limit: Int): List<InstallmentEntity>
    suspend fun all(): List<InstallmentEntity>
    suspend fun summary(): InstallmentSummaryEntity
    suspend fun pressureHeatmap(year: Int): List<HeatmapMonthEntity>
}

interface AnalyticsLocalDataSource {
    suspend fun expenseBreakdown(): List<CategoryBreakdownEntity>
    suspend fun financialHealth(): FinancialHealthEntity
    suspend fun aiInsights(): List<AiInsightEntity>
}

interface AiSuggestionLocalDataSource {
    /**
     * Looks up a parsed suggestion for the utterance. Concrete impls may use
     * a remote AI model, an on-device model, or a static fallback.
     */
    suspend fun suggestionFor(utterance: String): AiSuggestionRow
}

/**
 * Wire-format row for the AI suggestion. Mirrors what a remote AI service would
 * return. The repository converts it to the domain [com.coditria.walletai.domain.model.AiSuggestion].
 */
data class AiSuggestionRow(
    val parsedText: String,
    val amountMinor: Long,
    val currency: String,
    val categoryKey: String,
    val categoryLabel: String,
    val date: String,
    val account: String,
    val confidence: Float,
)
