package com.coditria.walletai.data.di

import com.coditria.walletai.data.db.DatabaseDriverFactory
import com.coditria.walletai.data.db.WalletDatabase
import com.coditria.walletai.data.preview.PreviewFakes
import com.coditria.walletai.data.repository.AiSuggestionRepositoryImpl
import com.coditria.walletai.data.repository.AnalyticsRepositoryImpl
import com.coditria.walletai.data.repository.BalanceRepositoryImpl
import com.coditria.walletai.data.repository.InstallmentRepositoryImpl
import com.coditria.walletai.data.repository.TransactionRepositoryImpl
import com.coditria.walletai.data.repository.UserRepositoryImpl
import com.coditria.walletai.data.source.local.AiSuggestionLocalDataSource
import com.coditria.walletai.data.source.local.AnalyticsLocalDataSource
import com.coditria.walletai.data.source.local.BalanceLocalDataSource
import com.coditria.walletai.data.source.local.InstallmentLocalDataSource
import com.coditria.walletai.data.source.local.StubAiSuggestionLocalDataSource
import com.coditria.walletai.data.source.local.TransactionLocalDataSource
import com.coditria.walletai.data.source.local.UserLocalDataSource
import com.coditria.walletai.data.source.local.sqldelight.SqlDelightAnalyticsLocalDataSource
import com.coditria.walletai.data.source.local.sqldelight.SqlDelightBalanceLocalDataSource
import com.coditria.walletai.data.source.local.sqldelight.SqlDelightInstallmentLocalDataSource
import com.coditria.walletai.data.source.local.sqldelight.SqlDelightTransactionLocalDataSource
import com.coditria.walletai.data.source.local.sqldelight.SqlDelightUserLocalDataSource
import com.coditria.walletai.domain.repository.AiSuggestionRepository
import com.coditria.walletai.domain.repository.AnalyticsRepository
import com.coditria.walletai.domain.repository.BalanceRepository
import com.coditria.walletai.domain.repository.InstallmentRepository
import com.coditria.walletai.domain.repository.TransactionRepository
import com.coditria.walletai.domain.repository.UserRepository

/**
 * Manual DI graph for the data layer. Builds the dependency tree once and
 * exposes the domain-level repository contracts so feature code never depends
 * on a concrete data source.
 *
 * Production: [sqlDelight] — opens the SQLite database and routes every
 * persistent repository through SQLDelight. The database starts empty;
 * onboarding / sync flows are responsible for populating it. AI suggestions
 * are routed through [StubAiSuggestionLocalDataSource] until a real inference
 * path is wired in.
 *
 * Tooling: [previewFakes] — returns a graph backed by [PreviewFakes] for IDE
 * previews and unit tests, without spinning up a SQLite driver.
 */
class DataGraph private constructor(
    val userRepository: UserRepository,
    val balanceRepository: BalanceRepository,
    val transactionRepository: TransactionRepository,
    val installmentRepository: InstallmentRepository,
    val analyticsRepository: AnalyticsRepository,
    val aiSuggestionRepository: AiSuggestionRepository,
) {
    companion object {

        suspend fun sqlDelight(driverFactory: DatabaseDriverFactory): DataGraph {
            val db = WalletDatabase(driverFactory.create())
            return build(
                userLocal = SqlDelightUserLocalDataSource(db),
                balanceLocal = SqlDelightBalanceLocalDataSource(db),
                transactionLocal = SqlDelightTransactionLocalDataSource(db),
                installmentLocal = SqlDelightInstallmentLocalDataSource(db),
                analyticsLocal = SqlDelightAnalyticsLocalDataSource(db),
                aiSuggestionLocal = StubAiSuggestionLocalDataSource(),
            )
        }

        /**
         * IDE-preview / test wiring. Skips storage entirely and returns a
         * [DataGraph] backed by hard-coded [PreviewFakes]. No driver needed.
         */
        fun previewFakes(): DataGraph = DataGraph(
            userRepository = PreviewFakes.userRepository(),
            balanceRepository = PreviewFakes.balanceRepository(),
            transactionRepository = PreviewFakes.transactionRepository(),
            installmentRepository = PreviewFakes.installmentRepository(),
            analyticsRepository = PreviewFakes.analyticsRepository(),
            aiSuggestionRepository = PreviewFakes.aiSuggestionRepository(),
        )

        /**
         * Generic factory — pass any combination of [LocalDataSource] implementations
         * to mix and match (e.g. SqlDelight for persisted entities + a remote AI source).
         */
        fun build(
            userLocal: UserLocalDataSource,
            balanceLocal: BalanceLocalDataSource,
            transactionLocal: TransactionLocalDataSource,
            installmentLocal: InstallmentLocalDataSource,
            analyticsLocal: AnalyticsLocalDataSource,
            aiSuggestionLocal: AiSuggestionLocalDataSource,
        ): DataGraph = DataGraph(
            userRepository = UserRepositoryImpl(userLocal),
            balanceRepository = BalanceRepositoryImpl(balanceLocal),
            transactionRepository = TransactionRepositoryImpl(transactionLocal),
            installmentRepository = InstallmentRepositoryImpl(installmentLocal),
            analyticsRepository = AnalyticsRepositoryImpl(analyticsLocal),
            aiSuggestionRepository = AiSuggestionRepositoryImpl(aiSuggestionLocal),
        )
    }
}
