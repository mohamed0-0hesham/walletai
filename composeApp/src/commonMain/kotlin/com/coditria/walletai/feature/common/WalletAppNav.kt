package com.coditria.walletai.feature.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.navigation.Route
import com.walletai.core.designsystem.components.NavItem
import com.walletai.core.designsystem.components.WalletBottomNav
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Single source of truth for the bottom nav across Dashboard / Installments /
 * Reports / Settings. Hides the slot wiring so feature screens stay focused
 * on their own concerns.
 */
@Composable
fun WalletAppBottomNav(
    selected: Route,
    onSelect: (Route) -> Unit,
    onAddTransaction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ink = WalletTheme.colors.ink2
    val brand = WalletTheme.colors.brand
    val s = LocalWalletStrings.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
    ) {
        WalletBottomNav(
            items = listOf(
                NavItem("home", s.navHome) { sel ->
                    WalletIconHome(color = if (sel) brand else ink)
                },
                NavItem("transactions", s.navTransactions) { sel ->
                    WalletIconList(color = if (sel) brand else ink)
                },
            ),
            trailingItems = listOf(
                NavItem("reports", s.navReports) { sel ->
                    WalletIconChart(color = if (sel) brand else ink)
                },
                NavItem("settings", s.navSettings) { sel ->
                    WalletIconGear(color = if (sel) brand else ink)
                },
            ),
            selectedKey = when (selected) {
                Route.Dashboard -> "home"
                Route.Installments -> "transactions"
                Route.Reports -> "reports"
                Route.Settings -> "settings"
                else -> "home"
            },
            onSelect = { key ->
                when (key) {
                    "home" -> onSelect(Route.Dashboard)
                    "transactions" -> onSelect(Route.Installments)
                    "reports" -> onSelect(Route.Reports)
                    "settings" -> onSelect(Route.Settings)
                }
            },
            onFabClick = onAddTransaction,
            fabIcon = { WalletIconPlus(color = WalletTheme.colors.onBrand) },
        )
    }
}
