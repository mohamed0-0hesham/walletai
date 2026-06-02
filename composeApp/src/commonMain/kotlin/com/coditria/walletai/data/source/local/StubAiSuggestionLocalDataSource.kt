package com.coditria.walletai.data.source.local

/**
 * No-op stub for AI suggestion lookups. Returns an empty row so the UI shows
 * its waiting state until a real remote / on-device inference is wired up.
 * Replace this with a network or ML implementation without touching consumers.
 *
 * Lives next to [AiSuggestionLocalDataSource] (rather than under storage
 * adapters like SQLDelight) because suggestions aren't persisted data —
 * they're inference output.
 */
class StubAiSuggestionLocalDataSource : AiSuggestionLocalDataSource {

    override suspend fun suggestionFor(utterance: String): AiSuggestionRow = AiSuggestionRow(
        parsedText = utterance,
        amountMinor = 0L,
        currency = "",
        categoryKey = "",
        categoryLabel = "",
        date = "",
        account = "",
        confidence = 0f,
    )
}
