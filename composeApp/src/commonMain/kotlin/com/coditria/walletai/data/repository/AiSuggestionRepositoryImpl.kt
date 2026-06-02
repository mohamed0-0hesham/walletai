package com.coditria.walletai.data.repository

import com.coditria.walletai.data.source.local.AiSuggestionLocalDataSource
import com.coditria.walletai.domain.model.AiSuggestion
import com.coditria.walletai.domain.model.TransactionCategory
import com.coditria.walletai.domain.repository.AiSuggestionRepository

class AiSuggestionRepositoryImpl(
    private val local: AiSuggestionLocalDataSource,
) : AiSuggestionRepository {

    override suspend fun suggestionFor(utterance: String): AiSuggestion {
        val row = local.suggestionFor(utterance)
        return AiSuggestion(
            parsedText = row.parsedText,
            amount = row.amountMinor.toString(),
            currency = row.currency,
            category = TransactionCategory.entries.firstOrNull { it.name == row.categoryKey }
                ?: TransactionCategory.Other,
            categoryLabel = row.categoryLabel,
            date = row.date,
            account = row.account,
            confidence = row.confidence,
        )
    }
}
