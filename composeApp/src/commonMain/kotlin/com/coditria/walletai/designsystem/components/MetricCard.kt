package com.walletai.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

enum class MetricTrend {
    /** Improvement direction — green text, up arrow. */
    Up,
    /** Worsening direction — red text, down arrow. */
    Down,
    /** No change — muted text. */
    Neutral,
}

/**
 * Metric card — small white card with icon, label, big value, optional delta.
 * Equivalent to .ie-card in CSS.
 *
 * Used on dashboard for income/expense summary.
 *
 * Usage:
 * ```
 * Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
 *     WalletMetricCard(
 *         label = "الدخل",
 *         value = "25,000",
 *         deltaLabel = "+12% عن الشهر اللي فات",
 *         trend = MetricTrend.Up,
 *         iconStyle = IconBgStyle.Good,
 *         icon = { ArrowUpIcon() },
 *         modifier = Modifier.weight(1f),
 *     )
 *     WalletMetricCard(
 *         label = "المصاريف",
 *         value = "16,800",
 *         deltaLabel = "−5% عن الشهر اللي فات",
 *         trend = MetricTrend.Up,
 *         iconStyle = IconBgStyle.Bad,
 *         icon = { ArrowDownIcon() },
 *         modifier = Modifier.weight(1f),
 *     )
 * }
 * ```
 */
@Composable
fun WalletMetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    deltaLabel: String? = null,
    trend: MetricTrend = MetricTrend.Neutral,
    iconStyle: IconBgStyle = IconBgStyle.Brand,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors

    WalletCard(modifier = modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                WalletIconBox(style = iconStyle, size = 30.dp, icon = icon)
                Text(
                    text = label,
                    style = WalletTheme.typography.bodySmall,
                    color = colors.muted,
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                style = WalletTheme.typography.displayMedium,
                color = colors.ink,
            )
            if (deltaLabel != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = deltaLabel,
                    style = WalletTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold),
                    color = when (trend) {
                        MetricTrend.Up -> colors.good
                        MetricTrend.Down -> colors.bad
                        MetricTrend.Neutral -> colors.muted
                    },
                )
            }
        }
    }
}
