package com.walletai.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

enum class IconBgStyle {
    /** Soft brand tint. */
    Brand,
    /** Soft accent (teal). */
    Accent,
    /** Soft good (income). */
    Good,
    /** Soft warn. */
    Warn,
    /** Soft bad. */
    Bad,
    /** Neutral chip bg. */
    Neutral,
    /** Brand gradient solid (for installment cards). */
    BrandGradient,
    /** Accent gradient solid. */
    AccentGradient,
}

/**
 * Round/squircle icon container with colored background.
 * Used inside transaction rows, installment cards, AI insights.
 */
@Composable
fun WalletIconBox(
    style: IconBgStyle,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 38.dp,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.small

    val (bg, gradient) = when (style) {
        IconBgStyle.Brand -> colors.brand.copy(alpha = 0.10f) to null
        IconBgStyle.Accent -> colors.accent.copy(alpha = 0.14f) to null
        IconBgStyle.Good -> colors.good.copy(alpha = 0.12f) to null
        IconBgStyle.Warn -> colors.warn.copy(alpha = 0.14f) to null
        IconBgStyle.Bad -> colors.bad.copy(alpha = 0.12f) to null
        IconBgStyle.Neutral -> colors.chipBg to null
        IconBgStyle.BrandGradient -> Color.Transparent to colors.brandGradient
        IconBgStyle.AccentGradient -> Color.Transparent to Brush.linearGradient(
            listOf(colors.accent, colors.brand)
        )
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .let {
                if (gradient != null) it.background(gradient)
                else it.background(bg)
            },
        contentAlignment = Alignment.Center,
    ) {
        icon()
    }
}

/**
 * Transaction list row — used in dashboard and full transactions screen.
 * Wrap multiple in a [WalletCard] for the segmented look.
 *
 * Equivalent to .tx in CSS.
 */
@Composable
fun WalletTransactionRow(
    title: String,
    subtitle: String,
    amount: String,
    isIncome: Boolean,
    iconStyle: IconBgStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(WalletTheme.shapes.small)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        WalletIconBox(style = iconStyle, icon = icon)

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = WalletTheme.typography.headingSmall,
                color = colors.ink,
            )
            Text(
                text = subtitle,
                style = WalletTheme.typography.bodySmall,
                color = colors.muted,
            )
        }

        Text(
            text = amount,
            style = WalletTheme.typography.displaySmall,
            color = if (isIncome) colors.good else colors.ink,
        )
    }
}

/**
 * Installment card — name, due date, progress bar, monthly amount.
 * Equivalent to .inst-card / .inst-big in CSS.
 */
@Composable
fun WalletInstallmentCard(
    name: String,
    subtitle: String,
    monthlyAmount: String,
    currency: String,
    progress: Float,
    iconStyle: IconBgStyle = IconBgStyle.BrandGradient,
    progressStyle: ProgressStyle = ProgressStyle.Brand,
    onClick: (() -> Unit)? = null,
    isWarning: Boolean = false,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    statusChip: (@Composable () -> Unit)? = null,
) {
    val colors = WalletTheme.colors

    val cardContent: @Composable () -> Unit = {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                WalletIconBox(
                    style = iconStyle,
                    size = WalletTheme.spacing.installmentIcon,
                    icon = icon,
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = WalletTheme.typography.headingSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                        color = colors.ink,
                    )
                    Text(
                        text = subtitle,
                        style = WalletTheme.typography.bodySmall,
                        color = colors.muted,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = monthlyAmount,
                        style = WalletTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        color = colors.ink,
                    )
                    Text(
                        text = currency,
                        style = WalletTheme.typography.caption,
                        color = colors.muted,
                    )
                }
            }
            Box(modifier = Modifier.padding(top = 10.dp)) {
                WalletProgressBar(
                    progress = progress,
                    style = progressStyle,
                    height = 6.dp,
                )
            }
            if (statusChip != null) {
                Box(modifier = Modifier.padding(top = 10.dp)) {
                    statusChip()
                }
            }
        }
    }

    if (isWarning) {
        WalletWarningCard(
            modifier = modifier,
            onClick = onClick,
            content = cardContent,
        )
    } else {
        WalletCard(
            modifier = modifier,
            onClick = onClick,
            content = cardContent,
        )
    }
}
