package dev.javfuentes.tictactoe2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.javfuentes.tictactoe2.R
import dev.javfuentes.tictactoe2.domain.model.Player

@Composable
fun ScoreContainer(
    player: Player,
    wins: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(3) { index ->
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (index < wins) {
                    val resourceId = when (player) {
                        Player.X -> R.drawable.x
                        Player.O -> R.drawable.o
                    }
                    
                    Image(
                        painter = painterResource(id = resourceId),
                        contentDescription = when (player) {
                            Player.X -> stringResource(R.string.cd_player_x)
                            Player.O -> stringResource(R.string.cd_player_o)
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}