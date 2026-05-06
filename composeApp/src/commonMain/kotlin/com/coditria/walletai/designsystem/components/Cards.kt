package com.walletai.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Standard surface card — white in light, elevated dark in dark mode.
 * Equivalent to .ie-card / .inst-card / .tx-list / .scr / .pie-card / .ins / .hm
 *
 * Usage:
 * ```
 * WalletCard(onClick = { ... }) {
 *     // content
 * }
 * ```
 */
@Composable
fun WalletCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(WalletTheme.spacing.cardPadding),
    content: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.medium

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = shape,
                ambientColor = Color(0x1014183C),
                spotColor = Color(0x1014183C),
            )
            .clip(shape)
            .background(colors.surface)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(contentPadding),
    ) {
        content()
    }
}

/**
 * Hero gradient card — used for balance display and installments summary.
 * Equivalent to .balance / .summ in CSS.
 *
 * Renders the brand gradient with white-ish text on top.
 */
@Composable
fun WalletHeroCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(WalletTheme.spacing.cardPaddingLarge),
    content: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.large

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = shape,
                ambientColor = colors.brand.copy(alpha = 0.25f),
                spotColor = colors.brand.copy(alpha = 0.25f),
            )
            .clip(shape)
            .background(colors.balanceGradient)
            .padding(contentPadding),
    ) {
        content()
    }
}

/**
 * Warning card variant — used for alerts (e.g. installment due tomorrow).
 * Subtle warm gradient overlay.
 */
@Composable
fun WalletWarningCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(WalletTheme.spacing.cardPadding),
    content: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.medium

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = shape,
                ambientColor = colors.warn.copy(alpha = 0.20f),
                spotColor = colors.warn.copy(alpha = 0.20f),
            )
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    listOf(colors.surface, colors.warn.copy(alpha = 0.10f))
                )
            )
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(contentPadding),
    ) {
        content()
    }
}

/**
 * AI suggestion card — branded gradient overlay on surface.
 * Equivalent to .ai-card / .ins.glow in CSS.
 *
 * Use for AI-detected transactions, AI insights with strong emphasis.
 */
@Composable
fun WalletAiCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(WalletTheme.spacing.cardPadding),
    content: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.medium

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = shape,
                ambientColor = colors.brand.copy(alpha = 0.15f),
                spotColor = colors.brand.copy(alpha = 0.15f),
            )
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    listOf(colors.surface, colors.brandAlt.copy(alpha = 0.10f))
                )
            )
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(contentPadding),
    ) {
        content()
    }
}
