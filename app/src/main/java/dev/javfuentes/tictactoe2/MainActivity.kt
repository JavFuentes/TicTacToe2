package dev.javfuentes.tictactoe2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.javfuentes.tictactoe2.ui.theme.TicTacToe2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToe2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black // Fondo negro para toda la app
                ) {
                    TicTacToeGame()
                }
            }
        }
    }
}

@Composable
fun TicTacToeGame() {
    // Estado del juego
    var board by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var gameStatus by remember { mutableStateOf("Turno del jugador: X") }
    var isGameOver by remember { mutableStateOf(false) }
    var playerXMoves by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }
    var playerOMoves by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }

    // Función para verificar si hay un ganador (solo cuenta símbolos fuertes)
    fun checkWinner(): String? {
        // Verificar filas
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] &&
                board[i][0].isNotEmpty() && !board[i][0].endsWith("_weak")) {
                return board[i][0]
            }
        }

        // Verificar columnas
        for (j in 0..2) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] &&
                board[0][j].isNotEmpty() && !board[0][j].endsWith("_weak")) {
                return board[0][j]
            }
        }

        // Verificar diagonales
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] &&
            board[0][0].isNotEmpty() && !board[0][0].endsWith("_weak")) {
            return board[0][0]
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] &&
            board[0][2].isNotEmpty() && !board[0][2].endsWith("_weak")) {
            return board[0][2]
        }

        return null
    }

    // Función para manejar el inicio de turno
    fun handleTurnStart() {
        val currentMoves = if (currentPlayer == "X") playerXMoves else playerOMoves

        // Si el jugador ya tiene 3 símbolos fuertes, convertir el más antiguo en débil
        if (currentMoves.size >= 3) {
            val oldestMove = currentMoves.first()
            board[oldestMove.first][oldestMove.second] = "${currentPlayer}_weak"

            // Actualizar la lista removiendo el primer elemento
            if (currentPlayer == "X") {
                playerXMoves = playerXMoves.drop(1)
            } else {
                playerOMoves = playerOMoves.drop(1)
            }
        }
    }

    // Función para marcar todos los símbolos del perdedor como débiles
    fun markLoserSymbolsAsWeak(winner: String) {
        val loser = if (winner == "X") "O" else "X"

        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == loser) {
                    board[i][j] = "${loser}_weak"
                }
            }
        }
    }

    // Función para hacer una jugada
    fun makeMove(row: Int, col: Int) {
        if (isGameOver) return

        // Solo permitir jugada en celdas vacías o con símbolos débiles
        if (board[row][col].isEmpty() || board[row][col].endsWith("_weak")) {
            // Borrar cualquier símbolo débil del jugador actual antes del nuevo movimiento
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == "${currentPlayer}_weak") {
                        board[i][j] = ""
                    }
                }
            }

            // Colocar el nuevo símbolo
            board[row][col] = currentPlayer

            // Actualizar la lista de movimientos del jugador actual
            if (currentPlayer == "X") {
                playerXMoves = playerXMoves + Pair(row, col)
            } else {
                playerOMoves = playerOMoves + Pair(row, col)
            }

            val winner = checkWinner()
            when {
                winner != null -> {
                    gameStatus = "¡Jugador $winner ha ganado!"
                    isGameOver = true
                    // Marcar los símbolos del perdedor como débiles
                    markLoserSymbolsAsWeak(winner)
                }
                else -> {
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    gameStatus = "Turno del jugador: $currentPlayer"
                    // Manejar el inicio del nuevo turno
                    if (!isGameOver) {
                        handleTurnStart()
                    }
                }
            }
        }
    }

    // Función para reiniciar el juego
    fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameStatus = "Turno del jugador: X"
        isGameOver = false
        playerXMoves = listOf()
        playerOMoves = listOf()
    }

    // Inicializar el primer turno
    LaunchedEffect(Unit) {
        handleTurnStart()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "TIC TAC TOE",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White, // Texto blanco sobre fondo negro
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Estado del juego
        Text(
            text = gameStatus,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White, // Texto blanco sobre fondo negro
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        // Tablero del juego
        Column(
            modifier = Modifier
                .background(
                    Color.Black, // Fondo negro para el contenedor del tablero
                    RoundedCornerShape(12.dp)
                )
                .padding(8.dp)
        ) {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        GameCell(
                            value = board[i][j],
                            onClick = { makeMove(i, j) },
                            isGameOver = isGameOver
                        )
                    }
                }
            }
        }

        // Botón para reiniciar
        Button(
            onClick = { resetGame() },
            modifier = Modifier
                .padding(top = 32.dp)
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Nuevo Juego",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GameCell(
    value: String,
    onClick: () -> Unit,
    isGameOver: Boolean
) {
    val canClick = !isGameOver && (value.isEmpty() || value.endsWith("_weak"))

    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(2.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                Color.Black, // Siempre fondo negro para todas las celdas
                RoundedCornerShape(8.dp)
            )
            .border(
                2.dp,
                if (canClick) Color.White.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.5f),
                RoundedCornerShape(8.dp)
            )
            .clickable(enabled = canClick) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when (value) {
            "X" -> Image(
                painter = painterResource(id = R.drawable.x),
                contentDescription = "X",
                modifier = Modifier.size(60.dp)
            )
            "O" -> Image(
                painter = painterResource(id = R.drawable.o),
                contentDescription = "O",
                modifier = Modifier.size(60.dp)
            )
            "X_weak" -> Image(
                painter = painterResource(id = R.drawable.x_weak),
                contentDescription = "X débil",
                modifier = Modifier.size(60.dp)
            )
            "O_weak" -> Image(
                painter = painterResource(id = R.drawable.o_weak),
                contentDescription = "O débil",
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicTacToePreview() {
    TicTacToe2Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            TicTacToeGame()
        }
    }
}