package com.coditria.walletai.feature.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.coditria.walletai.domain.model.BalanceSummary
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.User
import com.coditria.walletai.domain.repository.WalletRepository

data class DashboardUiState(
    val user: User,
    val balance: BalanceSummary,
    val transactions: List<Transaction>,
    val upcomingInstallments: List<Installment>,
    val balanceVisible: Boolean = true,
    val chartTab: ChartRange = ChartRange.Month,
)

enum class ChartRange(val label: String) {
    Week("أسبوع"),
    Month("شهر"),
    Year("سنة"),
}

/**
 * Holds dashboard state. Depends only on the repository abstraction.
 */
class DashboardViewModel(repository: WalletRepository) {
    var state: DashboardUiState by mutableStateOf(
        DashboardUiState(
            user = repository.currentUser(),
            balance = repository.balanceSummary(),
            transactions = repository.recentTransactions(),
            upcomingInstallments = repository.upcomingInstallments(),
        ),
    )
        private set

    fun toggleBalanceVisibility() {
        state = state.copy(balanceVisible = !state.balanceVisible)
    }

    fun selectChartRange(range: ChartRange) {
        state = state.copy(chartTab = range)
    }
}
