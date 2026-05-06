package com.walletai.core.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

data class NavItem(
    val key: String,
    val label: String,
    val icon: @Composable (selected: Boolean) -> Unit,
)

/**
 * Floating bottom navigation with center FAB cutout.
 * Equivalent to .nav / .navi / .fab in CSS.
 *
 * Usage:
 * ```
 * WalletBottomNav(
 *     items = listOf(
 *         NavItem("home", "الرئيسية") { sel -> HomeIcon(selected = sel) },
 *         NavItem("transactions", "المعاملات") { sel -> ListIcon(selected = sel) },
 *     ),
 *     selectedKey = currentTab,
 *     onSelect = { currentTab = it },
 *     onFabClick = { /* open add transaction */ },
 *     fabIcon = { /* + icon */ },
 *     trailingItems = listOf(
 *         NavItem("reports", "التقارير") { sel -> ChartIcon(selected = sel) },
 *         NavItem("more", "المزيد") { sel -> MenuIcon(selected = sel) },
 *     ),
 * )
 * ```
 */
@Composable
fun WalletBottomNav(
    items: List<NavItem>,
    trailingItems: List<NavItem>,
    selectedKey: String,
    onSelect: (String) -> Unit,
    onFabClick: () -> Unit,
    fabIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = WalletTheme.colors
    val shape = WalletTheme.shapes.large

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = WalletTheme.spacing.screenPadding)
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter,
    ) {
        // Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = shape,
                    ambientColor = Color(0x1A14183C),
                    spotColor = Color(0x1A14183C),
                )
                .clip(shape)
                .background(colors.surface)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            items.forEach { item ->
                NavItemSlot(
                    item = item,
                    selected = item.key == selectedKey,
                    onClick = { onSelect(item.key) },
                    modifier = Modifier.weight(1f),
                )
            }
            // FAB spacer
            Spacer(modifier = Modifier.width(WalletTheme.spacing.fab + 8.dp).weight(1f, fill = false))
            trailingItems.forEach { item ->
                NavItemSlot(
                    item = item,
                    selected = item.key == selectedKey,
                    onClick = { onSelect(item.key) },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        // FAB centered, lifted above bar
        Box(modifier = Modifier.offset(y = (-32).dp)) {
            WalletFab(
                onClick = onFabClick,
                size = WalletTheme.spacing.fab,
                modifier = Modifier
                    .shadow(
                        elevation = 0.dp,
                        shape = WalletTheme.shapes.large,
                    )
                    .background(colors.surface, WalletTheme.shapes.large)
                    .padding(4.dp),
                icon = fabIcon,
            )
        }
    }
}

@Composable
private fun NavItemSlot(
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = WalletTheme.colors

    Box(
        modifier = modifier
            .clip(WalletTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Top accent bar (visible only when selected)
        AnimatedVisibility(
            visible = selected,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            Box(
                modifier = Modifier
                    .offset(y = (-6).dp)
                    .size(width = 24.dp, height = 3.dp)
                    .clip(WalletTheme.shapes.pill)
                    .background(colors.brandGradient),
            )
        }

        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            item.icon(selected)
            Text(
                text = item.label,
                style = WalletTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold),
                color = if (selected) colors.brand else colors.muted,
            )
        }
    }
}
