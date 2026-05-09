package com.coditria.walletai.domain.repository

import com.coditria.walletai.domain.model.HeatmapMonth
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.InstallmentSummary

interface InstallmentRepository {
    suspend fun upcoming(limit: Int = 2): List<Installment>
    suspend fun all(): List<Installment>
    suspend fun summary(): InstallmentSummary
    suspend fun pressureHeatmap(year: Int): List<HeatmapMonth>
}
