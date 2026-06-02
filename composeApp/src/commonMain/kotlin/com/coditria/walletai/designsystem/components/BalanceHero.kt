package com.walletai.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Hero balance card with brand gradient bg, currency, optional pills.
 *
 * Used as the dashboard centerpiece. The amount uses display typography
 * with tabular figures for visual stability.
 *
 * Usage:
 * ```
 * WalletBalanceHero(
 *     label = "الرصيد الكلي",
 *     amount = "23,450",
 *     currency = "ج.م",
 *     onToggleVisibility = { ... },
 *     visibilityIcon = { EyeIcon() },
 * ) {
 *     Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
 *         WalletBalancePill(
 *             label = "الدخل",
 *             value = "25,000",
 *             style = BalancePillStyle.Up,
 *             icon = { ArrowUpIcon() },
 *             modifier = Modifier.weight(1f),
 *         )
 *         WalletBalancePill(
 *             label = "المصاريف",
 *             value = "16,800",
 *             style = BalancePillStyle.Down,
 *             icon = { ArrowDownIcon() },
 *             modifier = Modifier.weight(1f),
 *         )
 *     }
 * }
 * ```
 */
@Composable
fun WalletBalanceHero(
    label: String,
    amount: String,
    currency: String,
    onToggleVisibility: () -> Unit,
    visibilityIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    pillsContent: (@Composable () -> Unit)? = null,
) {
    WalletHeroCard(modifier = modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = label,
                    style = WalletTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.72f),
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(WalletTheme.shapes.small)
                        .background(Color.White.copy(alpha = 0.12f))
                        .clickable(onClick = onToggleVisibility),
                    contentAlignment = Alignment.Center,
                ) {
                    visibilityIcon()
                }
            }

            Spacer(Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = amount,
                    style = WalletTheme.typography.displayHero,
                    color = Color.White,
                )
                Text(
                    text = currency,
                    style = WalletTheme.typography.headingMedium.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                    color = Color.White.copy(alpha = 0.75f),
                    modifier = Modifier.padding(bottom = 6.dp),
                )
            }

            if (subtitle != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = WalletTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.65f),
                )
            }

            if (pillsContent != null) {
                Spacer(Modifier.height(18.dp))
                pillsContent()
            }
        }
    }
}

enum class BalancePillStyle {
    /** Income — green tint icon. */
    Up,
    /** Expense — red tint icon. */
    Down,
    /** Neutral — chip-bg icon. */
    Neutral,
}

/**
 * Glassy pill inside the balance hero — shows income/expense breakdown.
 * Equivalent to .bl-pill in CSS.
 */
@Composable
fun WalletBalancePill(
    label: String,
    value: String,
    style: BalancePillStyle,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors

    val (iconBg, iconFg) = when (style) {
        BalancePillStyle.Up -> colors.accent.copy(alpha = 0.22f) to Color(0xFF85F7E0)
        BalancePillStyle.Down -> colors.bad.copy(alpha = 0.22f) to Color(0xFFFFB1B9)
        BalancePillStyle.Neutral -> Color.White.copy(alpha = 0.12f) to Color.White
    }

    Row(
        modifier = modifier
            .clip(WalletTheme.shapes.small)
            .background(Color.White.copy(alpha = 0.10f))
            .border(0.5.dp, Color.White.copy(alpha = 0.16f), WalletTheme.shapes.small)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(WalletTheme.shapes.small)
                .background(iconBg),
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = WalletTheme.typography.caption,
                color = Color.White.copy(alpha = 0.65f),
            )
            Text(
                text = value,
                style = WalletTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = Color.White,
            )
        }
    }
}
