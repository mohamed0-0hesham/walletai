package com.coditria.walletai.domain.repository

import com.coditria.walletai.domain.model.AiInsight
import com.coditria.walletai.domain.model.AiSuggestion
import com.coditria.walletai.domain.model.BalanceSummary
import com.coditria.walletai.domain.model.CategoryBreakdown
import com.coditria.walletai.domain.model.FinancialHealth
import com.coditria.walletai.domain.model.HeatmapMonth
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.InstallmentSummary
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.User

/**
 * Single read-only seam for the demo data. SOLID: each screen depends on
 * the abstraction, not the in-memory fake. Replace with a network/db-backed
 * implementation without touching feature code.
 */
interface WalletRepository {
    fun currentUser(): User
    fun balanceSummary(): BalanceSummary
    fun recentTransactions(): List<Transaction>
    fun upcomingInstallments(): List<Installment>
    fun allInstallments(): List<Installment>
    fun installmentSummary(): InstallmentSummary
    fun heatmap(): List<HeatmapMonth>
    fun expenseBreakdown(): List<CategoryBreakdown>
    fun financialHealth(): FinancialHealth
    fun aiInsights(): List<AiInsight>
    fun aiSuggestionForUtterance(utterance: String): AiSuggestion
}
