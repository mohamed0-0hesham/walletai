package com.walletai.core.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.foundation.walletShadowDeep
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Primary CTA — gradient brand button with press scale animation.
 * Use for: "حفظ المعاملة", main onboarding actions, sign-in.
 */
@Composable
fun WalletPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = WalletTheme.spacing.ctaHeight,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.medium
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.97f else 1f, label = "btn-scale")

    Box(
        modifier = modifier
            .scale(scale)
            .fillMaxWidth()
            .height(height)
            .walletShadowDeep(shape, colors.brand)
            .clip(shape)
            .background(colors.brandGradient)
            .clickable(
                enabled = enabled,
                interactionSource = interaction,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            leadingIcon?.invoke()
            Text(text = text, style = WalletTheme.typography.button, color = colors.onBrand)
            trailingIcon?.invoke()
        }
    }
}

/** Ghost / secondary button — chipBg fill, ink text. Use for "إلغاء", "تعديل". */
@Composable
fun WalletGhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = WalletTheme.spacing.ctaHeight,
    enabled: Boolean = true,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.medium
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.97f else 1f, label = "btn-scale")

    Box(
        modifier = modifier
            .scale(scale)
            .fillMaxWidth()
            .height(height)
            .clip(shape)
            .background(colors.chipBg)
            .clickable(
                enabled = enabled,
                interactionSource = interaction,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = WalletTheme.typography.button, color = colors.ink2)
    }
}

/** Floating Action Button — gradient with deep shadow. Bottom-nav center FAB. */
@Composable
fun WalletFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = WalletTheme.spacing.fab,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.large
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.92f else 1f, label = "fab-scale")

    Box(
        modifier = modifier
            .scale(scale)
            .size(size)
            .walletShadowDeep(shape, colors.brand)
            .clip(shape)
            .background(colors.brandGradient)
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        icon()
    }
}

/** Square icon button — top-bar (notifications, search, back). 42x42 with optional badge. */
@Composable
fun WalletIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = WalletTheme.spacing.iconButton,
    enabled: Boolean = true,
    badged: Boolean = false,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.small
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, label = "ic-btn-scale")

    Box(modifier = modifier.scale(scale).size(size)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(colors.surface)
                .clickable(
                    enabled = enabled,
                    interactionSource = interaction,
                    indication = null,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }
        if (badged) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-6).dp, y = 6.dp)
                    .size(8.dp)
                    .clip(WalletTheme.shapes.pill)
                    .background(colors.bad),
            )
        }
    }
}

/** Danger CTA — red gradient. Use sparingly: destructive actions only. */
@Composable
fun WalletDangerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = WalletTheme.spacing.ctaHeight,
    enabled: Boolean = true,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.medium

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape)
            .background(colors.badGradient)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = WalletTheme.typography.button, color = Color.White)
    }
}
