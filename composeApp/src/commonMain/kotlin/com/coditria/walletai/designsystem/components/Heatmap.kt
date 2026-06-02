package com.walletai.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.foundation.ColorPalette
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Single cell in the debt-pressure heatmap.
 *
 * @param level 0..4 — pressure intensity (0 = lightest, 4 = high pressure red)
 */
data class HeatmapCellData(
    val monthLabel: String,
    val amount: String,
    val level: Int,
    val isCurrent: Boolean = false,
)

/**
 * Heatmap grid — 6 columns of debt pressure per month.
 * Equivalent to .hm / .hm-g / .hm-c in CSS.
 *
 * Usage:
 * ```
 * WalletHeatmapGrid(
 *     cells = listOf(
 *         HeatmapCellData("ينا", "2.1k", 2),
 *         HeatmapCellData("فبر", "1.5k", 1),
 *         HeatmapCellData("مار", "3.3k", 3),
 *         HeatmapCellData("أبر", "2.3k", 2),
 *         HeatmapCellData("ماي", "4.2k", 4, isCurrent = true),
 *         ...
 *     ),
 *     onCellClick = { idx -> /* show month detail */ },
 * )
 * ```
 */
@Composable
fun WalletHeatmapGrid(
    cells: List<HeatmapCellData>,
    modifier: Modifier = Modifier,
    columns: Int = 6,
    onCellClick: ((Int) -> Unit)? = null,
) {
    val colors = WalletTheme.colors

    // Pressure level colors (light to high)
    val levelColors = listOf(
        Color(0xFFDBEAFE),
        Color(0xFF93C5FD),
        Color(0xFF3B82F6),
        Color(0xFF1E40AF),
        ColorPalette.Bad,
    )

    Column(modifier = modifier.fillMaxWidth()) {
        // Render as 2 rows of 6 (12 months)
        cells.chunked(columns).forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                row.forEachIndexed { rowIdx, cell ->
                    val cellColor = levelColors.getOrElse(cell.level) { levelColors.first() }
                    val textColor = if (cell.level >= 3) Color.White else colors.ink

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(WalletTheme.shapes.small)
                            .background(cellColor)
                            .let {
                                if (onCellClick != null) {
                                    val idx = cells.indexOf(cell)
                                    it.clickable { onCellClick(idx) }
                                } else it
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = cell.monthLabel,
                                style = WalletTheme.typography.caption.copy(
                                    fontSize = androidx.compose.ui.unit.TextUnit(
                                        8.5f,
                                        androidx.compose.ui.unit.TextUnitType.Sp,
                                    ),
                                ),
                                color = textColor.copy(alpha = 0.65f),
                            )
                            Text(
                                text = cell.amount,
                                style = WalletTheme.typography.caption.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = androidx.compose.ui.unit.TextUnit(
                                        11f,
                                        androidx.compose.ui.unit.TextUnitType.Sp,
                                    ),
                                ),
                                color = textColor,
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Heatmap legend — small color scale below the grid.
 */
@Composable
fun WalletHeatmapLegend(
    modifier: Modifier = Modifier,
    leftLabel: String = "أقل",
    rightLabel: String = "أكثر",
) {
    val colors = WalletTheme.colors
    val levelColors = listOf(
        Color(0xFFDBEAFE),
        Color(0xFF93C5FD),
        Color(0xFF3B82F6),
        Color(0xFF1E40AF),
        ColorPalette.Bad,
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = leftLabel,
            style = WalletTheme.typography.caption,
            color = colors.muted,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            levelColors.forEach { c ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(WalletTheme.shapes.small)
                        .background(c),
                )
            }
        }
        Text(
            text = rightLabel,
            style = WalletTheme.typography.caption,
            color = colors.muted,
        )
    }
}


