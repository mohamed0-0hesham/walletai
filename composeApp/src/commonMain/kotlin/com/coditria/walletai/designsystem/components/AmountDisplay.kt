package com.walletai.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.walletai.core.designsystem.theme.WalletTheme

enum class AmountDisplaySize {
    /** Hero — 48sp, used on Add Transaction screen. */
    Hero,
    /** Large — 42sp, used on Balance card. */
    Large,
    /** Medium — 28sp, used on Installments summary. */
    Medium,
}

enum class AmountSign {
    /** No sign — pure value. */
    None,
    /** Positive (+) — green. */
    Positive,
    /** Negative (−) — neutral or red. */
    Negative,
}

/**
 * Amount with currency label — display typography with tabular figures.
 *
 * Usage:
 * ```
 * WalletAmountDisplay(
 *     amount = "23,450",
 *     currency = "ج.م",
 *     size = AmountDisplaySize.Large,
 * )
 * ```
 */
@Composable
fun WalletAmountDisplay(
    amount: String,
    currency: String,
    modifier: Modifier = Modifier,
    size: AmountDisplaySize = AmountDisplaySize.Large,
    sign: AmountSign = AmountSign.None,
    color: Color = WalletTheme.colors.ink,
    currencyColor: Color = WalletTheme.colors.muted,
) {
    val amountStyle: TextStyle = when (size) {
        AmountDisplaySize.Hero -> WalletTheme.typography.displayLarge
        AmountDisplaySize.Large -> WalletTheme.typography.displayHero
        AmountDisplaySize.Medium -> WalletTheme.typography.displayMedium.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-1).sp,
        )
    }

    val currencyStyle: TextStyle = when (size) {
        AmountDisplaySize.Hero -> WalletTheme.typography.headingMedium.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        )
        AmountDisplaySize.Large -> WalletTheme.typography.headingMedium.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )
        AmountDisplaySize.Medium -> WalletTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium,
        )
    }

    val signPrefix = when (sign) {
        AmountSign.Positive -> "+"
        AmountSign.Negative -> "−"
        AmountSign.None -> ""
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = "$signPrefix$amount",
            style = amountStyle,
            color = color,
        )
        Text(
            text = currency,
            style = currencyStyle,
            color = currencyColor,
            modifier = Modifier.padding(bottom = 6.dp),
        )
    }
}
