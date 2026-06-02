package com.coditria.walletai.data.source.local.sqldelight

import com.coditria.walletai.data.db.WalletDatabase
import com.coditria.walletai.data.entity.HeatmapMonthEntity
import com.coditria.walletai.data.entity.InstallmentEntity
import com.coditria.walletai.data.entity.InstallmentSummaryEntity
import com.coditria.walletai.data.source.local.InstallmentLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqlDelightInstallmentLocalDataSource(
    private val db: WalletDatabase,
) : InstallmentLocalDataSource {

    override suspend fun upcoming(limit: Int): List<InstallmentEntity> = withContext(Dispatchers.Default) {
        db.installmentQueries.selectUpcoming(limit.toLong()).executeAsList().map { it.toEntity() }
    }

    override suspend fun all(): List<InstallmentEntity> = withContext(Dispatchers.Default) {
        db.installmentQueries.selectAll().executeAsList().map { it.toEntity() }
    }

    override suspend fun summary(): InstallmentSummaryEntity = withContext(Dispatchers.Default) {
        val row = db.installmentQueries.selectSummary().executeAsOneOrNull()
            ?: return@withContext EmptySummary
        InstallmentSummaryEntity(
            totalRemainingMinor = row.totalRemainingMinor,
            monthlyMinor = row.monthlyMinor,
            currency = row.currency,
            remainingMonths = row.remainingMonths.toInt(),
        )
    }

    private companion object {
        val EmptySummary = InstallmentSummaryEntity(
            totalRemainingMinor = 0L, monthlyMinor = 0L,
            currency = "", remainingMonths = 0,
        )
    }

    override suspend fun pressureHeatmap(year: Int): List<HeatmapMonthEntity> = withContext(Dispatchers.Default) {
        db.installmentQueries.selectHeatmap(year.toLong()).executeAsList().map { row ->
            HeatmapMonthEntity(
                year = row.year.toInt(),
                monthIndex = row.monthIndex.toInt(),
                label = row.label,
                pressureLevel = row.pressureLevel.toInt(),
                totalMinor = row.totalMinor,
                currency = row.currency,
            )
        }
    }

    private fun com.coditria.walletai.data.db.InstallmentEntity.toEntity(): InstallmentEntity =
        InstallmentEntity(
            id = id,
            name = name,
            provider = provider,
            totalMonths = totalMonths.toInt(),
            paidMonths = paidMonths.toInt(),
            monthlyAmountMinor = monthlyAmountMinor,
            currency = currency,
            nextDueLabel = nextDueLabel,
            isWarning = isWarning == 1L,
            style = style,
        )
}
