package com.walletai.core.designsystem.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Typography tokens — mirrors the design system in WalletAI_Redesign.html.
 *
 * Two font families:
 * - **Display** (Plus Jakarta Sans): for amounts, balances, headings — better for numeric content
 * - **Body** (IBM Plex Sans Arabic): for body text — excellent Arabic support
 *
 * Pairing convention:
 * - Numbers / metrics → display family with tabular figures (set in Modifier or Text props)
 * - Arabic / English copy → body family
 *
 * Weights used: 400 (regular), 500 (medium), 600 (semibold), 700 (bold)
 */
@Immutable
data class WalletTypography(
    /** Hero balance amount: 42–48sp, tight tracking. */
    val displayHero: TextStyle,
    /** Section primary metrics (e.g. amount on add screen). */
    val displayLarge: TextStyle,
    /** Card metric values (income/expense pills). */
    val displayMedium: TextStyle,
    /** Compact tabular amounts in lists. */
    val displaySmall: TextStyle,

    /** Screen titles, big section headers. */
    val headingLarge: TextStyle,
    /** Card section titles ("التحليل"). */
    val headingMedium: TextStyle,
    /** Item titles (transaction name, installment name). */
    val headingSmall: TextStyle,

    /** Default body text. */
    val bodyLarge: TextStyle,
    /** Card body, descriptions. */
    val bodyMedium: TextStyle,
    /** Small labels, metadata. */
    val bodySmall: TextStyle,

    /** Pill / badge / chip labels — uppercase letterspaced. */
    val label: TextStyle,
    /** Tiny hint text — captions under inputs. */
    val caption: TextStyle,
    /** Button text. */
    val button: TextStyle,
)

/**
 * Build typography. Both font families default to system fallback;
 * inject the loaded resource families from your platform target.
 *
 * Android: load via androidx.compose.ui.text.googlefonts or font resources.
 * iOS: bundle .ttf/.otf and use FontFamily(Font(...)).
 */
fun walletTypography(
    displayFamily: FontFamily = FontFamily.Default,
    bodyFamily: FontFamily = FontFamily.Default,
): WalletTypography {
    return WalletTypography(
        displayHero = TextStyle(
            fontFamily = displayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 42.sp,
            lineHeight = 1.0.em,
            letterSpacing = (-1.4).sp,
        ),
        displayLarge = TextStyle(
            fontFamily = displayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            lineHeight = 1.0.em,
            letterSpacing = (-1.6).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = displayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 1.2.em,
            letterSpacing = (-0.4).sp,
        ),
        displaySmall = TextStyle(
            fontFamily = displayFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            letterSpacing = (-0.3).sp,
        ),

        headingLarge = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 1.3.em,
            letterSpacing = (-0.4).sp,
        ),
        headingMedium = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.5.sp,
            lineHeight = 1.4.em,
            letterSpacing = (-0.2).sp,
        ),
        headingSmall = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 1.4.em,
        ),

        bodyLarge = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 13.5.sp,
            lineHeight = 1.6.em,
        ),
        bodyMedium = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 1.55.em,
        ),
        bodySmall = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.5.sp,
            lineHeight = 1.5.em,
        ),

        label = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            lineHeight = 1.2.em,
            letterSpacing = 0.5.sp,  // approx 0.05em uppercase tracking
        ),
        caption = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = 1.5.em,
        ),
        button = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
        ),
    )
}

val LocalWalletTypography = staticCompositionLocalOf<WalletTypography> {
    error("WalletTypography not provided. Wrap your content in WalletTheme { ... }")
}
