package com.coditria.walletai.feature.voice

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coditria.walletai.app.LocalWalletStrings
import com.coditria.walletai.feature.common.WalletIconCheck
import com.coditria.walletai.feature.common.WalletIconClose
import com.coditria.walletai.feature.common.WalletIconMic
import com.coditria.walletai.feature.common.WalletPhoneStatusBar
import com.walletai.core.designsystem.theme.WalletTheme
import kotlinx.coroutines.delay

@Composable
fun VoiceScreen(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    val s = LocalWalletStrings.current
    var seconds by remember { mutableStateOf(4) }
    LaunchedEffect(Unit) {
        while (true) { delay(1000); seconds += 1 }
    }
    val timeLabel = "${(seconds / 60).pad2()}:${(seconds % 60).pad2()}"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1B2148),
                        Color(0xFF0B0E22),
                        Color(0xFF06081A),
                    ),
                ),
            ),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            WalletPhoneStatusBar(onDark = true)
            VoiceTopBar(onClose = onCancel)
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ListeningBadge(text = s.listening)
                Spacer(Modifier.height(18.dp))
                Text(
                    text = timeLabel,
                    fontSize = 54.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
                Text(
                    s.sayInArabic,
                    style = WalletTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.55f),
                    modifier = Modifier.padding(top = 6.dp, bottom = 26.dp),
                )
                MicCore()
                Spacer(Modifier.height(18.dp))
                Waveform()
                Spacer(Modifier.height(14.dp))
                LiveTranscriptCard(liveLabel = s.live, transcribingLabel = s.transcribing)
                Spacer(Modifier.height(20.dp))
                ControlRow(onCancel = onCancel, onConfirm = onConfirm)
            }
        }
    }
}

@Composable
private fun VoiceTopBar(onClose: () -> Unit) {
    val s = LocalWalletStrings.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(WalletTheme.shapes.medium)
                .background(Color.White.copy(alpha = 0.08f))
                .clickable(onClick = onClose),
            contentAlignment = Alignment.Center,
        ) { WalletIconClose(color = Color.White) }
        Text(
            s.voiceAssistant,
            style = WalletTheme.typography.headingSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
        )
        Box(modifier = Modifier.size(40.dp))
    }
}

@Composable
private fun ListeningBadge(text: String) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val scale by transition.animateFloat(
        initialValue = 1f, targetValue = 0.6f,
        animationSpec = infiniteRepeatable(tween(1400), repeatMode = RepeatMode.Reverse),
        label = "p",
    )
    Row(
        modifier = Modifier
            .clip(WalletTheme.shapes.pill)
            .background(Color(0x29F26B7A))
            .border(1.dp, Color(0x47F26B7A), WalletTheme.shapes.pill)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(Color(0xFFFF5F70)),
        )
        Text(
            text,
            style = WalletTheme.typography.bodySmall,
            color = Color(0xFFFFB1B9),
        )
    }
}

@Composable
private fun MicCore() {
    val transition = rememberInfiniteTransition(label = "mic")
    val ring1 by transition.animateFloat(
        0.6f, 1.6f,
        infiniteRepeatable(tween(2400, easing = LinearEasing), repeatMode = RepeatMode.Restart),
        label = "r1",
    )
    Box(modifier = Modifier.size(180.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(ring1)
                .clip(CircleShape)
                .border(1.5.dp, Color(0x735B6CFF), CircleShape),
        )
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            Color(0x665B6CFF),
                            Color(0x338E62FF),
                            Color.Transparent,
                        ),
                    ),
                ),
        )
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(WalletTheme.colors.brandGradient),
            contentAlignment = Alignment.Center,
        ) { WalletIconMic(size = 34.dp, color = Color.White) }
    }
}

@Composable
private fun Waveform() {
    val transition = rememberInfiniteTransition(label = "wave")
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val baseHeights = listOf(20, 36, 12, 44, 28, 34, 18, 38, 14, 32, 24, 40, 16, 30)
        baseHeights.forEachIndexed { i, base ->
            val h by transition.animateFloat(
                6f, base.toFloat(),
                infiniteRepeatable(tween(1200, delayMillis = i * 50), repeatMode = RepeatMode.Reverse),
                label = "wb$i",
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 1.5.dp)
                    .width(3.dp)
                    .height(h.dp)
                    .clip(WalletTheme.shapes.pill)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF3DD2C0), Color(0xFF5B6CFF), Color(0xFF8E62FF),
                            ),
                        ),
                    ),
            )
        }
    }
}

@Composable
private fun LiveTranscriptCard(liveLabel: String, transcribingLabel: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(18.dp))
            .padding(14.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .clip(WalletTheme.shapes.small)
                    .background(Color(0x2E5B6CFF))
                    .border(1.dp, Color(0x425B6CFF), WalletTheme.shapes.small)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Box(
                    modifier = Modifier.size(5.dp).clip(CircleShape)
                        .background(Color(0xFFA5B0FF)),
                )
                Text(liveLabel, style = WalletTheme.typography.caption, color = Color(0xFFA5B0FF))
            }
            Text(
                transcribingLabel,
                style = WalletTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFFA5B0FF),
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            "صرفت 30 جنيه مواصلات الصبح ع الشغل",
            style = WalletTheme.typography.bodyMedium,
            color = Color.White,
        )
    }
}

@Composable
private fun ControlRow(onCancel: () -> Unit, onConfirm: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
    ) {
        VoiceCircleButton(onClick = onCancel) {
            WalletIconClose(size = 20.dp, color = Color(0xFFFF8A95))
        }
        VoiceCircleButton(onClick = onConfirm) {
            WalletIconCheck(size = 22.dp, color = Color(0xFF85F7E0))
        }
    }
}

@Composable
private fun VoiceCircleButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Color.White.copy(alpha = 0.12f), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { content() }
}

private fun Int.pad2(): String = if (this < 10) "0$this" else "$this"
