package com.coditria.walletai.data.source.local.sqldelight

import com.coditria.walletai.data.db.WalletDatabase
import com.coditria.walletai.data.entity.BalanceSummaryEntity
import com.coditria.walletai.data.source.local.BalanceLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqlDelightBalanceLocalDataSource(
    private val db: WalletDatabase,
) : BalanceLocalDataSource {

    override suspend fun summary(): BalanceSummaryEntity = withContext(Dispatchers.Default) {
        val row = db.balanceQueries.selectSummary().executeAsOne()
        BalanceSummaryEntity(
            totalMinor = row.totalMinor,
            incomeMinor = row.incomeMinor,
            expensesMinor = row.expensesMinor,
            netDeltaMinor = row.netDeltaMinor,
            currency = row.currency,
        )
    }
}
