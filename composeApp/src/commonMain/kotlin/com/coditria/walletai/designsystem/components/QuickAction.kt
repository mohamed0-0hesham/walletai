package com.walletai.core.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.foundation.walletShadowDeep
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Quick action — large icon container with label below.
 * Equivalent to .qa / .qa-ico / .qa-l in CSS.
 *
 * Used on dashboard for "تسجيل صوتي", "إضافة يدوية", "أقساط", "ميزانية".
 *
 * Usage:
 * ```
 * Row {
 *     WalletQuickAction(
 *         label = "تسجيل صوتي",
 *         primary = true,
 *         onClick = { ... },
 *     ) { MicIcon(tint = Color.White) }
 *
 *     WalletQuickAction(
 *         label = "إضافة يدوية",
 *         onClick = { ... },
 *     ) { PlusIcon() }
 * }
 * ```
 */
@Composable
fun WalletQuickAction(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 54.dp,
    primary: Boolean = false,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.medium
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        label = "qa-scale",
    )

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .scale(scale)
                .size(iconSize)
                .let {
                    if (primary) {
                        it.walletShadowDeep(shape, colors.brand)
                    } else {
                        it.shadow(8.dp, shape, ambientColor = Color(0x1014183C))
                    }
                }
                .clip(shape)
                .background(
                    if (primary) colors.brandGradient
                    else androidx.compose.ui.graphics.Brush.linearGradient(
                        listOf(colors.surface, colors.surface)
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            style = WalletTheme.typography.caption.copy(
                fontWeight = if (primary) FontWeight.SemiBold else FontWeight.Medium,
            ),
            color = if (primary) colors.ink else colors.ink2,
        )
    }
}
