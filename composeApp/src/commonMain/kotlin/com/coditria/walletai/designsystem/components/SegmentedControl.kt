package com.walletai.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

enum class SegmentStyle {
    /** Default: surface highlight on selected. */
    Default,
    /** Bad: red gradient on selected (expense type). */
    Bad,
    /** Good: green gradient on selected (income type). */
    Good,
}

data class SegmentItem(
    val key: String,
    val label: String,
)

/**
 * Segmented control — chipBg track with selected indicator.
 * Equivalent to .seg / .chart-tabs / .ct in CSS.
 *
 * Two height variants:
 * - large (44dp): main tabs (مصروف/دخل/تحويل)
 * - small (32dp): in-card tabs (أسبوع/شهر/سنة)
 */
@Composable
fun WalletSegmentedControl(
    items: List<SegmentItem>,
    selectedKey: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 44.dp,
    style: SegmentStyle = SegmentStyle.Default,
) {
    val colors = WalletTheme.colors
    val outerShape = WalletTheme.shapes.small
    val innerShape = WalletTheme.shapes.small

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(outerShape)
            .background(colors.chipBg)
            .padding(4.dp),
    ) {
        items.forEach { item ->
            val isSelected = item.key == selectedKey

            val selectedFg: Color
            val selectedBrush: Brush?

            when {
                !isSelected -> {
                    selectedFg = colors.muted
                    selectedBrush = null
                }
                style == SegmentStyle.Bad -> {
                    selectedFg = Color.White
                    selectedBrush = colors.badGradient
                }
                style == SegmentStyle.Good -> {
                    selectedFg = Color.White
                    selectedBrush = colors.goodGradient
                }
                else -> {
                    selectedFg = colors.ink
                    selectedBrush = Brush.linearGradient(listOf(colors.surface, colors.surface))
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(2.dp)
                    .clip(innerShape)
                    .let {
                        if (selectedBrush != null) {
                            it.shadow(2.dp, innerShape).background(selectedBrush)
                        } else it
                    }
                    .clickable { onSelect(item.key) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = item.label,
                    style = WalletTheme.typography.bodyMedium.copy(
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                    ),
                    color = selectedFg,
                )
            }
        }
    }
}
