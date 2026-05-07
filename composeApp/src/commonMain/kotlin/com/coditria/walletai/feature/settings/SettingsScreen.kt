package com.coditria.walletai.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.AppPreferences
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.data.InMemoryWalletRepository
import com.coditria.walletai.resources.Res
import com.coditria.walletai.resources.linked_accounts
import org.jetbrains.compose.resources.stringResource
import com.coditria.walletai.domain.model.User
import com.coditria.walletai.domain.repository.WalletRepository
import com.coditria.walletai.feature.common.WalletAppBottomNav
import com.coditria.walletai.feature.common.WalletPreviewHarness
import com.coditria.walletai.feature.common.WalletIconBell
import com.coditria.walletai.feature.common.WalletIconCard
import com.coditria.walletai.feature.common.WalletIconChevronLeft
import com.coditria.walletai.feature.common.WalletIconClock
import com.coditria.walletai.feature.common.WalletIconClose
import com.coditria.walletai.feature.common.WalletIconLock
import com.coditria.walletai.feature.common.WalletIconSparkles
import com.coditria.walletai.feature.common.WalletIconUser
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.coditria.walletai.navigation.Route
import com.walletai.core.designsystem.components.ChipStyle
import com.walletai.core.designsystem.components.WalletChip
import com.coditria.walletai.designsystem.components.WalletTopBar
import com.walletai.core.designsystem.theme.WalletTheme

class SettingsViewModel(repository: WalletRepository) {
    val user: User = repository.currentUser()

    var notificationsEnabled by mutableStateOf(true)
        private set
    var biometricsEnabled by mutableStateOf(true)
        private set
    var twoFactorEnabled by mutableStateOf(false)
        private set

    fun toggleNotifications() { notificationsEnabled = !notificationsEnabled }
    fun toggleBiometrics() { biometricsEnabled = !biometricsEnabled }
    fun toggleTwoFactor() { twoFactorEnabled = !twoFactorEnabled }
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    preferences: AppPreferences,
    onBack: () -> Unit,
    onSignOut: () -> Unit,
    onAdd: () -> Unit,
    onSelect: (Route) -> Unit,
) {
    val s = LocalWalletStrings.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WalletTheme.colors.bg),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            WalletPhoneStatusBar()
            WalletTopBar(
                title = s.settings,
                onBack = onBack,
                backIcon = { WalletIconChevronLeft(color = WalletTheme.colors.ink2) },
            )
            Spacer(Modifier.height(8.dp))

            ProfileCard(viewModel.user)
            Spacer(Modifier.height(14.dp))

            SettingsGroup(title = s.accountSection) {
                SettingsRow(
                    title = s.profile,
                    subtitle = s.profileSub,
                    iconTint = WalletTheme.colors.brand,
                    iconBg = WalletTheme.colors.brand.copy(alpha = 0.10f),
                    icon = { WalletIconUser(color = WalletTheme.colors.brand) },
                    trailing = { ChevronTrailing() },
                )
                Divider()
                SettingsRow(
                    title = s.accountsCards,
                    subtitle = stringResource(Res.string.linked_accounts, 3),
                    iconTint = WalletTheme.colors.good,
                    iconBg = WalletTheme.colors.good.copy(alpha = 0.10f),
                    icon = { WalletIconCard(size = 17.dp, color = WalletTheme.colors.good) },
                    trailing = { ValueText("3") },
                )
                Divider()
                SettingsRow(
                    title = s.limitsBudgets,
                    subtitle = s.setMonthlyBudget,
                    iconTint = WalletTheme.colors.warnDeep,
                    iconBg = WalletTheme.colors.warn.copy(alpha = 0.14f),
                    icon = { WalletIconClock(color = WalletTheme.colors.warnDeep) },
                    trailing = { ChevronTrailing() },
                )
            }

            Spacer(Modifier.height(14.dp))
            SettingsGroup(title = s.preferences) {
                SettingsRow(
                    title = s.appearance,
                    subtitle = s.appearanceSub,
                    iconTint = WalletTheme.colors.brandAlt,
                    iconBg = WalletTheme.colors.brandAlt.copy(alpha = 0.12f),
                    icon = { WalletIconSparkles(color = WalletTheme.colors.brandAlt) },
                    trailing = {
                        SegmentedToggle(
                            options = s.light to s.dark,
                            selectedRight = preferences.darkMode,
                            onSelect = preferences::selectDarkMode,
                        )
                    },
                )
                Divider()
                SettingsRow(
                    title = s.language,
                    subtitle = s.languageSub,
                    iconTint = WalletTheme.colors.brand,
                    iconBg = WalletTheme.colors.brand.copy(alpha = 0.10f),
                    icon = { WalletIconSparkles(color = WalletTheme.colors.brand) },
                    trailing = {
                        SegmentedToggle(
                            options = s.arabic to s.english,
                            selectedRight = preferences.locale == AppLocale.English,
                            onSelect = { isEnglish ->
                                preferences.selectLocale(if (isEnglish) AppLocale.English else AppLocale.Arabic)
                            },
                        )
                    },
                )
                Divider()
                SettingsRow(
                    title = s.currency,
                    subtitle = s.egyptianPound,
                    iconTint = WalletTheme.colors.good,
                    iconBg = WalletTheme.colors.good.copy(alpha = 0.10f),
                    icon = { Text("£", style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = WalletTheme.colors.good) },
                    trailing = { ValueText("EGP") },
                )
                Divider()
                SettingsRow(
                    title = s.notifications,
                    subtitle = s.notificationsSub,
                    iconTint = WalletTheme.colors.warnDeep,
                    iconBg = WalletTheme.colors.warn.copy(alpha = 0.14f),
                    icon = { WalletIconBell(color = WalletTheme.colors.warnDeep) },
                    trailing = {
                        WalletSwitch(
                            checked = viewModel.notificationsEnabled,
                            onToggle = viewModel::toggleNotifications,
                        )
                    },
                )
            }

            Spacer(Modifier.height(14.dp))
            SettingsGroup(title = s.securityPrivacy) {
                SettingsRow(
                    title = s.changePassword,
                    subtitle = s.changePasswordSub,
                    iconTint = WalletTheme.colors.brandAlt,
                    iconBg = WalletTheme.colors.brandAlt.copy(alpha = 0.12f),
                    icon = { WalletIconLock(color = WalletTheme.colors.brandAlt) },
                    trailing = { ChevronTrailing() },
                )
                Divider()
                SettingsRow(
                    title = s.biometrics,
                    subtitle = s.biometricsSub,
                    iconTint = WalletTheme.colors.brand,
                    iconBg = WalletTheme.colors.brand.copy(alpha = 0.10f),
                    icon = { WalletIconUser(color = WalletTheme.colors.brand) },
                    trailing = {
                        WalletSwitch(
                            checked = viewModel.biometricsEnabled,
                            onToggle = viewModel::toggleBiometrics,
                        )
                    },
                )
                Divider()
                SettingsRow(
                    title = s.twoFactor,
                    subtitle = s.twoFactorSub,
                    iconTint = WalletTheme.colors.good,
                    iconBg = WalletTheme.colors.good.copy(alpha = 0.10f),
                    icon = { WalletIconLock(color = WalletTheme.colors.good) },
                    trailing = {
                        WalletSwitch(
                            checked = viewModel.twoFactorEnabled,
                            onToggle = viewModel::toggleTwoFactor,
                        )
                    },
                )
            }

            Spacer(Modifier.height(14.dp))
            SettingsGroup(title = s.support) {
                SettingsRow(
                    title = s.helpCenter,
                    iconTint = WalletTheme.colors.ink2,
                    iconBg = WalletTheme.colors.chipBg,
                    icon = { Text("?", style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = WalletTheme.colors.ink2) },
                    trailing = { ChevronTrailing() },
                )
                Divider()
                SettingsRow(
                    title = s.termsPrivacy,
                    iconTint = WalletTheme.colors.ink2,
                    iconBg = WalletTheme.colors.chipBg,
                    icon = { WalletIconLock(color = WalletTheme.colors.ink2) },
                    trailing = { ChevronTrailing() },
                )
                Divider()
                SettingsRow(
                    title = s.signOut,
                    titleColor = WalletTheme.colors.bad,
                    iconTint = WalletTheme.colors.bad,
                    iconBg = WalletTheme.colors.bad.copy(alpha = 0.12f),
                    icon = { WalletIconClose(color = WalletTheme.colors.bad) },
                    onClick = onSignOut,
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                s.versionLine,
                style = WalletTheme.typography.caption,
                color = WalletTheme.colors.muted,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            Spacer(Modifier.height(110.dp))
        }

        WalletAppBottomNav(
            selected = Route.Settings,
            onSelect = onSelect,
            onAddTransaction = onAdd,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun ProfileCard(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface)
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(WalletTheme.shapes.medium)
                .background(WalletTheme.colors.brandGradient),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                user.avatarInitial,
                style = WalletTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "${user.firstName} ${user.lastName}",
                style = WalletTheme.typography.headingSmall.copy(fontWeight = FontWeight.Bold),
                color = WalletTheme.colors.ink,
            )
            Text(
                user.email,
                style = WalletTheme.typography.caption,
                color = WalletTheme.colors.muted,
            )
            Spacer(Modifier.height(6.dp))
            WalletChip(text = LocalWalletStrings.current.proMembership, style = ChipStyle.Brand)
        }
    }
}

@Composable
private fun SettingsGroup(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface),
    ) {
        Text(
            title,
            style = WalletTheme.typography.label,
            color = WalletTheme.colors.muted,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )
        content()
    }
}

@Composable
private fun SettingsRow(
    title: String,
    iconBg: Color,
    iconTint: Color,
    icon: @Composable () -> Unit,
    subtitle: String? = null,
    titleColor: Color = WalletTheme.colors.ink,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(WalletTheme.shapes.small)
                .background(iconBg),
            contentAlignment = Alignment.Center,
        ) { icon() }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = WalletTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = titleColor,
            )
            if (subtitle != null) {
                Text(
                    subtitle,
                    style = WalletTheme.typography.caption,
                    color = WalletTheme.colors.muted,
                )
            }
        }
        trailing?.invoke()
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 62.dp, end = 16.dp)
            .height(1.dp)
            .background(WalletTheme.colors.line),
    )
}

@Composable
private fun ChevronTrailing() {
    WalletIconChevronLeft(size = 16.dp, color = WalletTheme.colors.muted)
}

@Composable
private fun ValueText(text: String) {
    Text(
        text,
        style = WalletTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
        color = WalletTheme.colors.ink2,
    )
}

@Composable
private fun WalletSwitch(checked: Boolean, onToggle: () -> Unit) {
    val width = 42.dp
    val height = 24.dp
    val knob = 18.dp
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(WalletTheme.shapes.pill)
            .background(if (checked) WalletTheme.colors.brand else WalletTheme.colors.line2)
            .clickable(onClick = onToggle),
    ) {
        Box(
            modifier = Modifier
                .padding(3.dp)
                .size(knob)
                .clip(CircleShape)
                .background(Color.White)
                .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart),
        )
    }
}

@Composable
private fun SegmentedToggle(
    options: Pair<String, String>,
    selectedRight: Boolean,
    onSelect: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(WalletTheme.shapes.small)
            .background(WalletTheme.colors.chipBg)
            .padding(3.dp),
    ) {
        SegToggleItem(label = options.first, selected = !selectedRight) { onSelect(false) }
        SegToggleItem(label = options.second, selected = selectedRight) { onSelect(true) }
    }
}

@Composable
private fun SegToggleItem(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(WalletTheme.shapes.small)
            .background(if (selected) WalletTheme.colors.surface else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 5.dp),
    ) {
        Text(
            label,
            style = WalletTheme.typography.caption.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            ),
            color = if (selected) WalletTheme.colors.ink else WalletTheme.colors.muted,
        )
    }
}

@Preview
@Composable
private fun SettingsScreenArabicPreview() {
    WalletPreviewHarness(locale = AppLocale.Arabic) {
        val vm = remember { SettingsViewModel(InMemoryWalletRepository()) }
        val prefs = remember { AppPreferences(initialLocale = AppLocale.Arabic) }
        SettingsScreen(
            viewModel = vm,
            preferences = prefs,
            onBack = {}, onSignOut = {}, onAdd = {}, onSelect = {},
        )
    }
}

@Preview
@Composable
private fun SettingsScreenEnglishDarkPreview() {
    WalletPreviewHarness(locale = AppLocale.English, darkTheme = true) {
        val vm = remember { SettingsViewModel(InMemoryWalletRepository()) }
        val prefs = remember { AppPreferences(initialDarkMode = true, initialLocale = AppLocale.English) }
        SettingsScreen(
            viewModel = vm,
            preferences = prefs,
            onBack = {}, onSignOut = {}, onAdd = {}, onSelect = {},
        )
    }
}
