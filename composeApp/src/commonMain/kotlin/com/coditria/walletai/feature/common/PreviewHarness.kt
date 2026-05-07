package com.coditria.walletai.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.app.stringsFor
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Wraps a screen in the production composition (theme + strings + layout
 * direction) so `@Preview` composables render exactly like the running app.
 * Keeps every per-screen preview a one-liner.
 */
@Composable
fun WalletPreviewHarness(
    locale: AppLocale = AppLocale.Arabic,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    WalletTheme(darkTheme = darkTheme) {
        CompositionLocalProvider(
            LocalWalletStrings provides stringsFor(locale),
            LocalLayoutDirection provides if (locale == AppLocale.Arabic) LayoutDirection.Rtl else LayoutDirection.Ltr,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WalletTheme.colors.bg),
            ) { content() }
        }
    }
}
