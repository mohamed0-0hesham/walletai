package com.coditria.walletai.data.repository

import com.coditria.walletai.data.mapper.toDomain
import com.coditria.walletai.data.source.local.InstallmentLocalDataSource
import com.coditria.walletai.domain.model.HeatmapMonth
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.InstallmentSummary
import com.coditria.walletai.domain.repository.InstallmentRepository

class InstallmentRepositoryImpl(
    private val local: InstallmentLocalDataSource,
) : InstallmentRepository {

    override suspend fun upcoming(limit: Int): List<Installment> =
        local.upcoming(limit).map { it.toDomain() }

    override suspend fun all(): List<Installment> =
        local.all().map { it.toDomain() }

    override suspend fun summary(): InstallmentSummary = local.summary().toDomain()

    override suspend fun pressureHeatmap(year: Int): List<HeatmapMonth> =
        local.pressureHeatmap(year).map { it.toDomain() }
}
