package com.coditria.walletai.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.data.InMemoryWalletRepository
import com.coditria.walletai.resources.Res
import com.coditria.walletai.resources.net_this_month
import org.jetbrains.compose.resources.stringResource
import com.coditria.walletai.feature.common.WalletPreviewHarness
import com.coditria.walletai.domain.model.Transaction
import com.coditria.walletai.domain.model.TransactionCategory
import com.coditria.walletai.domain.model.TransactionType
import com.coditria.walletai.feature.common.WalletAppBottomNav
import com.coditria.walletai.feature.common.WalletIconArrowDownLeft
import com.coditria.walletai.feature.common.WalletIconArrowUpRight
import com.coditria.walletai.feature.common.WalletIconBell
import com.coditria.walletai.feature.common.WalletIconCar
import com.coditria.walletai.feature.common.WalletIconCard
import com.coditria.walletai.feature.common.WalletIconCoffee
import com.coditria.walletai.feature.common.WalletIconEye
import com.coditria.walletai.feature.common.WalletIconMic
import com.coditria.walletai.feature.common.WalletIconPhone
import com.coditria.walletai.feature.common.WalletIconPlus
import com.coditria.walletai.feature.common.WalletIconUsers
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.coditria.walletai.navigation.Route
import com.walletai.core.designsystem.components.BalancePillStyle
import com.walletai.core.designsystem.components.IconBgStyle
import com.walletai.core.designsystem.components.ProgressStyle
import com.walletai.core.designsystem.components.WalletBalanceHero
import com.walletai.core.designsystem.components.WalletBalancePill
import com.coditria.walletai.designsystem.components.WalletGreetingBar
import com.walletai.core.designsystem.components.WalletInstallmentCard
import com.walletai.core.designsystem.components.WalletQuickAction
import com.coditria.walletai.designsystem.components.WalletSectionHeader
import com.walletai.core.designsystem.components.WalletTransactionRow
import com.walletai.core.designsystem.theme.WalletTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onAdd: () -> Unit,
    onVoice: () -> Unit,
    onInstallments: () -> Unit,
    onReports: () -> Unit,
    onSettings: () -> Unit,
) {
    val state = viewModel.state
    val s = LocalWalletStrings.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WalletTheme.colors.bg),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            WalletPhoneStatusBar()
            WalletGreetingBar(
                greeting = s.goodMorning,
                name = "${state.user.firstName} ${state.user.lastName}",
                avatarInitials = state.user.avatarInitial,
                onNotificationClick = {},
                hasNotifications = true,
                notificationIcon = { WalletIconBell(color = WalletTheme.colors.ink2) },
            )

            WalletBalanceHero(
                label = s.totalBalance,
                amount = if (state.balanceVisible) state.balance.total.amount.formatThousands() else "•••••",
                currency = state.balance.total.currency,
                onToggleVisibility = viewModel::toggleBalanceVisibility,
                visibilityIcon = { WalletIconEye(color = androidx.compose.ui.graphics.Color.White) },
                subtitle = stringResource(
                    Res.string.net_this_month,
                    state.balance.netDelta.amount.formatThousands(),
                    state.balance.netDelta.currency,
                ),
                modifier = Modifier.padding(horizontal = 18.dp),
                pillsContent = {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        WalletBalancePill(
                            label = s.income,
                            value = state.balance.income.amount.formatThousands(),
                            style = BalancePillStyle.Up,
                            icon = { WalletIconArrowUpRight(color = androidx.compose.ui.graphics.Color(0xFF85F7E0)) },
                            modifier = Modifier.weight(1f),
                        )
                        WalletBalancePill(
                            label = s.expenses,
                            value = state.balance.expenses.amount.formatThousands(),
                            style = BalancePillStyle.Down,
                            icon = { WalletIconArrowDownLeft(color = androidx.compose.ui.graphics.Color(0xFFFFB1B9)) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                },
            )

            Spacer(Modifier.height(22.dp))
            QuickActionRow(onAdd = onAdd, onVoice = onVoice)
            Spacer(Modifier.height(8.dp))

            WalletSectionHeader(
                title = s.analysis,
                moreText = s.more,
                onMoreClick = onReports,
            )
            Spacer(Modifier.height(6.dp))
            DashboardChartCard(
                selectedRange = state.chartTab,
                onSelect = viewModel::selectChartRange,
                modifier = Modifier.padding(horizontal = 18.dp),
            )

            Spacer(Modifier.height(18.dp))
            WalletSectionHeader(
                title = s.upcomingInstallments,
                moreText = s.viewAll,
                onMoreClick = onInstallments,
            )
            Spacer(Modifier.height(6.dp))
            state.upcomingInstallments.forEach { inst ->
                WalletInstallmentCard(
                    name = inst.name,
                    subtitle = inst.provider,
                    monthlyAmount = inst.monthlyAmount.amount.formatThousands(),
                    currency = inst.monthlyAmount.currency,
                    progress = inst.paidMonths.toFloat() / inst.totalMonths.toFloat(),
                    iconStyle = IconBgStyle.BrandGradient,
                    progressStyle = ProgressStyle.Brand,
                    onClick = onInstallments,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 5.dp),
                    icon = { WalletIconPhone(color = androidx.compose.ui.graphics.Color.White) },
                )
            }

            Spacer(Modifier.height(18.dp))
            WalletSectionHeader(title = s.recentTransactions, moreText = s.viewAll)
            Spacer(Modifier.height(6.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
                    .background(WalletTheme.colors.surface, WalletTheme.shapes.medium)
                    .padding(vertical = 6.dp, horizontal = 4.dp),
            ) {
                state.transactions.forEach { tx ->
                    DashboardTransaction(tx)
                }
            }

            Spacer(Modifier.height(110.dp))
        }

        WalletAppBottomNav(
            selected = Route.Dashboard,
            onSelect = { route ->
                when (route) {
                    Route.Installments -> onInstallments()
                    Route.Reports -> onReports()
                    Route.Settings -> onSettings()
                    else -> Unit
                }
            },
            onAddTransaction = onAdd,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun QuickActionRow(
    onAdd: () -> Unit,
    onVoice: () -> Unit,
) {
    val s = LocalWalletStrings.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        WalletQuickAction(
            label = s.add,
            onClick = onAdd,
            primary = true,
            modifier = Modifier.weight(1f),
        ) { WalletIconPlus(color = WalletTheme.colors.onBrand) }
        WalletQuickAction(
            label = s.voice,
            onClick = onVoice,
            modifier = Modifier.weight(1f),
        ) { WalletIconMic(color = WalletTheme.colors.brand) }
        WalletQuickAction(
            label = s.transfer,
            onClick = {},
            modifier = Modifier.weight(1f),
        ) { WalletIconArrowUpRight(size = 20.dp, color = WalletTheme.colors.ink2) }
        WalletQuickAction(
            label = s.goals,
            onClick = {},
            modifier = Modifier.weight(1f),
        ) { WalletIconCard(size = 20.dp, color = WalletTheme.colors.ink2) }
    }
}

@Composable
private fun DashboardTransaction(tx: Transaction) {
    val (style, iconColor) = when (tx.category) {
        TransactionCategory.Transport -> IconBgStyle.Brand to WalletTheme.colors.brand
        TransactionCategory.Food -> IconBgStyle.Warn to WalletTheme.colors.warnDeep
        TransactionCategory.Income -> IconBgStyle.Good to WalletTheme.colors.good
        else -> IconBgStyle.Neutral to WalletTheme.colors.ink2
    }
    val sign = if (tx.type == TransactionType.Income) "+" else "−"
    WalletTransactionRow(
        title = tx.title,
        subtitle = tx.subtitle,
        amount = "$sign${tx.amount.amount.formatThousands()} ${tx.amount.currency}",
        isIncome = tx.type == TransactionType.Income,
        iconStyle = style,
        onClick = {},
        icon = {
            when (tx.category) {
                TransactionCategory.Transport -> WalletIconCar(color = iconColor)
                TransactionCategory.Food -> WalletIconCoffee(color = iconColor)
                TransactionCategory.Income -> WalletIconArrowUpRight(size = 18.dp, color = iconColor)
                else -> WalletIconUsers(size = 18.dp, color = iconColor)
            }
        },
    )
}

internal fun Long.formatThousands(): String {
    val s = this.toString()
    val rev = s.reversed()
    val groups = rev.chunked(3).joinToString(",")
    return groups.reversed()
}

@Preview
@Composable
private fun DashboardScreenArabicPreview() {
    WalletPreviewHarness(locale = AppLocale.Arabic) {
        val vm = remember { DashboardViewModel(InMemoryWalletRepository()) }
        DashboardScreen(
            viewModel = vm,
            onAdd = {}, onVoice = {}, onInstallments = {},
            onReports = {}, onSettings = {},
        )
    }
}

@Preview
@Composable
private fun DashboardScreenEnglishDarkPreview() {
    WalletPreviewHarness(locale = AppLocale.English, darkTheme = true) {
        val vm = remember { DashboardViewModel(InMemoryWalletRepository()) }
        DashboardScreen(
            viewModel = vm,
            onAdd = {}, onVoice = {}, onInstallments = {},
            onReports = {}, onSettings = {},
        )
    }
}
