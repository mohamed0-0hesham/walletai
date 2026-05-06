package com.walletai.core.designsystem.foundation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

/**
 * Corner radii — three steps that match the design system's --r-sm/md/lg.
 *
 * - small  (14dp): chips, tiny pills, inline tags
 * - medium (18dp): cards, list items, input fields
 * - large  (24dp): hero cards, modal sheets, FAB
 * - extraLarge (30dp): splash logo, big visual containers
 * - pill: full radius for capsule shapes (chevron pills, status pills)
 */
@Immutable
data class WalletShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape,
    val extraLarge: RoundedCornerShape,
    val pill: RoundedCornerShape,
)

val DefaultShapes = WalletShapes(
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(30.dp),
    pill = RoundedCornerShape(percent = 50),
)

val LocalWalletShapes = staticCompositionLocalOf<WalletShapes> {
    error("WalletShapes not provided. Wrap your content in WalletTheme { ... }")
}
