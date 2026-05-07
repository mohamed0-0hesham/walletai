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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.feature.common.WalletIconEye
import com.coditria.walletai.feature.common.WalletIconLock
import com.coditria.walletai.feature.common.WalletIconMail
import com.coditria.walletai.feature.common.WalletIconPhone
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.walletai.core.designsystem.components.WalletPrimaryButton
import com.walletai.core.designsystem.theme.WalletTheme

data class SignUpState(
    val firstName: String = "هشام",
    val lastName: String = "محمد",
    val email: String = "",
    val phone: String = "",
    val password: String = "strongPass#9",
    val passwordStrength: Int = 3, // out of 4
    val termsAccepted: Boolean = true,
)

class SignUpViewModel {
    var state: SignUpState by mutableStateOf(SignUpState())
        private set
    fun onFirstNameChange(v: String) { state = state.copy(firstName = v) }
    fun onLastNameChange(v: String) { state = state.copy(lastName = v) }
    fun onEmailChange(v: String) { state = state.copy(email = v) }
    fun onPhoneChange(v: String) { state = state.copy(phone = v) }
    fun onPasswordChange(v: String) {
        state = state.copy(
            password = v,
            passwordStrength = when {
                v.length < 6 -> 1
                v.length < 9 -> 2
                v.any { it.isDigit() } && v.any { !it.isLetterOrDigit() } -> 3
                else -> 2
            },
        )
    }
    fun onToggleTerms() { state = state.copy(termsAccepted = !state.termsAccepted) }
}

@Composable
fun SignUpScreen(
    onSignUp: () -> Unit,
    onGoToSignIn: () -> Unit,
    onBack: () -> Unit,
    viewModel: SignUpViewModel = remember { SignUpViewModel() },
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
            title = s.createTitle,
            subtitle = s.createSub,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    AuthField(
                        label = s.firstName,
                        value = state.firstName,
                        onValueChange = viewModel::onFirstNameChange,
                        placeholder = "",
                        modifier = Modifier.weight(1f),
                    )
                    AuthField(
                        label = s.lastName,
                        value = state.lastName,
                        onValueChange = viewModel::onLastNameChange,
                        placeholder = "",
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(Modifier.height(14.dp))
                AuthField(
                    label = s.email,
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    placeholder = "hisham@example.com",
                    leadingIcon = { WalletIconMail(color = WalletTheme.colors.muted) },
                )
                Spacer(Modifier.height(14.dp))
                AuthField(
                    label = s.mobile,
                    value = state.phone,
                    onValueChange = viewModel::onPhoneChange,
                    placeholder = "010 1234 5678",
                    leadingIcon = { WalletIconPhone(size = 16.dp, color = WalletTheme.colors.muted) },
                )
                Spacer(Modifier.height(14.dp))
                AuthField(
                    label = s.password,
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = s.passwordPlaceholder,
                    leadingIcon = { WalletIconLock(color = WalletTheme.colors.muted) },
                    trailingIcon = { WalletIconEye(color = WalletTheme.colors.muted) },
                    isPassword = true,
                )
                Spacer(Modifier.height(6.dp))
                PasswordStrengthBars(state.passwordStrength)
                Spacer(Modifier.height(6.dp))
                Text(
                    text = s.passwordStrength,
                    style = WalletTheme.typography.caption,
                    color = WalletTheme.colors.good,
                )
                Spacer(Modifier.height(14.dp))
                AuthCheckbox(
                    label = s.agreeTerms,
                    checked = state.termsAccepted,
                    onToggle = viewModel::onToggleTerms,
                )
                Spacer(Modifier.height(14.dp))
                WalletPrimaryButton(
                    text = s.createAccount,
                    onClick = onSignUp,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = s.haveAccount + " ",
                        style = WalletTheme.typography.bodyMedium,
                        color = WalletTheme.colors.muted,
                    )
                    Text(
                        text = s.signIn,
                        style = WalletTheme.typography.bodyMedium,
                        color = WalletTheme.colors.brand,
                        modifier = Modifier
                            .clip(WalletTheme.shapes.small)
                            .clickable(onClick = onGoToSignIn),
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun PasswordStrengthBars(strength: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val colors = listOf(
            Brush.horizontalGradient(listOf(Color(0xFFF26B7A), Color(0xFFF0A24A))),
            Brush.horizontalGradient(listOf(Color(0xFFF0A24A), Color(0xFF22C58B))),
            Brush.horizontalGradient(listOf(Color(0xFF22C58B), Color(0xFF3DD2C0))),
        )
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(WalletTheme.shapes.pill)
                    .then(
                        if (index < strength && index < colors.size)
                            Modifier.background(colors[index])
                        else if (index < strength)
                            Modifier.background(WalletTheme.colors.good)
                        else Modifier.background(WalletTheme.colors.line2),
                    ),
            )
        }
    }
}
