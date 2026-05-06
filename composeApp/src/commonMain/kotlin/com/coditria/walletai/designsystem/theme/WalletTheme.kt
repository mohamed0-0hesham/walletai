package com.walletai.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontFamily
import com.walletai.core.designsystem.foundation.DarkWalletColors
import com.walletai.core.designsystem.foundation.DefaultElevation
import com.walletai.core.designsystem.foundation.DefaultShapes
import com.walletai.core.designsystem.foundation.DefaultSpacing
import com.walletai.core.designsystem.foundation.LightWalletColors
import com.walletai.core.designsystem.foundation.LocalWalletColors
import com.walletai.core.designsystem.foundation.LocalWalletElevation
import com.walletai.core.designsystem.foundation.LocalWalletShapes
import com.walletai.core.designsystem.foundation.LocalWalletSpacing
import com.walletai.core.designsystem.foundation.LocalWalletTypography
import com.walletai.core.designsystem.foundation.WalletColors
import com.walletai.core.designsystem.foundation.WalletElevation
import com.walletai.core.designsystem.foundation.WalletShapes
import com.walletai.core.designsystem.foundation.WalletSpacing
import com.walletai.core.designsystem.foundation.WalletTypography
import com.walletai.core.designsystem.foundation.walletTypography

/**
 * Root theme for WalletAI. Wrap your app content in this composable.
 *
 * Usage in App.kt:
 * ```
 * @Composable
 * fun App() {
 *     WalletTheme(darkTheme = isSystemInDarkTheme()) {
 *         Navigator { screen -> ... }
 *     }
 * }
 * ```
 *
 * Then access tokens via [WalletTheme.colors], [WalletTheme.typography], etc:
 * ```
 * Text(
 *     text = "...",
 *     style = WalletTheme.typography.headingMedium,
 *     color = WalletTheme.colors.ink,
 * )
 * ```
 *
 * Pass real font families when you load them on the platform target:
 * ```
 * WalletTheme(
 *     darkTheme = isSystemInDarkTheme(),
 *     displayFamily = plusJakartaFontFamily(),
 *     bodyFamily = ibmPlexArabicFontFamily(),
 * ) { ... }
 * ```
 */
@Composable
fun WalletTheme(
    darkTheme: Boolean = false,
    displayFamily: FontFamily = FontFamily.Default,
    bodyFamily: FontFamily = FontFamily.Default,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkWalletColors else LightWalletColors
    val typography = walletTypography(
        displayFamily = displayFamily,
        bodyFamily = bodyFamily,
    )
    val shapes = DefaultShapes
    val spacing = DefaultSpacing
    val elevation = DefaultElevation

    CompositionLocalProvider(
        LocalWalletColors provides colors,
        LocalWalletTypography provides typography,
        LocalWalletShapes provides shapes,
        LocalWalletSpacing provides spacing,
        LocalWalletElevation provides elevation,
        content = content,
    )
}

/**
 * Token accessor — `WalletTheme.colors`, `WalletTheme.typography`, etc.
 */
object WalletTheme {
    val colors: WalletColors
        @Composable
        @ReadOnlyComposable
        get() = LocalWalletColors.current

    val typography: WalletTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalWalletTypography.current

    val shapes: WalletShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalWalletShapes.current

    val spacing: WalletSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalWalletSpacing.current

    val elevation: WalletElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalWalletElevation.current
}
