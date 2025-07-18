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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    val context = LocalContext.current

    // Estado del juego
    var board by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var isGameOver by remember { mutableStateOf(false) }
    var playerXMoves by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }
    var playerOMoves by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }
    var winningPositions by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }

    // Función para verificar si hay un ganador (solo cuenta símbolos fuertes)
    fun checkWinner(): String? {
        // Verificar filas
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] &&
                board[i][0].isNotEmpty() && !board[i][0].endsWith("_weak")) {
                winningPositions = listOf(Pair(i, 0), Pair(i, 1), Pair(i, 2))
                return board[i][0]
            }
        }

        // Verificar columnas
        for (j in 0..2) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] &&
                board[0][j].isNotEmpty() && !board[0][j].endsWith("_weak")) {
                winningPositions = listOf(Pair(0, j), Pair(1, j), Pair(2, j))
                return board[0][j]
            }
        }

        // Verificar diagonales
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] &&
            board[0][0].isNotEmpty() && !board[0][0].endsWith("_weak")) {
            winningPositions = listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2))
            return board[0][0]
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] &&
            board[0][2].isNotEmpty() && !board[0][2].endsWith("_weak")) {
            winningPositions = listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))
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
                    isGameOver = true
                    // Marcar los símbolos del perdedor como débiles
                    markLoserSymbolsAsWeak(winner)
                }
                else -> {
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    // Manejar el inicio del nuevo turno
                    if (!isGameOver) {
                        handleTurnStart()
                    }
                }
            }
        }
    }

    // Función para reiniciar el juego
    fun resetGame(startingPlayer: String = "X") {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = startingPlayer
        isGameOver = false
        playerXMoves = listOf()
        playerOMoves = listOf()
        winningPositions = listOf()
    }

    // Inicializar el primer turno
    LaunchedEffect(Unit) {
        handleTurnStart()
    }

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
                player = "X",
                isActive = currentPlayer == "X" && !isGameOver,
                showWeak = isGameOver && checkWinner() == "O",
                isWinner = isGameOver && checkWinner() == "X",
                onRestart = {
                    val winner = checkWinner()
                    if (winner != null) {
                        val loser = if (winner == "X") "O" else "X"
                        resetGame(loser)
                    }
                }
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
                                isGameOver = isGameOver,
                                winner = checkWinner(),
                                isWinningPosition = winningPositions.contains(Pair(i, j))
                            )
                        }
                    }
                }
            }

            // Indicador de turno para O (abajo)
            TurnIndicatorBox(
                player = "O",
                isActive = currentPlayer == "O" && !isGameOver,
                showWeak = isGameOver && checkWinner() == "X",
                isWinner = isGameOver && checkWinner() == "O",
                onRestart = {
                    val winner = checkWinner()
                    if (winner != null) {
                        val loser = if (winner == "X") "O" else "X"
                        resetGame(loser)
                    }
                }
            )
        }

        // Menú del jugador X (overlay alineado horizontalmente con su casilla)
        if (isGameOver && checkWinner() == "O") {
            LoserMenu(
                isPlayerX = true,
                onRematch = {
                    val winner = checkWinner()
                    if (winner != null) {
                        val loser = if (winner == "X") "O" else "X"
                        resetGame(loser)
                    }
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
        if (isGameOver && checkWinner() == "X") {
            LoserMenu(
                isPlayerX = false,
                onRematch = {
                    val winner = checkWinner()
                    if (winner != null) {
                        val loser = if (winner == "X") "O" else "X"
                        resetGame(loser)
                    }
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

@Composable
fun LoserMenu(
    isPlayerX: Boolean,
    onRematch: () -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(120.dp)
            .background(
                Color.Black,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isPlayerX) {
            // Para jugador X (rotado 180°), orden inverso para que se vea correcto
            // Texto Quit (se verá en el medio cuando esté rotado)
            Text(
                text = "QUIT",
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.romanus)),
                modifier = Modifier
                    .clickable { onQuit() }
                    .rotate(180f)
                    .padding(8.dp)
            )

            // Texto Rematch (se verá primero cuando esté rotado)
            Text(
                text = "REMATCH",
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.romanus)),
                modifier = Modifier
                    .clickable { onRematch() }
                    .rotate(180f)
                    .padding(8.dp)
            )
        } else {
            // Para jugador O (sin rotación), orden normal
            // Texto Rematch
            Text(
                text = "REMATCH",
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.romanus)),
                modifier = Modifier
                    .clickable { onRematch() }
                    .padding(8.dp)
            )

            // Texto Quit
            Text(
                text = "QUIT",
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.romanus)),
                modifier = Modifier
                    .clickable { onQuit() }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun TurnIndicatorBox(
    player: String,
    isActive: Boolean,
    showWeak: Boolean,
    isWinner: Boolean,
    onRestart: () -> Unit
) {
    val borderColor = when {
        isWinner && player == "X" -> Color(0xFFf23c19)
        isWinner && player == "O" -> Color(0xFF34f6ec)
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
                showWeak && player == "X" -> R.drawable.x_weak
                showWeak && player == "O" -> R.drawable.o_weak
                player == "X" -> R.drawable.x
                else -> R.drawable.o
            }

            Image(
                painter = painterResource(id = resourceId),
                contentDescription = when {
                    showWeak -> "$player débil - Presiona para reiniciar"
                    isWinner -> "$player ganador"
                    else -> player
                },
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
fun GameCell(
    value: String,
    onClick: () -> Unit,
    isGameOver: Boolean,
    winner: String?,
    isWinningPosition: Boolean
) {
    val canClick = !isGameOver && (value.isEmpty() || value.endsWith("_weak"))

    val borderColor = when {
        isWinningPosition && winner == "X" -> Color(0xFFf23c19)
        isWinningPosition && winner == "O" -> Color(0xFF34f6ec)
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
                Color.Black, // Siempre fondo negro para todas las celdas
                RoundedCornerShape(8.dp)
            )
            .border(
                2.dp,
                borderColor,
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