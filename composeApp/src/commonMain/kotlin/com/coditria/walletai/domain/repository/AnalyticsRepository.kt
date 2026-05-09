package com.coditria.walletai.domain.repository

import com.coditria.walletai.domain.model.AiInsight
import com.coditria.walletai.domain.model.CategoryBreakdown
import com.coditria.walletai.domain.model.FinancialHealth

interface AnalyticsRepository {
    suspend fun expenseBreakdown(): List<CategoryBreakdown>
    suspend fun financialHealth(): FinancialHealth
    suspend fun aiInsights(): List<AiInsight>
}
