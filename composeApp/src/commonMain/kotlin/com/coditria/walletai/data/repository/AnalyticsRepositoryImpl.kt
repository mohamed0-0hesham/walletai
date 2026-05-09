package com.coditria.walletai.data.repository

import com.coditria.walletai.data.mapper.toDomain
import com.coditria.walletai.data.source.local.AnalyticsLocalDataSource
import com.coditria.walletai.domain.model.AiInsight
import com.coditria.walletai.domain.model.CategoryBreakdown
import com.coditria.walletai.domain.model.FinancialHealth
import com.coditria.walletai.domain.repository.AnalyticsRepository

class AnalyticsRepositoryImpl(
    private val local: AnalyticsLocalDataSource,
) : AnalyticsRepository {

    override suspend fun expenseBreakdown(): List<CategoryBreakdown> =
        local.expenseBreakdown().map { it.toDomain() }

    override suspend fun financialHealth(): FinancialHealth =
        local.financialHealth().toDomain()

    override suspend fun aiInsights(): List<AiInsight> =
        local.aiInsights().map { it.toDomain() }
}
