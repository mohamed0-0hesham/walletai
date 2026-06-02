package com.coditria.walletai.domain.repository

import com.coditria.walletai.domain.model.BalanceSummary

interface BalanceRepository {
    suspend fun summary(): BalanceSummary
}
