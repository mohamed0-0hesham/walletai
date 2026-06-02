package com.coditria.walletai.designsystem.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.components.WalletIconButton
import com.walletai.core.designsystem.components.WalletMorePill
import com.walletai.core.designsystem.theme.WalletTheme

@Composable
fun WalletGreetingBar(
    greeting: String,
    name: String,
    avatarInitials: String,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
    hasNotifications: Boolean = false,
    notificationIcon: @Composable () -> Unit,
) {
    val colors = WalletTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // Gradient avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(WalletTheme.shapes.small)
                    .background(colors.brandGradient),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = avatarInitials,
                    color = Color.White,
                    style = WalletTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            }
            Column {
                Text(
                    text = greeting,
                    style = WalletTheme.typography.bodySmall,
                    color = colors.muted,
                )
                Text(
                    text = name,
                    style = WalletTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = colors.ink,
                )
            }
        }

        WalletIconButton(
            onClick = onNotificationClick,
            badged = hasNotifications,
            icon = notificationIcon,
        )
    }
}

/**
 * Standard back-bar — back button + title + optional trailing action.
 * Used on most screens that aren't the Dashboard.
 */
@Composable
fun WalletTopBar(
    title: String,
    onBack: () -> Unit,
    backIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    onTrailingClick: (() -> Unit)? = null,
) {
    val colors = WalletTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        WalletIconButton(
            onClick = onBack,
            size = 40.dp,
            icon = backIcon,
        )

        Text(
            text = title,
            style = WalletTheme.typography.headingMedium.copy(fontWeight = FontWeight.Bold),
            color = colors.ink,
        )

        if (trailingIcon != null && onTrailingClick != null) {
            WalletIconButton(
                onClick = onTrailingClick,
                size = 40.dp,
                icon = trailingIcon,
            )
        } else {
            // Spacer to keep title centered
            Box(modifier = Modifier.size(40.dp))
        }
    }
}

/**
 * Section header — bold title on left + optional "more" pill on right.
 * Equivalent to .sh in CSS.
 */
@Composable
fun WalletSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    moreText: String? = null,
    onMoreClick: (() -> Unit)? = null,
) {
    val colors = WalletTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = WalletTheme.typography.headingMedium,
            color = colors.ink,
        )
        if (moreText != null && onMoreClick != null) {
            WalletMorePill(text = moreText, onClick = onMoreClick)
        }
    }
}
