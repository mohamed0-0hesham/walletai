package com.coditria.walletai.feature.dashboard

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coditria.walletai.app.LocalWalletStrings
import com.walletai.core.designsystem.theme.WalletTheme

@Composable
fun DashboardChartCard(
    selectedRange: ChartRange,
    onSelect: (ChartRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    val s = LocalWalletStrings.current
    val labels = mapOf(
        ChartRange.Week to s.week,
        ChartRange.Month to s.month,
        ChartRange.Year to s.year,
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface)
            .padding(horizontal = 16.dp, vertical = 18.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(WalletTheme.shapes.medium)
                .background(WalletTheme.colors.chipBg)
                .padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            ChartRange.entries.forEach { range ->
                ChartTab(
                    label = labels[range] ?: range.label,
                    selected = range == selectedRange,
                    onClick = { onSelect(range) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        ChartCanvas()
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            listOf("ينا", "فبر", "مار", "أبر", "ماي", "يون").forEachIndexed { i, m ->
                Text(
                    text = m,
                    style = WalletTheme.typography.caption,
                    color = if (i == 4) WalletTheme.colors.ink else WalletTheme.colors.muted,
                    fontWeight = if (i == 4) FontWeight.SemiBold else FontWeight.Normal,
                )
            }
        }
    }
}

@Composable
private fun ChartTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(WalletTheme.shapes.small)
            .background(if (selected) WalletTheme.colors.surface2 else Color.Transparent)
            .padding(vertical = 7.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = WalletTheme.typography.caption.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            ),
            color = if (selected) WalletTheme.colors.ink else WalletTheme.colors.muted,
        )
    }
}

@Composable
private fun ChartCanvas() {
    val brand = WalletTheme.colors.brand
    val accent = WalletTheme.colors.accent
    val brandAlt = WalletTheme.colors.brandAlt

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Sample points emulating the SVG curve from the prototype.
            val xs = listOf(0.03f, 0.12f, 0.20f, 0.28f, 0.43f, 0.53f, 0.72f, 0.81f, 0.94f, 0.97f)
            val ys = listOf(0.73f, 0.62f, 0.77f, 0.54f, 0.23f, 0.39f, 0.15f, 0.27f, 0.46f, 0.35f)

            val main = Path().apply {
                xs.forEachIndexed { i, x ->
                    val px = x * w; val py = ys[i] * h
                    if (i == 0) moveTo(px, py) else lineTo(px, py)
                }
            }
            val area = Path().apply {
                xs.forEachIndexed { i, x ->
                    val px = x * w; val py = ys[i] * h
                    if (i == 0) moveTo(px, py) else lineTo(px, py)
                }
                lineTo(w * 0.97f, h)
                lineTo(w * 0.03f, h)
                close()
            }
            drawPath(
                path = area,
                brush = Brush.verticalGradient(
                    colors = listOf(brand.copy(alpha = 0.35f), brand.copy(alpha = 0f)),
                ),
            )
            drawPath(
                path = main,
                brush = Brush.horizontalGradient(listOf(accent, brandAlt)),
                style = Stroke(width = 2.4f * density, cap = StrokeCap.Round),
            )
            // Secondary expense dashed line
            val secondary = Path().apply {
                moveTo(w * 0.03f, h * 0.81f)
                lineTo(w * 0.30f, h * 0.74f)
                lineTo(w * 0.55f, h * 0.62f)
                lineTo(w * 0.78f, h * 0.62f)
                lineTo(w * 0.97f, h * 0.55f)
            }
            drawPath(
                path = secondary,
                color = Color(0x2E14183C),
                style = Stroke(
                    width = 1.5f * density,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f)),
                ),
            )
            // Highlight point
            val cx = w * 0.43f
            val cy = h * 0.23f
            drawCircle(Color.White, radius = 6f * density, center = Offset(cx, cy))
            drawCircle(brand, radius = 6f * density, center = Offset(cx, cy),
                style = Stroke(width = 2.4f * density))
            drawCircle(brand, radius = 2.5f * density, center = Offset(cx, cy))
        }
    }
}

