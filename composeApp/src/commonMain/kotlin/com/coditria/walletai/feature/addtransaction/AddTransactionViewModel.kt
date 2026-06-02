package com.coditria.walletai.feature.addtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.coditria.walletai.domain.model.AiSuggestion
import com.coditria.walletai.domain.model.Money
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.TransactionCategory
import com.coditria.walletai.domain.model.TransactionType
import com.coditria.walletai.domain.repository.AiSuggestionRepository
import com.coditria.walletai.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class AddTransactionUiState(
    val type: TransactionType = TransactionType.Expense,
    val utterance: String = "صرفت 35 جنيه فطار من بتاع الكشري",
    val suggestion: AiSuggestion = EmptySuggestion,
    val saving: Boolean = false,
)

private val EmptySuggestion = AiSuggestion(
    parsedText = "",
    amount = "0",
    currency = "ج.م",
    category = TransactionCategory.Other,
    categoryLabel = "",
    date = "",
    account = "",
    confidence = 0f,
)

class AddTransactionViewModel(
    private val aiSuggestionRepository: AiSuggestionRepository,
    private val transactionRepository: TransactionRepository,
    private val scope: CoroutineScope,
) {
    var state: AddTransactionUiState by mutableStateOf(AddTransactionUiState())
        private set

    init {
        scope.launch {
            state = state.copy(suggestion = aiSuggestionRepository.suggestionFor(state.utterance))
        }
    }

    fun onTypeChange(type: TransactionType) {
        state = state.copy(type = type)
    }

    fun onUtteranceChange(text: String) {
        state = state.copy(utterance = text)
        scope.launch {
            state = state.copy(suggestion = aiSuggestionRepository.suggestionFor(text))
        }
    }

    fun save(onComplete: () -> Unit) {
        val suggestion = state.suggestion
        scope.launch {
            state = state.copy(saving = true)
            transactionRepository.add(
                Transaction(
                    id = "tx-" + suggestion.parsedText.hashCode().toString(),
                    title = suggestion.parsedText.take(40),
                    subtitle = suggestion.date,
                    amount = Money(
                        amount = suggestion.amount.toLongOrNull() ?: 0L,
                        currency = suggestion.currency,
                    ),
                    type = state.type,
                    category = suggestion.category,
                ),
            )
            state = state.copy(saving = false)
            onComplete()
        }
    }
}
