package com.coditria.walletai.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Faux iOS status bar — clock + 3 dots. Mirrors `.status` in the prototype.
 *
 * @param onDark when true, foreground is white (used on Voice / Splash).
 */
@Composable
fun WalletPhoneStatusBar(
    modifier: Modifier = Modifier,
    onDark: Boolean = false,
    time: String = "9:41",
) {
    val fg = if (onDark) Color.White else WalletTheme.colors.ink
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(start = 24.dp, end = 24.dp, top = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

    }
}
