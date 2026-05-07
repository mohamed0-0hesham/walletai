package com.coditria.walletai.feature.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.coditria.walletai.app.ArabicStrings
import com.coditria.walletai.app.EnglishStrings
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.feature.common.WalletIconWallet
import com.walletai.core.designsystem.foundation.walletShadowDeep
import com.walletai.core.designsystem.theme.WalletTheme
import kotlinx.coroutines.delay

/**
 * Splash — auto-advances to sign-in after a short delay.
 */
@Composable
fun SplashScreen(
    onContinue: () -> Unit,
) {
    val s = LocalWalletStrings.current
    LaunchedEffect(Unit) {
        delay(1600)
        onContinue()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2A2F66),
                        Color(0xFF0E1430),
                        Color(0xFF06081A),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(108.dp)
                    .walletShadowDeep(WalletTheme.shapes.large, WalletTheme.colors.brand)
                    .clip(WalletTheme.shapes.large)
                    .background(WalletTheme.colors.brandGradient),
                contentAlignment = Alignment.Center,
            ) {
                WalletIconWallet(size = 48.dp, color = Color.White)
            }
            Spacer(Modifier.height(28.dp))
            Text(
                text = s.appName,
                style = WalletTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = s.tagline,
                style = WalletTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.6f),
            )
            Spacer(Modifier.height(36.dp))
            ProgressShimmer()
            Spacer(Modifier.height(28.dp))
            Text(
                text = s.versionLoading,
                style = WalletTheme.typography.caption,
                color = Color.White.copy(alpha = 0.4f),
            )
        }
    }
}

@Composable
private fun ProgressShimmer() {
    val transition = rememberInfiniteTransition(label = "splash-progress")
    val offset by transition.animateFloat(
        initialValue = -0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer",
    )

    Box(
        modifier = Modifier
            .width(140.dp)
            .height(3.dp)
            .clip(WalletTheme.shapes.pill)
            .background(Color.White.copy(alpha = 0.10f)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(3.dp)
                .padding(start = (offset * 140).coerceAtLeast(0f).dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0xFF8E62FF),
                            Color.Transparent,
                        ),
                    ),
                ),
        )
    }
}

@Preview
@Composable
private fun SplashScreenArabicPreview() {
    WalletTheme(darkTheme = true) {
        CompositionLocalProvider(LocalWalletStrings provides ArabicStrings) {
            SplashScreen(onContinue = {})
        }
    }
}

@Preview
@Composable
private fun SplashScreenEnglishPreview() {
    WalletTheme(darkTheme = true) {
        CompositionLocalProvider(LocalWalletStrings provides EnglishStrings) {
            SplashScreen(onContinue = {})
        }
    }
}
