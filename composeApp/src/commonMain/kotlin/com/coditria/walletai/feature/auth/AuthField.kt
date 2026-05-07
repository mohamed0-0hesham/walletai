package com.coditria.walletai.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.coditria.walletai.feature.common.WalletIconCheck
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Form field — label + chip-bg input row with optional leading icon and trailing slot.
 * Equivalent to `.field` / `.field-w` in CSS.
 */
@Composable
fun AuthField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    isPassword: Boolean = false,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = WalletTheme.typography.label,
            color = WalletTheme.colors.muted,
        )
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(WalletTheme.spacing.inputHeight)
                .clip(WalletTheme.shapes.medium)
                .background(WalletTheme.colors.chipBg)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (leadingIcon != null) leadingIcon()
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = WalletTheme.typography.bodyLarge,
                        color = WalletTheme.colors.muted,
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    cursorBrush = SolidColor(WalletTheme.colors.brand),
                    textStyle = WalletTheme.typography.bodyLarge.copy(color = WalletTheme.colors.ink),
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                )
            }
            if (trailingIcon != null) trailingIcon()
        }
    }
}

@Composable
fun AuthCheckbox(
    label: String,
    checked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(onClick = onToggle),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(WalletTheme.shapes.small)
                .then(
                    if (checked) Modifier.background(WalletTheme.colors.brandGradient)
                    else Modifier
                        .background(WalletTheme.colors.surface)
                        .border(1.5.dp, WalletTheme.colors.line2, WalletTheme.shapes.small),
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) WalletIconCheck(size = 11.dp, color = WalletTheme.colors.onBrand)
        }
        Text(
            text = label,
            style = WalletTheme.typography.bodyMedium,
            color = WalletTheme.colors.ink2,
        )
    }
}
