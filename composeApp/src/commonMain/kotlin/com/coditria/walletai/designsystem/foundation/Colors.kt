package com.walletai.core.designsystem.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Raw color palette — not used directly in UI.
 * Use [WalletColors] via LocalWalletColors instead.
 */
internal object ColorPalette {

    // Brand gradients
    val GradientA = Color(0xFF5B6CFF) // Primary indigo
    val GradientB = Color(0xFF8E62FF) // Purple
    val GradientC = Color(0xFF3DD2C0) // Teal accent

    // Light theme
    val LightBg = Color(0xFFF4F5F9)
    val LightBg2 = Color(0xFFEEF0F6)
    val LightSurface = Color(0xFFFFFFFF)
    val LightSurface2 = Color(0xFFFFFFFF)
    val LightInk = Color(0xFF0E1430)
    val LightInk2 = Color(0xFF3A4060)
    val LightMuted = Color(0xFF7A819E)
    val LightLine = Color(0x0F14183C)        // rgba(20,24,60,0.06)
    val LightLine2 = Color(0x1A14183C)       // rgba(20,24,60,0.10)
    val LightChipBg = Color(0x0A14183C)      // rgba(20,24,60,0.04)
    val LightTxHover = Color(0x0614183C)     // rgba(20,24,60,0.025)
    val LightPhoneShell = Color(0xFF1A1D2E)

    // Dark theme
    val DarkBg = Color(0xFF0B0E22)
    val DarkBg2 = Color(0xFF101427)
    val DarkSurface = Color(0xFF161A33)
    val DarkSurface2 = Color(0xFF1E2342)
    val DarkInk = Color(0xFFF1F3FB)
    val DarkInk2 = Color(0xFFC7CCE6)
    val DarkMuted = Color(0xFF8A91B5)
    val DarkLine = Color(0x0FFFFFFF)         // rgba(255,255,255,0.06)
    val DarkLine2 = Color(0x1AFFFFFF)        // rgba(255,255,255,0.10)
    val DarkChipBg = Color(0x0FFFFFFF)
    val DarkTxHover = Color(0x0AFFFFFF)
    val DarkPhoneShell = Color(0xFF05071A)

    // Semantic — same in both themes (with subtle adaptations via alpha)
    val Good = Color(0xFF22C58B)
    val GoodDeep = Color(0xFF0FA572)
    val Bad = Color(0xFFF26B7A)
    val BadDeep = Color(0xFFE84B5C)
    val Warn = Color(0xFFF0A24A)
    val WarnDeep = Color(0xFFD4842B)

    // Pure
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
}

/**
 * Theme-aware color tokens. Reference these via [WalletTheme.colors].
 *
 * Naming follows the design system:
 * - bg / bg2:  page backgrounds
 * - surface:   cards, sheets
 * - ink / ink2: primary / secondary text
 * - muted:      tertiary text
 * - line:       hairline borders
 * - chipBg:     pill / chip background
 * - good / bad / warn: semantic colors
 */
@Immutable
data class WalletColors(
    val bg: Color,
    val bg2: Color,
    val surface: Color,
    val surface2: Color,
    val ink: Color,
    val ink2: Color,
    val muted: Color,
    val line: Color,
    val line2: Color,
    val chipBg: Color,
    val txHover: Color,
    val phoneShell: Color,

    // Brand
    val brand: Color,         // GradientA — primary indigo
    val brandAlt: Color,      // GradientB — purple
    val accent: Color,        // GradientC — teal

    // Semantic
    val good: Color,
    val goodDeep: Color,
    val bad: Color,
    val badDeep: Color,
    val warn: Color,
    val warnDeep: Color,

    // Constants
    val onBrand: Color,       // text/icons that sit on brand gradient
    val white: Color,

    val isDark: Boolean,
) {
    /** Brand gradient for primary CTAs and FAB. */
    val brandGradient: Brush
        get() = Brush.linearGradient(listOf(brand, brandAlt))

    /** Hero balance card background — heavy radial mix. */
    val balanceGradient: Brush
        get() = Brush.linearGradient(
            colors = listOf(brand, Color(0xFF262C56), Color(0xFF101427))
        )

    /** Subtle AI suggestion card background. */
    val aiSuggestionGradient: Brush
        get() = Brush.linearGradient(
            colors = listOf(
                brand.copy(alpha = 0.06f),
                brandAlt.copy(alpha = 0.04f)
            )
        )

    /** Success toast / positive deltas. */
    val goodGradient: Brush
        get() = Brush.linearGradient(listOf(good, goodDeep))

    /** Danger CTA (e.g. expense segment selected). */
    val badGradient: Brush
        get() = Brush.linearGradient(listOf(bad, badDeep))

    /** Confidence bar fill. */
    val confidenceGradient: Brush
        get() = Brush.linearGradient(listOf(accent, good))

    /** Warning chips (heatmap high pressure). */
    val warnGradient: Brush
        get() = Brush.linearGradient(listOf(warn, bad))
}

internal val LightWalletColors = WalletColors(
    bg = ColorPalette.LightBg,
    bg2 = ColorPalette.LightBg2,
    surface = ColorPalette.LightSurface,
    surface2 = ColorPalette.LightSurface2,
    ink = ColorPalette.LightInk,
    ink2 = ColorPalette.LightInk2,
    muted = ColorPalette.LightMuted,
    line = ColorPalette.LightLine,
    line2 = ColorPalette.LightLine2,
    chipBg = ColorPalette.LightChipBg,
    txHover = ColorPalette.LightTxHover,
    phoneShell = ColorPalette.LightPhoneShell,
    brand = ColorPalette.GradientA,
    brandAlt = ColorPalette.GradientB,
    accent = ColorPalette.GradientC,
    good = ColorPalette.Good,
    goodDeep = ColorPalette.GoodDeep,
    bad = ColorPalette.Bad,
    badDeep = ColorPalette.BadDeep,
    warn = ColorPalette.Warn,
    warnDeep = ColorPalette.WarnDeep,
    onBrand = ColorPalette.White,
    white = ColorPalette.White,
    isDark = false,
)

internal val DarkWalletColors = WalletColors(
    bg = ColorPalette.DarkBg,
    bg2 = ColorPalette.DarkBg2,
    surface = ColorPalette.DarkSurface,
    surface2 = ColorPalette.DarkSurface2,
    ink = ColorPalette.DarkInk,
    ink2 = ColorPalette.DarkInk2,
    muted = ColorPalette.DarkMuted,
    line = ColorPalette.DarkLine,
    line2 = ColorPalette.DarkLine2,
    chipBg = ColorPalette.DarkChipBg,
    txHover = ColorPalette.DarkTxHover,
    phoneShell = ColorPalette.DarkPhoneShell,
    brand = ColorPalette.GradientA,
    brandAlt = ColorPalette.GradientB,
    accent = ColorPalette.GradientC,
    good = ColorPalette.Good,
    goodDeep = ColorPalette.GoodDeep,
    bad = ColorPalette.Bad,
    badDeep = ColorPalette.BadDeep,
    warn = ColorPalette.Warn,
    warnDeep = ColorPalette.WarnDeep,
    onBrand = ColorPalette.White,
    white = ColorPalette.White,
    isDark = true,
)

val LocalWalletColors = staticCompositionLocalOf<WalletColors> {
    error("WalletColors not provided. Wrap your content in WalletTheme { ... }")
}
