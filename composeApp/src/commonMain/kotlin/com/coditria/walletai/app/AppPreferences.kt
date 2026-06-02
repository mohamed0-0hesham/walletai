package com.coditria.walletai.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * App-wide preferences hoisted to the composition root. The Settings screen
 * mutates these and every screen reacts immediately — theme via [WalletTheme],
 * locale via [LocalWalletStrings] and [LocalLayoutDirection].
 */
class AppPreferences(
    initialDarkMode: Boolean = false,
    initialLocale: AppLocale = AppLocale.Arabic,
) {
    var darkMode: Boolean by mutableStateOf(initialDarkMode)
        private set

    var locale: AppLocale by mutableStateOf(initialLocale)
        private set

    fun selectDarkMode(value: Boolean) { darkMode = value }
    fun selectLocale(value: AppLocale) { locale = value }
}
