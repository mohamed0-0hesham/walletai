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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.feature.common.WalletIconArrowBack
import com.coditria.walletai.feature.common.WalletIconWallet
import com.walletai.core.designsystem.theme.WalletTheme

/**
 * Brand hero band shared by SignIn / SignUp. Equivalent to `.auth-hero` in CSS.
 */
@Composable
fun AuthHero(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF5B6CFF),
                        Color(0xFF3D4BCC),
                        Color(0xFF262C56),
                    ),
                ),
            )
            .padding(horizontal = 24.dp, vertical = 18.dp),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(WalletTheme.shapes.medium)
                    .background(Color.White.copy(alpha = 0.12f))
                    .border(1.dp, Color.White.copy(alpha = 0.18f), WalletTheme.shapes.medium)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                WalletIconArrowBack(color = Color.White)
            }
            Spacer(Modifier.height(18.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(WalletTheme.shapes.medium)
                        .background(Color.White.copy(alpha = 0.14f))
                        .border(1.dp, Color.White.copy(alpha = 0.22f), WalletTheme.shapes.medium),
                    contentAlignment = Alignment.Center,
                ) { WalletIconWallet(size = 22.dp, color = Color.White) }
                Text(
                    text = LocalWalletStrings.current.appName,
                    style = WalletTheme.typography.headingMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                )
            }
            Spacer(Modifier.height(14.dp))
            Text(
                text = title,
                style = WalletTheme.typography.headingLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = subtitle,
                style = WalletTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.78f),
            )
            Spacer(Modifier.height(20.dp))
        }
    }
}
