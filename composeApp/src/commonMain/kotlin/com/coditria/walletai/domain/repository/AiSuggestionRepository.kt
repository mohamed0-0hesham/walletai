package com.coditria.walletai.domain.repository

import com.coditria.walletai.domain.model.AiSuggestion

interface AiSuggestionRepository {
    suspend fun suggestionFor(utterance: String): AiSuggestion
}
