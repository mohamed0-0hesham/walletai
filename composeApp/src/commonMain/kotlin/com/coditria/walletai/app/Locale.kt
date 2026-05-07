@file:OptIn(InternalResourceApi::class)
@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.coditria.walletai.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.ComposeEnvironment
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.LanguageQualifier
import org.jetbrains.compose.resources.LocalComposeEnvironment
import org.jetbrains.compose.resources.ResourceEnvironment

enum class AppLocale(val languageCode: String) {
    Arabic("ar"),
    English("en"),
}

/**
 * Forces every Compose Multiplatform `stringResource(...)` call inside [content]
 * to resolve against [locale], regardless of system locale. We override the
 * (internal) `LocalComposeEnvironment` because CMP 1.10 does not yet expose a
 * public hook for runtime locale switching.
 */
@Composable
fun ProvideAppLocale(
    locale: AppLocale,
    content: @Composable () -> Unit,
) {
    val parent = LocalComposeEnvironment.current
    val custom = remember(parent, locale) {
        object : ComposeEnvironment {
            @Composable
            override fun rememberEnvironment(): ResourceEnvironment {
                val base = parent.rememberEnvironment()
                return ResourceEnvironment(
                    language = LanguageQualifier(locale.languageCode),
                    region = base.region,
                    theme = base.theme,
                    density = base.density,
                )
            }
        }
    }
    CompositionLocalProvider(LocalComposeEnvironment provides custom) {
        content()
    }
}
