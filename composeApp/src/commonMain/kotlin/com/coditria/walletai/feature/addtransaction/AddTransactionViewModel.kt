package com.coditria.walletai.feature.addtransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.coditria.walletai.domain.model.AiSuggestion
import com.coditria.walletai.domain.model.TransactionType
import com.coditria.walletai.domain.repository.WalletRepository

data class AddTransactionUiState(
    val type: TransactionType = TransactionType.Expense,
    val utterance: String = "صرفت 35 جنيه فطار من بتاع الكشري",
    val suggestion: AiSuggestion,
)

class AddTransactionViewModel(private val repository: WalletRepository) {
    var state: AddTransactionUiState by mutableStateOf(
        AddTransactionUiState(
            suggestion = repository.aiSuggestionForUtterance("صرفت 35 جنيه فطار من بتاع الكشري"),
        ),
    )
        private set

    fun onTypeChange(type: TransactionType) { state = state.copy(type = type) }

    fun onUtteranceChange(text: String) {
        state = state.copy(
            utterance = text,
            suggestion = repository.aiSuggestionForUtterance(text),
        )
    }
}
