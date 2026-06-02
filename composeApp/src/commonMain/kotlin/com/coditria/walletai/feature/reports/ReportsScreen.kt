package com.coditria.walletai.feature.reports

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.data.di.DataGraph
import kotlinx.coroutines.launch
import com.coditria.walletai.resources.Res
import com.coditria.walletai.resources.out_of
import org.jetbrains.compose.resources.stringResource
import com.coditria.walletai.domain.model.AiInsight
import com.coditria.walletai.feature.common.WalletPreviewHarness
import com.coditria.walletai.domain.model.CategoryBreakdown
import com.coditria.walletai.domain.model.FinancialHealth
import com.coditria.walletai.domain.model.InsightTone
import com.coditria.walletai.feature.common.WalletAppBottomNav
import com.coditria.walletai.feature.common.WalletIconArrowDownLeft
import com.coditria.walletai.feature.common.WalletIconArrowUpRight
import com.coditria.walletai.feature.common.WalletIconChevronLeft
import com.coditria.walletai.feature.common.WalletIconSparkles
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.coditria.walletai.feature.dashboard.formatThousands
import com.coditria.walletai.navigation.Route
import com.walletai.core.designsystem.components.ChipStyle
import com.walletai.core.designsystem.components.WalletChip
import com.coditria.walletai.designsystem.components.WalletTopBar
import com.walletai.core.designsystem.theme.WalletTheme

class ReportsViewModel(
    private val analyticsRepository: com.coditria.walletai.domain.repository.AnalyticsRepository,
    private val balanceRepository: com.coditria.walletai.domain.repository.BalanceRepository,
    scope: kotlinx.coroutines.CoroutineScope,
) {
    var health: FinancialHealth by mutableStateOf(
        FinancialHealth(score = 0, verdict = "", description = ""),
    )
        private set
    var breakdown: List<CategoryBreakdown> by mutableStateOf(emptyList())
        private set
    var insights: List<AiInsight> by mutableStateOf(emptyList())
        private set
    var totalExpenses: com.coditria.walletai.domain.model.Money by mutableStateOf(
        com.coditria.walletai.domain.model.Money(0, ""),
    )
        private set

    init {
        scope.launch {
            health = analyticsRepository.financialHealth()
            breakdown = analyticsRepository.expenseBreakdown()
            insights = analyticsRepository.aiInsights()
            totalExpenses = balanceRepository.summary().expenses
        }
    }
}

@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel,
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
                title = s.reports,
                onBack = onBack,
                backIcon = { WalletIconChevronLeft(color = WalletTheme.colors.ink2) },
                trailingIcon = { WalletIconArrowDownLeft(size = 16.dp, color = WalletTheme.colors.ink2) },
                onTrailingClick = {},
            )

            Spacer(Modifier.height(8.dp))
            HealthCard(viewModel.health)
            Spacer(Modifier.height(14.dp))
            BreakdownCard(viewModel.breakdown, viewModel.totalExpenses.amount)

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                WalletChip(text = s.ai, style = ChipStyle.AiBadge)
                Text(
                    s.smartInsights,
                    style = WalletTheme.typography.bodyMedium,
                    color = WalletTheme.colors.ink2,
                )
            }
            viewModel.insights.forEach { ins ->
                InsightCard(ins)
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(110.dp))
        }

        WalletAppBottomNav(
            selected = Route.Reports,
            onSelect = onSelect,
            onAddTransaction = onAdd,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun HealthCard(health: FinancialHealth) {
    val s = LocalWalletStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface)
            .padding(18.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                s.financialHealth,
                style = WalletTheme.typography.label,
                color = WalletTheme.colors.muted,
            )
            WalletChip(text = s.verdictGood, style = ChipStyle.Good)
        }
        Spacer(Modifier.height(14.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            HealthRing(score = health.score, max = health.maxScore)
            Text(
                health.description,
                style = WalletTheme.typography.bodySmall,
                color = WalletTheme.colors.ink2,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun HealthRing(score: Int, max: Int) {
    val s = LocalWalletStrings.current
    Box(
        modifier = Modifier.size(96.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val sw = 9.dp.toPx()
            val r = (size.minDimension - sw) / 2f
            val c = Offset(size.width / 2f, size.height / 2f)
            drawCircle(color = Color(0x1214183C), radius = r, center = c, style = Stroke(sw))
            val sweep = 360f * (score.toFloat() / max.toFloat())
            drawArc(
                brush = Brush.linearGradient(listOf(Color(0xFF3DD2C0), Color(0xFF5B6CFF))),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(sw, cap = StrokeCap.Round),
                topLeft = Offset(c.x - r, c.y - r),
                size = androidx.compose.ui.geometry.Size(r * 2, r * 2),
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                score.toString(),
                style = WalletTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = WalletTheme.colors.ink,
            )
            Text(
                stringResource(Res.string.out_of, max),
                style = WalletTheme.typography.caption,
                color = WalletTheme.colors.muted,
            )
        }
    }
}

@Composable
private fun BreakdownCard(items: List<CategoryBreakdown>, total: Long) {
    val s = LocalWalletStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface)
            .padding(18.dp),
    ) {
        Text(
            s.expenseBreakdown,
            style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = WalletTheme.colors.ink,
        )
        Spacer(Modifier.height(14.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            DonutChart(items = items, total = total)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                items.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(WalletTheme.shapes.small)
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(item.gradientStart), Color(item.gradientEnd)),
                                    ),
                                ),
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            item.name,
                            style = WalletTheme.typography.bodySmall,
                            color = WalletTheme.colors.ink,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            "${item.percent}%",
                            style = WalletTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold),
                            color = WalletTheme.colors.muted,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DonutChart(items: List<CategoryBreakdown>, total: Long) {
    val s = LocalWalletStrings.current
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val sw = 14.dp.toPx()
            val r = (size.minDimension - sw) / 2f
            val c = Offset(size.width / 2f, size.height / 2f)
            drawCircle(color = Color(0x0F14183C), radius = r, center = c, style = Stroke(sw))
            var start = -90f
            items.forEach { item ->
                val sweep = 360f * (item.percent / 100f)
                drawArc(
                    brush = Brush.linearGradient(
                        listOf(Color(item.gradientStart), Color(item.gradientEnd)),
                    ),
                    startAngle = start,
                    sweepAngle = sweep - 4f,
                    useCenter = false,
                    style = Stroke(sw, cap = StrokeCap.Round),
                    topLeft = Offset(c.x - r, c.y - r),
                    size = androidx.compose.ui.geometry.Size(r * 2, r * 2),
                )
                start += sweep
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                total.formatThousands(),
                style = WalletTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = WalletTheme.colors.ink,
            )
            Text(
                s.expensesUnit,
                style = WalletTheme.typography.caption,
                color = WalletTheme.colors.muted,
            )
        }
    }
}

@Composable
private fun InsightCard(ins: AiInsight) {
    val (bg, fg) = when (ins.tone) {
        InsightTone.Brand -> WalletTheme.colors.brand.copy(alpha = 0.18f) to WalletTheme.colors.brand
        InsightTone.Bad -> WalletTheme.colors.bad.copy(alpha = 0.14f) to WalletTheme.colors.bad
        InsightTone.Good -> WalletTheme.colors.good.copy(alpha = 0.14f) to WalletTheme.colors.good
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface)
            .padding(14.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(WalletTheme.shapes.small)
                .background(bg),
            contentAlignment = Alignment.Center,
        ) {
            when (ins.tone) {
                InsightTone.Brand -> WalletIconSparkles(size = 16.dp, color = fg)
                InsightTone.Bad -> WalletIconArrowUpRight(size = 16.dp, color = fg)
                InsightTone.Good -> WalletIconArrowDownLeft(size = 16.dp, color = fg)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                ins.title,
                style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = WalletTheme.colors.ink,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                ins.description,
                style = WalletTheme.typography.caption,
                color = WalletTheme.colors.muted,
            )
        }
    }
}

@Preview
@Composable
private fun ReportsScreenArabicPreview() {
    WalletPreviewHarness(locale = AppLocale.Arabic) {
        val data = remember { DataGraph.previewFakes() }
        val scope = androidx.compose.runtime.rememberCoroutineScope()
        val vm = remember { ReportsViewModel(data.analyticsRepository, data.balanceRepository, scope) }
        ReportsScreen(viewModel = vm, onBack = {}, onAdd = {}, onSelect = {})
    }
}

@Preview
@Composable
private fun ReportsScreenEnglishPreview() {
    WalletPreviewHarness(locale = AppLocale.English) {
        val data = remember { DataGraph.previewFakes() }
        val scope = androidx.compose.runtime.rememberCoroutineScope()
        val vm = remember { ReportsViewModel(data.analyticsRepository, data.balanceRepository, scope) }
        ReportsScreen(viewModel = vm, onBack = {}, onAdd = {}, onSelect = {})
    }
}
