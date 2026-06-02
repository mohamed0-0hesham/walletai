package com.walletai.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

enum class ChipStyle {
    /** Neutral chip — chipBg + ink2 text. */
    Default,
    /** Brand-tinted — soft purple bg + brand text. */
    Brand,
    /** Success chip — soft green bg + good text. */
    Good,
    /** Warning chip — soft amber bg + warn text. */
    Warn,
    /** Danger chip — soft red bg + bad text. */
    Bad,
    /** Filled brand — gradient bg + white text (e.g. AI badge). */
    AiBadge,
}

/**
 * Pill-shaped label/tag.
 *
 * Examples:
 * - Time tags (.ib-tg.warn): "مستحق غداً" → ChipStyle.Warn
 * - AI badge (.ai-tag): "AI" → ChipStyle.AiBadge
 * - Status pill (.scr-bdg "جيد"): ChipStyle.Good
 */
@Composable
fun WalletChip(
    text: String,
    modifier: Modifier = Modifier,
    style: ChipStyle = ChipStyle.Default,
    onClick: (() -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.small

    val (bg, fg) = when (style) {
        ChipStyle.Default -> colors.chipBg to colors.ink2
        ChipStyle.Brand -> colors.brand.copy(alpha = 0.10f) to colors.brand
        ChipStyle.Good -> colors.good.copy(alpha = 0.14f) to colors.good
        ChipStyle.Warn -> colors.warn.copy(alpha = 0.16f) to Color(0xFFB86A1C)
        ChipStyle.Bad -> colors.bad.copy(alpha = 0.14f) to colors.bad
        ChipStyle.AiBadge -> Color.Transparent to colors.onBrand
    }

    val baseModifier = modifier
        .clip(shape)
        .let {
            if (style == ChipStyle.AiBadge) it.background(colors.brandGradient)
            else it.background(bg)
        }
        .let { if (onClick != null) it.clickable(onClick = onClick) else it }
        .padding(horizontal = 11.dp, vertical = 5.dp)

    Row(
        modifier = baseModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        leadingIcon?.invoke()
        Text(
            text = text,
            style = WalletTheme.typography.bodySmall.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            ),
            color = fg,
        )
    }
}

/**
 * Section "more" pill — small chevron pill on right of section headers.
 * Equivalent to .sh .more in CSS.
 */
@Composable
fun WalletMorePill(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = WalletTheme.colors

    Box(
        modifier = modifier
            .clip(WalletTheme.shapes.pill)
            .background(colors.surface.copy(alpha = 0.7f))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp),
    ) {
        Text(
            text = text,
            style = WalletTheme.typography.bodySmall.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            ),
            color = colors.ink2,
        )
    }
}
