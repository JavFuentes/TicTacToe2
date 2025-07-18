package dev.javfuentes.tictactoe2.domain.model

data class GameState(
    val board: Array<Array<CellState>> = Array(3) { Array(3) { CellState.Empty } },
    val currentPlayer: Player = Player.X,
    val isGameOver: Boolean = false,
    val winner: Player? = null,
    val playerXMoves: List<Position> = emptyList(),
    val playerOMoves: List<Position> = emptyList(),
    val winningPositions: List<Position> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (!board.contentDeepEquals(other.board)) return false
        if (currentPlayer != other.currentPlayer) return false
        if (isGameOver != other.isGameOver) return false
        if (winner != other.winner) return false
        if (playerXMoves != other.playerXMoves) return false
        if (playerOMoves != other.playerOMoves) return false
        if (winningPositions != other.winningPositions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentDeepHashCode()
        result = 31 * result + currentPlayer.hashCode()
        result = 31 * result + isGameOver.hashCode()
        result = 31 * result + (winner?.hashCode() ?: 0)
        result = 31 * result + playerXMoves.hashCode()
        result = 31 * result + playerOMoves.hashCode()
        result = 31 * result + winningPositions.hashCode()
        return result
    }
}