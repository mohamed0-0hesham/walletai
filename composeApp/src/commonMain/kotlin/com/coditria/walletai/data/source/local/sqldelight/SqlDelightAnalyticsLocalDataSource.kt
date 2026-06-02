package com.coditria.walletai.data.source.local.sqldelight

import com.coditria.walletai.data.db.WalletDatabase
import com.coditria.walletai.data.entity.AiInsightEntity
import com.coditria.walletai.data.entity.CategoryBreakdownEntity
import com.coditria.walletai.data.entity.FinancialHealthEntity
import com.coditria.walletai.data.source.local.AnalyticsLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqlDelightAnalyticsLocalDataSource(
    private val db: WalletDatabase,
) : AnalyticsLocalDataSource {

    override suspend fun expenseBreakdown(): List<CategoryBreakdownEntity> = withContext(Dispatchers.Default) {
        db.analyticsQueries.selectBreakdown().executeAsList().map { row ->
            CategoryBreakdownEntity(
                name = row.name,
                percent = row.percent.toInt(),
                gradientStart = row.gradientStart,
                gradientEnd = row.gradientEnd,
            )
        }
    }

    override suspend fun financialHealth(): FinancialHealthEntity = withContext(Dispatchers.Default) {
        val row = db.analyticsQueries.selectHealth().executeAsOneOrNull()
            ?: return@withContext EmptyHealth
        FinancialHealthEntity(
            score = row.score.toInt(),
            maxScore = row.maxScore.toInt(),
            verdict = row.verdict,
            description = row.description,
        )
    }

    private companion object {
        val EmptyHealth = FinancialHealthEntity(
            score = 0, maxScore = 0, verdict = "", description = "",
        )
    }

    override suspend fun aiInsights(): List<AiInsightEntity> = withContext(Dispatchers.Default) {
        db.analyticsQueries.selectInsights().executeAsList().map { row ->
            AiInsightEntity(
                title = row.title,
                description = row.description,
                tone = row.tone,
                highlight = row.highlight == 1L,
            )
        }
    }
}
