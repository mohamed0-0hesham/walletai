package com.walletai.core.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme
import kotlinx.coroutines.delay

/**
 * Status pill for "recording", "live", "syncing" — small dot + label.
 * Equivalent to .v-stat / .vc-trbd in CSS.
 */
@Composable
fun WalletStatusPill(
    text: String,
    modifier: Modifier = Modifier,
    style: ChipStyle = ChipStyle.Bad,
    pulsing: Boolean = false,
) {
    val colors = WalletTheme.colors

    val (bg, dotColor, textColor, borderColor) = when (style) {
        ChipStyle.Bad -> StatusColors(
            bg = colors.bad.copy(alpha = 0.16f),
            dot = colors.bad,
            text = Color(0xFFFFB1B9),
            border = colors.bad.copy(alpha = 0.28f),
        )
        ChipStyle.Brand -> StatusColors(
            bg = colors.brand.copy(alpha = 0.18f),
            dot = colors.brand,
            text = Color(0xFFA5B0FF),
            border = colors.brand.copy(alpha = 0.26f),
        )
        ChipStyle.Good -> StatusColors(
            bg = colors.good.copy(alpha = 0.14f),
            dot = colors.good,
            text = colors.good,
            border = colors.good.copy(alpha = 0.28f),
        )
        else -> StatusColors(
            bg = colors.chipBg,
            dot = colors.muted,
            text = colors.ink2,
            border = colors.line2,
        )
    }

    Row(
        modifier = modifier
            .clip(WalletTheme.shapes.pill)
            .background(bg)
            .border(1.dp, borderColor, WalletTheme.shapes.pill)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(WalletTheme.shapes.pill)
                .background(dotColor),
        )
        Text(
            text = text,
            style = WalletTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = textColor,
        )
    }
}

private data class StatusColors(
    val bg: Color,
    val dot: Color,
    val text: Color,
    val border: Color,
)

/**
 * Confidence bar — used inside AI suggestion cards.
 * Equivalent to .cf in CSS.
 *
 * @param confidence 0.0 to 1.0
 */
@Composable
fun WalletConfidenceBar(
    confidence: Float,
    modifier: Modifier = Modifier,
    label: String = "دقة الـ AI",
) {
    val colors = WalletTheme.colors
    val percent = (confidence * 100).toInt()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            style = WalletTheme.typography.bodySmall,
            color = colors.muted,
        )
        Box(modifier = Modifier.weight(1f)) {
            WalletProgressBar(
                progress = confidence,
                style = ProgressStyle.Accent,
                height = 5.dp,
            )
        }
        Text(
            text = "${percent}%",
            style = WalletTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = colors.good,
        )
    }
}

/**
 * Toast — auto-dismiss success notification at top of screen.
 * Equivalent to .toast in CSS.
 *
 * Usage:
 * ```
 * val toastVisible = remember { mutableStateOf(false) }
 *
 * Box {
 *     YourScreen()
 *     WalletToast(
 *         visible = toastVisible.value,
 *         text = "تم حفظ المعاملة",
 *         onDismiss = { toastVisible.value = false },
 *         icon = { CheckIcon() },
 *     )
 * }
 * ```
 */
@Composable
fun WalletToast(
    visible: Boolean,
    text: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Long = 1500,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors

    LaunchedEffect(visible) {
        if (visible) {
            delay(durationMillis)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { -it } + fadeIn(),
        exit = slideOutVertically { -it } + fadeOut(),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 12.dp,
                    shape = WalletTheme.shapes.medium,
                    ambientColor = colors.good.copy(alpha = 0.35f),
                    spotColor = colors.good.copy(alpha = 0.35f),
                )
                .clip(WalletTheme.shapes.medium)
                .background(colors.goodGradient)
                .padding(horizontal = 18.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            icon()
            Text(
                text = text,
                style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White,
            )
        }
    }
}


