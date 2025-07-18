package dev.javfuentes.tictactoe2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import dev.javfuentes.tictactoe2.R
import dev.javfuentes.tictactoe2.domain.model.CellState
import dev.javfuentes.tictactoe2.domain.model.Player
import dev.javfuentes.tictactoe2.domain.model.Position

@Composable
fun GameCell(
    cellState: CellState,
    position: Position,
    onClick: (Position) -> Unit,
    isGameOver: Boolean,
    winner: Player?,
    isWinningPosition: Boolean
) {
    val canClick = !isGameOver && (cellState is CellState.Empty || cellState is CellState.Weak)
    
    val borderColor = when {
        isWinningPosition && winner == Player.X -> Color(0xFFf23c19)
        isWinningPosition && winner == Player.O -> Color(0xFF34f6ec)
        canClick -> Color.White.copy(alpha = 0.3f)
        else -> Color.Gray.copy(alpha = 0.5f)
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(2.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                Color.Black,
                RoundedCornerShape(8.dp)
            )
            .border(
                2.dp,
                borderColor,
                RoundedCornerShape(8.dp)
            )
            .clickable(enabled = canClick) { onClick(position) },
        contentAlignment = Alignment.Center
    ) {
        when (cellState) {
            is CellState.Strong -> {
                val resourceId = when (cellState.player) {
                    Player.X -> R.drawable.x
                    Player.O -> R.drawable.o
                }
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = cellState.player.name,
                    modifier = Modifier.size(60.dp)
                )
            }
            is CellState.Weak -> {
                val resourceId = when (cellState.player) {
                    Player.X -> R.drawable.x_weak
                    Player.O -> R.drawable.o_weak
                }
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = stringResource(R.string.cd_player_weak, cellState.player.name),
                    modifier = Modifier.size(60.dp)
                )
            }
            is CellState.Empty -> {
                // Empty cell - no image
            }
        }
    }
}