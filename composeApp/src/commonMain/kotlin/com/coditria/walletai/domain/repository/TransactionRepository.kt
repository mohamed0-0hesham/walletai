package com.coditria.walletai.domain.repository

import com.coditria.walletai.domain.model.Transaction

interface TransactionRepository {
    suspend fun recent(limit: Int = 5): List<Transaction>
    suspend fun add(transaction: Transaction)
}
