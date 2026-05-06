package com.walletai.core.designsystem.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing scale based on a 4dp grid.
 *
 * Use semantic names where possible:
 * - screenPadding: outer page margins (18dp)
 * - cardPadding:   inside card content (16-18dp)
 * - sectionGap:    between vertical sections
 * - itemGap:       inside an item row (icon + text)
 */
@Immutable
data class WalletSpacing(
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 12.dp,
    val lg: Dp = 16.dp,
    val xl: Dp = 20.dp,
    val xxl: Dp = 24.dp,
    val xxxl: Dp = 32.dp,

    // Semantic
    val screenPadding: Dp = 18.dp,
    val cardPadding: Dp = 16.dp,
    val cardPaddingLarge: Dp = 22.dp,
    val sectionGap: Dp = 14.dp,
    val itemGap: Dp = 12.dp,
    val tightGap: Dp = 6.dp,

    // Component sizing
    val iconButton: Dp = 42.dp,
    val iconButtonSmall: Dp = 36.dp,
    val avatar: Dp = 40.dp,
    val avatarLarge: Dp = 54.dp,
    val listItemIcon: Dp = 38.dp,
    val installmentIcon: Dp = 42.dp,
    val fab: Dp = 60.dp,
    val ctaHeight: Dp = 52.dp,
    val inputHeight: Dp = 48.dp,
)

val DefaultSpacing = WalletSpacing()

val LocalWalletSpacing = staticCompositionLocalOf<WalletSpacing> {
    error("WalletSpacing not provided. Wrap your content in WalletTheme { ... }")
}
