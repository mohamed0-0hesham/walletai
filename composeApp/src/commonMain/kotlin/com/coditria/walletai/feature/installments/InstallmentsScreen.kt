package com.coditria.walletai.feature.installments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.data.InMemoryWalletRepository
import com.coditria.walletai.resources.Res
import com.coditria.walletai.resources.paid_of_total
import org.jetbrains.compose.resources.stringResource
import com.coditria.walletai.domain.model.HeatmapMonth
import com.coditria.walletai.feature.common.WalletPreviewHarness
import com.coditria.walletai.domain.model.Installment
import com.coditria.walletai.domain.repository.WalletRepository
import com.coditria.walletai.feature.common.WalletAppBottomNav
import com.coditria.walletai.feature.common.WalletIconChevronLeft
import com.coditria.walletai.feature.common.WalletIconPhone
import com.coditria.walletai.feature.common.WalletIconSearch
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.coditria.walletai.feature.dashboard.formatThousands
import com.coditria.walletai.navigation.Route
import com.walletai.core.designsystem.components.HeatmapCellData
import com.walletai.core.designsystem.components.WalletHeatmapGrid
import com.coditria.walletai.designsystem.components.WalletTopBar
import com.walletai.core.designsystem.theme.WalletTheme

class InstallmentsViewModel(repository: WalletRepository) {
    val summary = repository.installmentSummary()
    val items = repository.allInstallments()
    val heatmap = repository.heatmap()

    var selectedTab: InstallmentsTab by mutableStateOf(InstallmentsTab.Active)
        private set

    fun selectTab(tab: InstallmentsTab) { selectedTab = tab }
}

enum class InstallmentsTab(val label: String) {
    Active("نشطة"),
    Calendar("التقويم"),
    Done("منتهية"),
}

@Composable
fun InstallmentsScreen(
    viewModel: InstallmentsViewModel,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onSelect: (Route) -> Unit,
) {
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
            WalletTopBar(
                title = s.installments,
                onBack = onBack,
                backIcon = { WalletIconChevronLeft(color = WalletTheme.colors.ink2) },
                trailingIcon = { WalletIconSearch(color = WalletTheme.colors.ink2) },
                onTrailingClick = {},
            )

            Spacer(Modifier.height(8.dp))
            SummaryHero(viewModel)

            Spacer(Modifier.height(14.dp))
            TabRow(selected = viewModel.selectedTab, onSelect = viewModel::selectTab)

            Spacer(Modifier.height(14.dp))
            HeatmapCard(viewModel.heatmap)

            Spacer(Modifier.height(10.dp))
            viewModel.items.forEach { inst ->
                InstallmentBigCard(inst)
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(110.dp))
        }

        WalletAppBottomNav(
            selected = Route.Installments,
            onSelect = onSelect,
            onAddTransaction = onAdd,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun SummaryHero(viewModel: InstallmentsViewModel) {
    val s = LocalWalletStrings.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.large)
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF5B6CFF), Color(0xFF262C56), Color(0xFF101427)),
                ),
            )
            .padding(20.dp),
    ) {
        Column {
            Text(
                s.totalRemainingDebt,
                style = WalletTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.72f),
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    viewModel.summary.totalRemaining.amount.formatThousands(),
                    style = WalletTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                )
                Spacer(Modifier.size(6.dp))
                Text(
                    viewModel.summary.totalRemaining.currency,
                    style = WalletTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SummaryStat(
                    label = s.monthly,
                    value = "${viewModel.summary.monthly.amount.formatThousands()} ${viewModel.summary.monthly.currency}",
                    modifier = Modifier.weight(1f),
                )
                SummaryStat(
                    label = s.remaining,
                    value = "${viewModel.summary.remainingMonths} ${s.monthsWord}",
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun SummaryStat(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(WalletTheme.shapes.small)
            .background(Color.White.copy(alpha = 0.10f))
            .padding(horizontal = 12.dp, vertical = 10.dp),
    ) {
        Text(label, style = WalletTheme.typography.caption, color = Color.White.copy(alpha = 0.7f))
        Spacer(Modifier.height(3.dp))
        Text(
            value,
            style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White,
        )
    }
}

@Composable
private fun TabRow(selected: InstallmentsTab, onSelect: (InstallmentsTab) -> Unit) {
    val s = LocalWalletStrings.current
    val labelFor: (InstallmentsTab) -> String = {
        when (it) {
            InstallmentsTab.Active -> s.tabActive
            InstallmentsTab.Calendar -> s.tabCalendar
            InstallmentsTab.Done -> s.tabDone
        }
    }
    Row(
        modifier = Modifier.padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        InstallmentsTab.entries.forEach { tab ->
            val on = tab == selected
            Box(
                modifier = Modifier
                    .clip(WalletTheme.shapes.small)
                    .background(if (on) WalletTheme.colors.ink else WalletTheme.colors.surface)
                    .clickable { onSelect(tab) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = labelFor(tab),
                    style = WalletTheme.typography.bodySmall.copy(
                        fontWeight = if (on) FontWeight.SemiBold else FontWeight.Medium,
                    ),
                    color = if (on) Color.White else WalletTheme.colors.ink2,
                )
            }
        }
    }
}

@Composable
private fun HeatmapCard(months: List<HeatmapMonth>) {
    val s = LocalWalletStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                s.pressureMap,
                style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = WalletTheme.colors.ink,
            )
            Box(
                modifier = Modifier
                    .clip(WalletTheme.shapes.small)
                    .background(WalletTheme.colors.chipBg)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    "2026",
                    style = WalletTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold),
                    color = WalletTheme.colors.ink2,
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        WalletHeatmapGrid(
            cells = months.map {
                HeatmapCellData(
                    monthLabel = it.label,
                    amount = it.amountLabel,
                    level = it.pressure,
                )
            },
        )
    }
}

@Composable
private fun InstallmentBigCard(inst: Installment) {
    val s = LocalWalletStrings.current
    val warn = inst.isWarning
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface)
            .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(WalletTheme.shapes.small)
                    .background(WalletTheme.colors.brandGradient),
                contentAlignment = Alignment.Center,
            ) { WalletIconPhone(color = Color.White) }
            Spacer(Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    inst.name,
                    style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = WalletTheme.colors.ink,
                )
                Text(
                    inst.provider,
                    style = WalletTheme.typography.caption,
                    color = WalletTheme.colors.muted,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    inst.monthlyAmount.amount.formatThousands(),
                    style = WalletTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                    color = WalletTheme.colors.ink,
                )
                Text(
                    "${inst.monthlyAmount.currency}${s.perMonth}",
                    style = WalletTheme.typography.caption,
                    color = WalletTheme.colors.muted,
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        val percent = (inst.paidMonths * 100 / inst.totalMonths)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                stringResource(Res.string.paid_of_total, inst.paidMonths, inst.totalMonths),
                style = WalletTheme.typography.caption,
                color = WalletTheme.colors.muted,
            )
            Text(
                "$percent%",
                style = WalletTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
                color = WalletTheme.colors.ink,
            )
        }
        Spacer(Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(WalletTheme.shapes.pill)
                .background(WalletTheme.colors.line),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percent / 100f)
                    .height(6.dp)
                    .clip(WalletTheme.shapes.pill)
                    .background(
                        if (warn) WalletTheme.colors.warnGradient
                        else WalletTheme.colors.brandGradient,
                    ),
            )
        }
        Spacer(Modifier.height(10.dp))
        Row {
            Box(
                modifier = Modifier
                    .clip(WalletTheme.shapes.small)
                    .background(
                        if (warn) WalletTheme.colors.warn.copy(alpha = 0.16f)
                        else WalletTheme.colors.brand.copy(alpha = 0.10f),
                    )
                    .padding(horizontal = 11.dp, vertical = 5.dp),
            ) {
                Text(
                    inst.nextDueLabel,
                    style = WalletTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold),
                    color = if (warn) WalletTheme.colors.warnDeep else WalletTheme.colors.brand,
                )
            }
        }
    }
}

@Preview
@Composable
private fun InstallmentsScreenArabicPreview() {
    WalletPreviewHarness(locale = AppLocale.Arabic) {
        val vm = remember { InstallmentsViewModel(InMemoryWalletRepository()) }
        InstallmentsScreen(viewModel = vm, onBack = {}, onAdd = {}, onSelect = {})
    }
}

@Preview
@Composable
private fun InstallmentsScreenEnglishPreview() {
    WalletPreviewHarness(locale = AppLocale.English) {
        val vm = remember { InstallmentsViewModel(InMemoryWalletRepository()) }
        InstallmentsScreen(viewModel = vm, onBack = {}, onAdd = {}, onSelect = {})
    }
}
