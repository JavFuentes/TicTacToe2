package dev.javfuentes.tictactoe2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import dev.javfuentes.tictactoe2.R
import dev.javfuentes.tictactoe2.domain.model.Player

@Composable
fun DefinitiveWinMenu(
    player: Player,
    onNewGame: () -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPlayerX = player == Player.X
    val romanusFont = FontFamily(Font(R.font.romanus))
    
    Column(
        modifier = modifier
            .widthIn(min = 120.dp, max = 160.dp)
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
            // Texto Quit (se verá al final cuando esté rotado)
            Text(
                text = stringResource(R.string.menu_quit),
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = romanusFont,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable { onQuit() }
                    .rotate(180f)
                    .padding(8.dp)
            )

            // Texto New Game (se verá primero cuando esté rotado)
            Text(
                text = stringResource(R.string.menu_new_game),
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = romanusFont,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable { onNewGame() }
                    .rotate(180f)
                    .padding(8.dp)
            )
        } else {
            // Para jugador O (sin rotación), orden normal
            // Texto New Game
            Text(
                text = stringResource(R.string.menu_new_game),
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = romanusFont,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable { onNewGame() }
                    .padding(8.dp)
            )

            // Texto Quit
            Text(
                text = stringResource(R.string.menu_quit),
                color = Color.Gray.copy(alpha = 0.3f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = romanusFont,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable { onQuit() }
                    .padding(8.dp)
            )
        }
    }
}