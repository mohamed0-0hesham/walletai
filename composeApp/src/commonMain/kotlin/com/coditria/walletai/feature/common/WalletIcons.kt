package com.coditria.walletai.feature.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size as GSize
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Tiny stroke-icon library — keeps the UI faithful to the prototype's lucide-style
 * icons without pulling in material-icons. Each shape lives on a 24x24 viewport
 * then scales to [size].
 */
private fun DrawScope.strokePath(path: Path, color: Color, strokeWidth: Float) {
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
        ),
    )
}

private fun roundedRectPath(x: Float, y: Float, w: Float, h: Float, r: Float): Path =
    Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(Offset(x, y), GSize(w, h)),
                cornerRadius = CornerRadius(r, r),
            ),
        )
    }

@Composable
fun WalletIconArrowBack(modifier: Modifier = Modifier, size: Dp = 16.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        val p = Path().apply {
            moveTo(15f * s, 18f * s); lineTo(9f * s, 12f * s); lineTo(15f * s, 6f * s)
        }
        strokePath(p, color, sw)
    }
}

@Composable
fun WalletIconClose(modifier: Modifier = Modifier, size: Dp = 16.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(Path().apply {
            moveTo(18f * s, 6f * s); lineTo(6f * s, 18f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(6f * s, 6f * s); lineTo(18f * s, 18f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconPlus(modifier: Modifier = Modifier, size: Dp = 22.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2.6f * s
        strokePath(Path().apply {
            moveTo(12f * s, 5f * s); lineTo(12f * s, 19f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(5f * s, 12f * s); lineTo(19f * s, 12f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconCheck(modifier: Modifier = Modifier, size: Dp = 14.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        strokePath(Path().apply {
            moveTo(20f * s, 6f * s); lineTo(9f * s, 17f * s); lineTo(4f * s, 12f * s)
        }, color, 2.5f * s)
    }
}

@Composable
fun WalletIconBell(modifier: Modifier = Modifier, size: Dp = 18.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(Path().apply {
            moveTo(6f * s, 8f * s)
            cubicTo(6f * s, 4.7f * s, 8.7f * s, 2f * s, 12f * s, 2f * s)
            cubicTo(15.3f * s, 2f * s, 18f * s, 4.7f * s, 18f * s, 8f * s)
            cubicTo(18f * s, 15f * s, 21f * s, 17f * s, 21f * s, 17f * s)
            lineTo(3f * s, 17f * s)
            cubicTo(3f * s, 17f * s, 6f * s, 15f * s, 6f * s, 8f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(10.3f * s, 21f * s)
            cubicTo(10.6f * s, 22f * s, 13.4f * s, 22f * s, 13.7f * s, 21f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconEye(modifier: Modifier = Modifier, size: Dp = 14.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(Path().apply {
            moveTo(2f * s, 12f * s)
            cubicTo(2f * s, 12f * s, 5f * s, 5f * s, 12f * s, 5f * s)
            cubicTo(19f * s, 5f * s, 22f * s, 12f * s, 22f * s, 12f * s)
            cubicTo(22f * s, 12f * s, 19f * s, 19f * s, 12f * s, 19f * s)
            cubicTo(5f * s, 19f * s, 2f * s, 12f * s, 2f * s, 12f * s)
        }, color, sw)
        drawCircle(color = color, radius = 3f * s, center = Offset(12f * s, 12f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
    }
}

@Composable
fun WalletIconClock(modifier: Modifier = Modifier, size: Dp = 14.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        drawCircle(color = color, radius = 10f * s, center = Offset(12f * s, 12f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
        strokePath(Path().apply {
            moveTo(12f * s, 6f * s); lineTo(12f * s, 12f * s); lineTo(16f * s, 14f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconHome(modifier: Modifier = Modifier, size: Dp = 22.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(Path().apply {
            moveTo(3f * s, 9f * s); lineTo(12f * s, 2f * s); lineTo(21f * s, 9f * s)
            lineTo(21f * s, 20f * s)
            cubicTo(21f * s, 21.1f * s, 20.1f * s, 22f * s, 19f * s, 22f * s)
            lineTo(5f * s, 22f * s)
            cubicTo(3.9f * s, 22f * s, 3f * s, 21.1f * s, 3f * s, 20f * s)
            close()
        }, color, sw)
    }
}

@Composable
fun WalletIconList(modifier: Modifier = Modifier, size: Dp = 22.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(roundedRectPath(3f * s, 6f * s, 18f * s, 14f * s, 3f * s), color, sw)
        strokePath(Path().apply {
            moveTo(3f * s, 10f * s); lineTo(21f * s, 10f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconChart(modifier: Modifier = Modifier, size: Dp = 22.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(Path().apply {
            moveTo(3f * s, 13f * s)
            cubicTo(3f * s, 8f * s, 7f * s, 4f * s, 12f * s, 4f * s)
            cubicTo(17f * s, 4f * s, 21f * s, 8f * s, 21f * s, 13f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconGear(modifier: Modifier = Modifier, size: Dp = 22.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        drawCircle(color = color, radius = 3f * s, center = Offset(12f * s, 12f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
        drawCircle(color = color, radius = 9f * s, center = Offset(12f * s, 12f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
    }
}

@Composable
fun WalletIconMic(modifier: Modifier = Modifier, size: Dp = 20.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(roundedRectPath(9f * s, 2f * s, 6f * s, 13f * s, 3f * s), color, sw)
        strokePath(Path().apply {
            moveTo(5f * s, 10f * s); lineTo(5f * s, 12f * s)
            cubicTo(5f * s, 16f * s, 8f * s, 19f * s, 12f * s, 19f * s)
            cubicTo(16f * s, 19f * s, 19f * s, 16f * s, 19f * s, 12f * s)
            lineTo(19f * s, 10f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(12f * s, 19f * s); lineTo(12f * s, 22f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconArrowUpRight(modifier: Modifier = Modifier, size: Dp = 14.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(Path().apply {
            moveTo(7f * s, 17f * s); lineTo(16.2f * s, 7.8f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(7f * s, 7f * s); lineTo(17f * s, 7f * s); lineTo(17f * s, 17f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconArrowDownLeft(modifier: Modifier = Modifier, size: Dp = 14.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(Path().apply {
            moveTo(17f * s, 7f * s); lineTo(7.8f * s, 16.2f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(17f * s, 17f * s); lineTo(7f * s, 17f * s); lineTo(7f * s, 7f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconChevronLeft(modifier: Modifier = Modifier, size: Dp = 16.dp, color: Color) {
    WalletIconArrowBack(modifier, size, color)
}

@Composable
fun WalletIconSearch(modifier: Modifier = Modifier, size: Dp = 16.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        drawCircle(color = color, radius = 7f * s, center = Offset(11f * s, 11f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
        strokePath(Path().apply {
            moveTo(21f * s, 21f * s); lineTo(16.65f * s, 16.65f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconCard(modifier: Modifier = Modifier, size: Dp = 22.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(roundedRectPath(2f * s, 6f * s, 20f * s, 12f * s, 2f * s), color, sw)
        strokePath(Path().apply {
            moveTo(6f * s, 12f * s); lineTo(10f * s, 12f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconPhone(modifier: Modifier = Modifier, size: Dp = 20.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(roundedRectPath(5f * s, 2f * s, 14f * s, 20f * s, 3f * s), color, sw)
    }
}

@Composable
fun WalletIconUsers(modifier: Modifier = Modifier, size: Dp = 20.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        drawCircle(color = color, radius = 4f * s, center = Offset(9f * s, 7f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
        strokePath(Path().apply {
            moveTo(3f * s, 21f * s); lineTo(3f * s, 19f * s)
            cubicTo(3f * s, 16.8f * s, 4.8f * s, 15f * s, 7f * s, 15f * s)
            lineTo(11f * s, 15f * s)
            cubicTo(13.2f * s, 15f * s, 15f * s, 16.8f * s, 15f * s, 19f * s)
            lineTo(15f * s, 21f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconCoffee(modifier: Modifier = Modifier, size: Dp = 18.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(Path().apply {
            moveTo(2f * s, 8f * s); lineTo(18f * s, 8f * s); lineTo(18f * s, 17f * s)
            cubicTo(18f * s, 19.2f * s, 16.2f * s, 21f * s, 14f * s, 21f * s)
            lineTo(6f * s, 21f * s)
            cubicTo(3.8f * s, 21f * s, 2f * s, 19.2f * s, 2f * s, 17f * s)
            close()
        }, color, sw)
        strokePath(Path().apply {
            moveTo(18f * s, 8f * s); lineTo(19f * s, 8f * s)
            cubicTo(21.2f * s, 8f * s, 23f * s, 9.8f * s, 23f * s, 12f * s)
            cubicTo(23f * s, 14.2f * s, 21.2f * s, 16f * s, 19f * s, 16f * s)
            lineTo(18f * s, 16f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconCar(modifier: Modifier = Modifier, size: Dp = 18.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(roundedRectPath(1f * s, 8f * s, 15f * s, 8f * s, 2f * s), color, sw)
        strokePath(Path().apply {
            moveTo(16f * s, 8f * s); lineTo(20f * s, 8f * s); lineTo(23f * s, 11f * s)
            lineTo(23f * s, 16f * s); lineTo(16f * s, 16f * s); close()
        }, color, sw)
        drawCircle(color = color, radius = 2f * s, center = Offset(5.5f * s, 18.5f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
        drawCircle(color = color, radius = 2f * s, center = Offset(18.5f * s, 18.5f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
    }
}

@Composable
fun WalletIconWallet(modifier: Modifier = Modifier, size: Dp = 22.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(Path().apply {
            moveTo(21f * s, 12f * s); lineTo(21f * s, 7f * s); lineTo(5f * s, 7f * s)
            cubicTo(3.9f * s, 7f * s, 3f * s, 6.1f * s, 3f * s, 5f * s)
            cubicTo(3f * s, 3.9f * s, 3.9f * s, 3f * s, 5f * s, 3f * s)
            lineTo(19f * s, 3f * s); lineTo(19f * s, 7f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(3f * s, 5f * s); lineTo(3f * s, 19f * s)
            cubicTo(3f * s, 20.1f * s, 3.9f * s, 21f * s, 5f * s, 21f * s)
            lineTo(21f * s, 21f * s); lineTo(21f * s, 16f * s)
        }, color, sw)
        strokePath(Path().apply {
            moveTo(18f * s, 12f * s)
            cubicTo(16.9f * s, 12f * s, 16f * s, 12.9f * s, 16f * s, 14f * s)
            cubicTo(16f * s, 15.1f * s, 16.9f * s, 16f * s, 18f * s, 16f * s)
            lineTo(22f * s, 16f * s); lineTo(22f * s, 12f * s); close()
        }, color, sw)
    }
}

@Composable
fun WalletIconMail(modifier: Modifier = Modifier, size: Dp = 16.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(roundedRectPath(2f * s, 4f * s, 20f * s, 16f * s, 2f * s), color, sw)
        strokePath(Path().apply {
            moveTo(2f * s, 7f * s); lineTo(12f * s, 13f * s); lineTo(22f * s, 7f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconLock(modifier: Modifier = Modifier, size: Dp = 16.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.8f * s
        strokePath(roundedRectPath(3f * s, 11f * s, 18f * s, 11f * s, 2f * s), color, sw)
        strokePath(Path().apply {
            moveTo(7f * s, 11f * s); lineTo(7f * s, 7f * s)
            cubicTo(7f * s, 4.2f * s, 9.2f * s, 2f * s, 12f * s, 2f * s)
            cubicTo(14.8f * s, 2f * s, 17f * s, 4.2f * s, 17f * s, 7f * s)
            lineTo(17f * s, 11f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconUser(modifier: Modifier = Modifier, size: Dp = 18.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 1.9f * s
        drawCircle(color = color, radius = 4f * s, center = Offset(12f * s, 8f * s),
            style = Stroke(width = sw, cap = StrokeCap.Round))
        strokePath(Path().apply {
            moveTo(4f * s, 21f * s)
            cubicTo(4f * s, 16.6f * s, 7.6f * s, 13f * s, 12f * s, 13f * s)
            cubicTo(16.4f * s, 13f * s, 20f * s, 16.6f * s, 20f * s, 21f * s)
        }, color, sw)
    }
}

@Composable
fun WalletIconSparkles(modifier: Modifier = Modifier, size: Dp = 16.dp, color: Color) {
    Canvas(modifier.size(size)) {
        val s = this.size.minDimension / 24f
        val sw = 2f * s
        strokePath(Path().apply {
            moveTo(12f * s, 2f * s); lineTo(12f * s, 7f * s)
            moveTo(12f * s, 17f * s); lineTo(12f * s, 22f * s)
            moveTo(2f * s, 12f * s); lineTo(7f * s, 12f * s)
            moveTo(17f * s, 12f * s); lineTo(22f * s, 12f * s)
        }, color, sw)
    }
}
