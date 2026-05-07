package com.coditria.walletai.feature.auth

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.coditria.walletai.app.AppLocale
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.feature.common.WalletIconChevronLeft
import com.coditria.walletai.feature.common.WalletPreviewHarness
import com.coditria.walletai.feature.common.WalletIconEye
import com.coditria.walletai.feature.common.WalletIconLock
import com.coditria.walletai.feature.common.WalletIconMail
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.walletai.core.designsystem.components.WalletPrimaryButton
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * SignIn — auth hero header + email/password card + social row.
 *
 * Pure stateless screen: caller drives navigation, ViewModel holds the form state.
 */
@Composable
fun SignInScreen(
    onSignIn: () -> Unit,
    onGoToSignUp: () -> Unit,
    onBack: () -> Unit,
    viewModel: SignInViewModel = remember { SignInViewModel() },
) {
    val state = viewModel.state
    val s = LocalWalletStrings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WalletTheme.colors.bg)
            .verticalScroll(rememberScrollState()),
    ) {
        WalletPhoneStatusBar(onDark = true)
        AuthHero(
            title = s.welcomeBack,
            subtitle = s.welcomeBackSub,
            onBack = onBack,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .offset(y = (-22).dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(WalletTheme.shapes.large)
                    .background(WalletTheme.colors.surface)
                    .padding(22.dp),
            ) {
                AuthField(
                    label = s.email,
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    placeholder = "hisham@example.com",
                    leadingIcon = { WalletIconMail(color = WalletTheme.colors.muted) },
                )
                Spacer(Modifier.height(14.dp))
                AuthField(
                    label = s.password,
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = "••••••••",
                    leadingIcon = { WalletIconLock(color = WalletTheme.colors.muted) },
                    trailingIcon = {
                        WalletIconEye(
                            color = WalletTheme.colors.muted,
                            modifier = Modifier.clip(WalletTheme.shapes.small),
                        )
                    },
                    isPassword = !state.passwordVisible,
                )
                Spacer(Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AuthCheckbox(
                        label = s.rememberMe,
                        checked = state.rememberMe,
                        onToggle = viewModel::onToggleRemember,
                    )
                    Text(
                        text = s.forgotPassword,
                        style = WalletTheme.typography.bodyMedium,
                        color = WalletTheme.colors.brand,
                    )
                }
                Spacer(Modifier.height(18.dp))
                WalletPrimaryButton(
                    text = s.signIn,
                    onClick = onSignIn,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(18.dp))
                AuthDivider()
                Spacer(Modifier.height(14.dp))
                SocialRow()
                Spacer(Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = s.noAccount + " ",
                        style = WalletTheme.typography.bodyMedium,
                        color = WalletTheme.colors.muted,
                    )
                    Text(
                        text = s.signUp,
                        style = WalletTheme.typography.bodyMedium,
                        color = WalletTheme.colors.brand,
                        modifier = Modifier
                            .clip(WalletTheme.shapes.small)
                            .clickable(onClick = onGoToSignUp),
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun AuthDivider() {
    val s = LocalWalletStrings.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(WalletTheme.colors.line),
        )
        Text(
            text = s.orSignInWith,
            style = WalletTheme.typography.caption,
            color = WalletTheme.colors.muted,
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(WalletTheme.colors.line),
        )
    }
}

@Composable
private fun SocialRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        SocialButton("Google", modifier = Modifier.weight(1f))
        SocialButton("Apple", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SocialButton(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(WalletTheme.shapes.medium)
            .background(WalletTheme.colors.surface),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = WalletTheme.typography.button,
            color = WalletTheme.colors.ink,
        )
    }
}


@Preview
@Composable
private fun SignInScreenArabicPreview() {
    WalletPreviewHarness(locale = AppLocale.Arabic) {
        SignInScreen(onSignIn = {}, onGoToSignUp = {}, onBack = {})
    }
}

@Preview
@Composable
private fun SignInScreenEnglishPreview() {
    WalletPreviewHarness(locale = AppLocale.English) {
        SignInScreen(onSignIn = {}, onGoToSignUp = {}, onBack = {})
    }
}
