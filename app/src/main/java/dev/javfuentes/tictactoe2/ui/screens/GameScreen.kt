package dev.javfuentes.tictactoe2.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.javfuentes.tictactoe2.domain.model.Player
import dev.javfuentes.tictactoe2.ui.components.GameBoard
import dev.javfuentes.tictactoe2.ui.components.LoserMenu
import dev.javfuentes.tictactoe2.ui.components.TurnIndicatorBox
import dev.javfuentes.tictactoe2.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel()
) {
    val context = LocalContext.current
    val gameState by viewModel.gameState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Layout principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(72.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Indicador de turno para X (arriba)
            TurnIndicatorBox(
                player = Player.X,
                isActive = gameState.currentPlayer == Player.X && !gameState.isGameOver,
                showWeak = gameState.isGameOver && gameState.winner == Player.O,
                isWinner = gameState.isGameOver && gameState.winner == Player.X,
                onRestart = {
                    if (gameState.winner != null) {
                        val loser = gameState.winner!!.opposite()
                        viewModel.resetGame(loser)
                    }
                }
            )

            // Tablero del juego
            GameBoard(
                gameState = gameState,
                onCellClick = { position ->
                    viewModel.makeMove(position)
                }
            )

            // Indicador de turno para O (abajo)
            TurnIndicatorBox(
                player = Player.O,
                isActive = gameState.currentPlayer == Player.O && !gameState.isGameOver,
                showWeak = gameState.isGameOver && gameState.winner == Player.X,
                isWinner = gameState.isGameOver && gameState.winner == Player.O,
                onRestart = {
                    if (gameState.winner != null) {
                        val loser = gameState.winner!!.opposite()
                        viewModel.resetGame(loser)
                    }
                }
            )
        }

        // Menú del jugador X (overlay alineado horizontalmente con su casilla)
        if (gameState.isGameOver && gameState.winner == Player.O) {
            LoserMenu(
                player = Player.X,
                onRematch = {
                    val loser = gameState.winner!!.opposite()
                    viewModel.resetGame(loser)
                },
                onQuit = {
                    (context as ComponentActivity).finish()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 58.dp, end = 16.dp)
            )
        }

        // Menú del jugador O (overlay alineado horizontalmente con su casilla)
        if (gameState.isGameOver && gameState.winner == Player.X) {
            LoserMenu(
                player = Player.O,
                onRematch = {
                    val loser = gameState.winner!!.opposite()
                    viewModel.resetGame(loser)
                },
                onQuit = {
                    (context as ComponentActivity).finish()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 58.dp, end = 16.dp)
            )
        }
    }
}