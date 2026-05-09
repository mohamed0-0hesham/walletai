package com.coditria.walletai.data.source.local.sqldelight

import com.coditria.walletai.data.db.WalletDatabase
import com.coditria.walletai.data.entity.TransactionEntity
import com.coditria.walletai.data.source.local.TransactionLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqlDelightTransactionLocalDataSource(
    private val db: WalletDatabase,
) : TransactionLocalDataSource {

    override suspend fun recent(limit: Int): List<TransactionEntity> = withContext(Dispatchers.Default) {
        db.walletTransactionQueries.selectRecent(limit.toLong()).executeAsList().map { row ->
            TransactionEntity(
                id = row.id,
                title = row.title,
                subtitle = row.subtitle,
                amountMinor = row.amountMinor,
                currency = row.currency,
                type = row.type,
                category = row.category,
                createdAtEpochMs = row.createdAtEpochMs,
            )
        }
    }

    override suspend fun insert(entity: TransactionEntity) {
        withContext(Dispatchers.Default) {
            db.walletTransactionQueries.upsert(
                id = entity.id,
                title = entity.title,
                subtitle = entity.subtitle,
                amountMinor = entity.amountMinor,
                currency = entity.currency,
                type = entity.type,
                category = entity.category,
                createdAtEpochMs = entity.createdAtEpochMs,
            )
        }
    }
}
