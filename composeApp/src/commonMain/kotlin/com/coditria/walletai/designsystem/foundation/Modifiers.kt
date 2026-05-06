package com.walletai.core.designsystem.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Standard card surface — white in light, elevated dark in dark mode.
 * Equivalent to .ie-card / .inst-card / .tx-list in CSS.
 */
fun Modifier.walletCard(
    colors: WalletColors,
    shape: Shape,
): Modifier = this
    .shadow(
        elevation = 8.dp,
        shape = shape,
        ambientColor = Color(0x1014183C),
        spotColor = Color(0x1014183C),
    )
    .clip(shape)
    .background(colors.surface)

/**
 * Primary CTA / FAB shadow — purple-tinted glow.
 * Equivalent to --shadow-deep in CSS.
 */
fun Modifier.walletShadowDeep(
    shape: Shape,
    brand: Color,
): Modifier = this.shadow(
    elevation = 12.dp,
    shape = shape,
    ambientColor = brand.copy(alpha = 0.45f),
    spotColor = brand.copy(alpha = 0.45f),
)

/** Hairline border used on icon buttons, pills, etc. */
fun Modifier.walletHairline(
    colors: WalletColors,
    shape: Shape,
): Modifier = this.border(
    width = 0.5.dp,
    color = colors.line2,
    shape = shape,
)
