package com.walletai.core.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

enum class ProgressStyle {
    /** Brand gradient — default. */
    Brand,
    /** Teal-to-brand gradient — for "good progress" contexts. */
    Accent,
    /** Warn gradient (amber → red) — for high pressure / risk. */
    Warn,
}

/**
 * Progress bar — gradient fill with smooth animation.
 * Equivalent to .pg / .pgf / .ib-bgf in CSS.
 *
 * @param progress 0.0 to 1.0
 */
@Composable
fun WalletProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 5.dp,
    style: ProgressStyle = ProgressStyle.Brand,
    animated: Boolean = true,
) {
    val colors = WalletTheme.colors

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = if (animated) 1200 else 0),
        label = "progress",
    )

    val fillBrush: Brush = when (style) {
        ProgressStyle.Brand -> colors.brandGradient
        ProgressStyle.Accent -> Brush.linearGradient(listOf(colors.accent, colors.brand))
        ProgressStyle.Warn -> colors.warnGradient
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(WalletTheme.shapes.pill)
            .background(colors.line),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(WalletTheme.shapes.pill)
                .background(fillBrush),
        )
    }
}
