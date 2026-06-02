package com.walletai.core.designsystem.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Elevation tokens — Compose handles shadow rendering via Modifier.shadow().
 *
 * Note: the design uses two distinct shadow styles which can't be perfectly
 * replicated with a single elevation value, so use the Modifier.walletShadow*
 * extensions in [com.walletai.core.designsystem.foundation.Modifiers] for
 * more accurate shadows when needed.
 *
 * - none:       0dp — flat
 * - card:       1dp — subtle card lift  (--shadow-card)
 * - elevated:   8dp — emphasized cards
 * - deep:      12dp — primary CTAs, FAB (--shadow-deep)
 * - sheet:     30dp — modal sheets, phone shell
 */
@Immutable
data class WalletElevation(
    val none: Dp = 0.dp,
    val card: Dp = 1.dp,
    val elevated: Dp = 8.dp,
    val deep: Dp = 12.dp,
    val sheet: Dp = 30.dp,
)

val DefaultElevation = WalletElevation()

val LocalWalletElevation = staticCompositionLocalOf<WalletElevation> {
    error("WalletElevation not provided. Wrap your content in WalletTheme { ... }")
}
