package com.walletai.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
 * AI input card — used on Add Transaction screen to show what the AI understood.
 * Equivalent to .ai-card in CSS.
 *
 * Renders a subtle gradient bg, a small AI badge in the corner,
 * a "AI فهم" label, and the parsed text.
 */
@Composable
fun WalletAiInputCard(
    parsedText: String,
    modifier: Modifier = Modifier,
    label: String = "AI فهم",
    badgeIcon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.small

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.aiSuggestionGradient)
            .border(1.dp, colors.brand.copy(alpha = 0.18f), shape),
    ) {
        Row(modifier = Modifier.padding(14.dp)) {
            // Badge icon
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(WalletTheme.shapes.small)
                    .background(colors.brandGradient),
                contentAlignment = Alignment.Center,
            ) {
                badgeIcon()
            }

            Spacer(Modifier.size(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = WalletTheme.typography.label.copy(fontWeight = FontWeight.Bold),
                    color = colors.brand,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = parsedText,
                    style = WalletTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = colors.ink,
                )
            }
        }
    }
}

/**
 * Suggestion row — used inside AI suggestion card to show extracted fields.
 * Equivalent to .sg-row in CSS.
 *
 * Usage:
 * ```
 * WalletAiCard {
 *     WalletAiSuggestionRow(label = "الفئة") {
 *         WalletChip(text = "أكل وشرب", style = ChipStyle.Warn) { ... }
 *     }
 *     WalletAiSuggestionRow(label = "التاريخ", value = "اليوم • 9:10 ص")
 *     WalletAiSuggestionRow(label = "الحساب", value = "كاش")
 *     Spacer(Modifier.height(10.dp))
 *     WalletConfidenceBar(confidence = 0.92f)
 * }
 * ```
 */
@Composable
fun WalletAiSuggestionRow(
    label: String,
    modifier: Modifier = Modifier,
    value: String? = null,
    showDivider: Boolean = true,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val colors = WalletTheme.colors

    Column {
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.line),
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label,
                style = WalletTheme.typography.bodyMedium,
                color = colors.muted,
            )
            if (value != null) {
                Text(
                    text = value,
                    style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.ink,
                )
            }
            trailingContent?.invoke()
        }
    }
}
