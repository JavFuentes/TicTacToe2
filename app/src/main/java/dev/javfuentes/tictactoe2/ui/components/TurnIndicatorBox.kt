package dev.javfuentes.tictactoe2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import dev.javfuentes.tictactoe2.R
import dev.javfuentes.tictactoe2.domain.model.Player

@Composable
fun TurnIndicatorBox(
    player: Player,
    isActive: Boolean,
    showWeak: Boolean,
    isWinner: Boolean,
    onRestart: () -> Unit
) {
    val borderColor = when {
        isWinner && player == Player.X -> Color(0xFFf23c19)
        isWinner && player == Player.O -> Color(0xFF34f6ec)
        isActive -> Color.White
        else -> Color.Gray.copy(alpha = 0.3f)
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(
                Color.Black,
                RoundedCornerShape(12.dp)
            )
            .border(
                2.dp,
                borderColor,
                RoundedCornerShape(12.dp)
            )
            .clickable(enabled = showWeak) { onRestart() },
        contentAlignment = Alignment.Center
    ) {
        if (isActive || showWeak || isWinner) {
            val resourceId = when {
                showWeak && player == Player.X -> R.drawable.x_weak
                showWeak && player == Player.O -> R.drawable.o_weak
                player == Player.X -> R.drawable.x
                else -> R.drawable.o
            }

            Image(
                painter = painterResource(id = resourceId),
                contentDescription = when {
                    showWeak -> stringResource(R.string.cd_player_weak_restart, player.name)
                    isWinner -> stringResource(R.string.cd_player_winner, player.name)
                    else -> player.name
                },
                modifier = Modifier.size(60.dp)
            )
        }
    }
}