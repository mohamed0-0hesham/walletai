package com.coditria.walletai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.AppPreferences
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.app.ProvideAppLocale
import com.coditria.walletai.app.rememberWalletStrings
import com.coditria.walletai.data.InMemoryWalletRepository
import com.coditria.walletai.feature.addtransaction.AddTransactionScreen
import com.coditria.walletai.feature.addtransaction.AddTransactionViewModel
import com.coditria.walletai.feature.auth.SignInScreen
import com.coditria.walletai.feature.auth.SignUpScreen
import com.coditria.walletai.feature.dashboard.DashboardScreen
import com.coditria.walletai.feature.dashboard.DashboardViewModel
import com.coditria.walletai.feature.installments.InstallmentsScreen
import com.coditria.walletai.feature.installments.InstallmentsViewModel
import com.coditria.walletai.feature.reports.ReportsScreen
import com.coditria.walletai.feature.reports.ReportsViewModel
import com.coditria.walletai.feature.settings.SettingsScreen
import com.coditria.walletai.feature.settings.SettingsViewModel
import com.coditria.walletai.feature.splash.SplashScreen
import com.coditria.walletai.feature.voice.VoiceScreen
import com.coditria.walletai.navigation.Route
import com.coditria.walletai.navigation.WalletNavHost
import com.coditria.walletai.navigation.rememberWalletNavController
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * App composition root. Wires repository, view models, navigation and
 * app-wide preferences (theme + locale) so the Settings screen can drive
 * the appearance and language of every other screen.
 */
@Composable
@Preview
fun App() {
    val prefs = remember { AppPreferences() }
    val direction = if (prefs.locale == AppLocale.Arabic) LayoutDirection.Rtl else LayoutDirection.Ltr

    WalletTheme(darkTheme = prefs.darkMode) {
        ProvideAppLocale(prefs.locale) {
            val strings = rememberWalletStrings()
            CompositionLocalProvider(
                LocalWalletStrings provides strings,
                LocalLayoutDirection provides direction,
            ) {
            val repository = remember { InMemoryWalletRepository() }
            val nav = rememberWalletNavController(initial = Route.Splash)

            val dashboardVm = remember { DashboardViewModel(repository) }
            val addTxVm = remember { AddTransactionViewModel(repository) }
            val installmentsVm = remember { InstallmentsViewModel(repository) }
            val reportsVm = remember { ReportsViewModel(repository) }
            val settingsVm = remember { SettingsViewModel(repository) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WalletTheme.colors.bg),
            ) {
                WalletNavHost(controller = nav) { route ->
                    when (route) {
                        Route.Splash -> SplashScreen(onContinue = { nav.replace(Route.SignIn) })
                        Route.SignIn -> SignInScreen(
                            onSignIn = { nav.replace(Route.Dashboard) },
                            onGoToSignUp = { nav.navigate(Route.SignUp) },
                            onBack = { nav.navigate(Route.Splash) },
                        )
                        Route.SignUp -> SignUpScreen(
                            onSignUp = { nav.replace(Route.Dashboard) },
                            onGoToSignIn = { nav.navigate(Route.SignIn) },
                            onBack = nav::back,
                        )
                        Route.Dashboard -> DashboardScreen(
                            viewModel = dashboardVm,
                            onAdd = { nav.navigate(Route.AddTransaction) },
                            onVoice = { nav.navigate(Route.Voice) },
                            onInstallments = { nav.navigate(Route.Installments) },
                            onReports = { nav.navigate(Route.Reports) },
                            onSettings = { nav.navigate(Route.Settings) },
                        )
                        Route.AddTransaction -> AddTransactionScreen(
                            viewModel = addTxVm,
                            onSave = { nav.replace(Route.Dashboard) },
                            onCancel = nav::back,
                        )
                        Route.Voice -> VoiceScreen(
                            onCancel = nav::back,
                            onConfirm = { nav.navigate(Route.AddTransaction) },
                        )
                        Route.Installments -> InstallmentsScreen(
                            viewModel = installmentsVm,
                            onBack = { nav.replace(Route.Dashboard) },
                            onAdd = { nav.navigate(Route.AddTransaction) },
                            onSelect = nav::navigate,
                        )
                        Route.Reports -> ReportsScreen(
                            viewModel = reportsVm,
                            onBack = { nav.replace(Route.Dashboard) },
                            onAdd = { nav.navigate(Route.AddTransaction) },
                            onSelect = nav::navigate,
                        )
                        Route.Settings -> SettingsScreen(
                            viewModel = settingsVm,
                            preferences = prefs,
                            onBack = { nav.replace(Route.Dashboard) },
                            onSignOut = { nav.replace(Route.SignIn) },
                            onAdd = { nav.navigate(Route.AddTransaction) },
                            onSelect = nav::navigate,
                        )
                    }
                }
            }
        }
        }
    }
}
