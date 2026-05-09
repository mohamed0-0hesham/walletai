package com.coditria.walletai.data.source.local

/**
 * Static stub for AI suggestion lookups. Stands in until a real remote /
 * on-device inference is wired up — replace this with a network or ML
 * implementation without touching consumers.
 *
 * Lives next to [AiSuggestionLocalDataSource] (rather than under storage
 * adapters like SQLDelight) because suggestions aren't persisted data, they're
 * inference output.
 */
class StubAiSuggestionLocalDataSource : AiSuggestionLocalDataSource {

    override suspend fun suggestionFor(utterance: String): AiSuggestionRow {
        val text = utterance.ifBlank { "صرفت 35 جنيه فطار من بتاع الكشري" }
        return AiSuggestionRow(
            parsedText = text,
            amountMinor = 35,
            currency = "ج.م",
            categoryKey = "Food",
            categoryLabel = "أكل وشرب",
            date = "اليوم · 9:10 ص",
            account = "كاش",
            confidence = 0.92f,
        )
    }
}
