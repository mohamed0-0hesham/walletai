package com.coditria.walletai.data.repository

import com.coditria.walletai.data.mapper.toDomain
import com.coditria.walletai.data.mapper.toEntity
import com.coditria.walletai.data.source.local.TransactionLocalDataSource
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val local: TransactionLocalDataSource,
) : TransactionRepository {

    override suspend fun recent(limit: Int): List<Transaction> =
        local.recent(limit).map { it.toDomain() }

    override suspend fun add(transaction: Transaction) {
        local.insert(transaction.toEntity())
    }
}
