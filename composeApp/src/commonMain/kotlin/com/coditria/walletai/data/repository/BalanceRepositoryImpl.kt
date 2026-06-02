package com.coditria.walletai.data.repository

import com.coditria.walletai.data.mapper.toDomain
import com.coditria.walletai.data.source.local.BalanceLocalDataSource
import com.coditria.walletai.domain.model.BalanceSummary
import com.coditria.walletai.domain.repository.BalanceRepository

class BalanceRepositoryImpl(
    private val local: BalanceLocalDataSource,
) : BalanceRepository {
    override suspend fun summary(): BalanceSummary = local.summary().toDomain()
}
