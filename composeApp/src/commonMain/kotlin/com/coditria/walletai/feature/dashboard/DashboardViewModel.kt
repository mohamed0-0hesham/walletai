package com.coditria.walletai.feature.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.coditria.walletai.domain.model.BalanceSummary
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.model.Money
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.User
import com.coditria.walletai.domain.repository.BalanceRepository
import com.coditria.walletai.domain.repository.InstallmentRepository
import com.coditria.walletai.domain.repository.TransactionRepository
import com.coditria.walletai.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DashboardUiState(
    val user: User,
    val balance: BalanceSummary,
    val transactions: List<Transaction>,
    val upcomingInstallments: List<Installment>,
    val balanceVisible: Boolean = true,
    val chartTab: ChartRange = ChartRange.Month,
)

enum class ChartRange { Week, Month, Year }

/**
 * Orchestrates dashboard data via four narrow repositories (ISP) instead of
 * one god repository. Loads on init via the supplied [scope].
 */
class DashboardViewModel(
    private val userRepository: UserRepository,
    private val balanceRepository: BalanceRepository,
    private val transactionRepository: TransactionRepository,
    private val installmentRepository: InstallmentRepository,
    scope: CoroutineScope,
) {
    var state: DashboardUiState by mutableStateOf(EmptyState)
        private set

    init {
        scope.launch {
            state = DashboardUiState(
                user = userRepository.currentUser(),
                balance = balanceRepository.summary(),
                transactions = transactionRepository.recent(),
                upcomingInstallments = installmentRepository.upcoming(),
            )
        }
    }

    fun toggleBalanceVisibility() {
        state = state.copy(balanceVisible = !state.balanceVisible)
    }

    fun selectChartRange(range: ChartRange) {
        state = state.copy(chartTab = range)
    }
}

private val EmptyState = DashboardUiState(
    user = User("", "", "", "", "", ""),
    balance = BalanceSummary(
        total = Money(0, ""),
        income = Money(0, ""),
        expenses = Money(0, ""),
        netDelta = Money(0, ""),
    ),
    transactions = emptyList(),
    upcomingInstallments = emptyList(),
)
