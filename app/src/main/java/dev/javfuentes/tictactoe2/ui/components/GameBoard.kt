package dev.javfuentes.tictactoe2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.javfuentes.tictactoe2.domain.model.GameState
import dev.javfuentes.tictactoe2.domain.model.Position

@Composable
fun GameBoard(
    gameState: GameState,
    onCellClick: (Position) -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                Color.Black,
                RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        for (i in 0..2) {
            Row {
                for (j in 0..2) {
                    val position = Position(i, j)
                    GameCell(
                        cellState = gameState.board[i][j],
                        position = position,
                        onClick = onCellClick,
                        isGameOver = gameState.isGameOver,
                        winner = gameState.winner,
                        isWinningPosition = gameState.winningPositions.contains(position),
                        isDefinitiveWin = gameState.isDefinitiveWin,
                        definitiveWinner = gameState.definitiveWinner
                    )
                }
            }
        }
    }
}