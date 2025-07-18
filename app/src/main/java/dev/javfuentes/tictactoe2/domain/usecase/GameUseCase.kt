package dev.javfuentes.tictactoe2.domain.usecase

import dev.javfuentes.tictactoe2.domain.model.CellState
import dev.javfuentes.tictactoe2.domain.model.GameState
import dev.javfuentes.tictactoe2.domain.model.Player
import dev.javfuentes.tictactoe2.domain.model.Position

class GameUseCase {
    
    fun makeMove(gameState: GameState, position: Position): GameState {
        if (gameState.isGameOver) return gameState
        
        val cellState = gameState.board[position.row][position.col]
        if (!isCellPlayable(cellState)) return gameState
        
        // Create new board with the move
        val newBoard = gameState.board.map { row -> row.clone() }.toTypedArray()
        
        // Clear any weak symbols of the current player
        clearWeakSymbols(newBoard, gameState.currentPlayer)
        
        // Place the new symbol
        newBoard[position.row][position.col] = CellState.Strong(gameState.currentPlayer)
        
        // Update player moves
        val currentPlayerMoves = getCurrentPlayerMoves(gameState)
        val newPlayerMoves = currentPlayerMoves + position
        
        val updatedGameState = updatePlayerMoves(gameState, newPlayerMoves, newBoard)
        
        // Check for winner
        val winner = checkWinner(newBoard)
        val winningPositions = if (winner != null) getWinningPositions(newBoard, winner) else emptyList()
        
        return if (winner != null) {
            val finalBoard = markLoserSymbolsAsWeak(newBoard, winner)
            val newPlayerXWins = if (winner == Player.X) updatedGameState.playerXWins + 1 else updatedGameState.playerXWins
            val newPlayerOWins = if (winner == Player.O) updatedGameState.playerOWins + 1 else updatedGameState.playerOWins
            
            // Verificar si hay victoria definitiva (3 victorias)
            val isDefinitiveWin = newPlayerXWins >= 3 || newPlayerOWins >= 3
            val definitiveWinner = if (newPlayerXWins >= 3) Player.X else if (newPlayerOWins >= 3) Player.O else null
            
            updatedGameState.copy(
                board = finalBoard,
                isGameOver = true,
                winner = winner,
                winningPositions = winningPositions,
                playerXWins = newPlayerXWins,
                playerOWins = newPlayerOWins,
                isDefinitiveWin = isDefinitiveWin,
                definitiveWinner = definitiveWinner
            )
        } else {
            val nextPlayer = gameState.currentPlayer.opposite()
            val stateAfterTurnStart = handleTurnStart(
                updatedGameState.copy(
                    currentPlayer = nextPlayer,
                    winningPositions = winningPositions
                )
            )
            stateAfterTurnStart
        }
    }
    
    fun resetGame(startingPlayer: Player = Player.X, currentState: GameState? = null): GameState {
        return GameState(
            currentPlayer = startingPlayer,
            playerXWins = currentState?.playerXWins ?: 0,
            playerOWins = currentState?.playerOWins ?: 0,
            isDefinitiveWin = currentState?.isDefinitiveWin ?: false,
            definitiveWinner = currentState?.definitiveWinner
        )
    }
    
    fun newGame(): GameState {
        return GameState()
    }
    
    private fun isCellPlayable(cellState: CellState): Boolean {
        return cellState is CellState.Empty || cellState is CellState.Weak
    }
    
    private fun clearWeakSymbols(board: Array<Array<CellState>>, player: Player) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] is CellState.Weak && (board[i][j] as CellState.Weak).player == player) {
                    board[i][j] = CellState.Empty
                }
            }
        }
    }
    
    private fun getCurrentPlayerMoves(gameState: GameState): List<Position> {
        return when (gameState.currentPlayer) {
            Player.X -> gameState.playerXMoves
            Player.O -> gameState.playerOMoves
        }
    }
    
    private fun updatePlayerMoves(
        gameState: GameState, 
        newMoves: List<Position>, 
        board: Array<Array<CellState>>
    ): GameState {
        return when (gameState.currentPlayer) {
            Player.X -> gameState.copy(board = board, playerXMoves = newMoves)
            Player.O -> gameState.copy(board = board, playerOMoves = newMoves)
        }
    }
    
    private fun handleTurnStart(gameState: GameState): GameState {
        val currentMoves = getCurrentPlayerMoves(gameState)
        
        if (currentMoves.size >= 3) {
            val oldestMove = currentMoves.first()
            val newBoard = gameState.board.map { row -> row.clone() }.toTypedArray()
            newBoard[oldestMove.row][oldestMove.col] = CellState.Weak(gameState.currentPlayer)
            
            val newMoves = currentMoves.drop(1)
            return updatePlayerMoves(gameState.copy(board = newBoard), newMoves, newBoard)
        }
        
        return gameState
    }
    
    private fun checkWinner(board: Array<Array<CellState>>): Player? {
        // Check rows
        for (i in 0..2) {
            val firstCell = board[i][0]
            if (firstCell is CellState.Strong && 
                board[i][1] is CellState.Strong && 
                board[i][2] is CellState.Strong &&
                (board[i][1] as CellState.Strong).player == firstCell.player &&
                (board[i][2] as CellState.Strong).player == firstCell.player) {
                return firstCell.player
            }
        }
        
        // Check columns
        for (j in 0..2) {
            val firstCell = board[0][j]
            if (firstCell is CellState.Strong && 
                board[1][j] is CellState.Strong && 
                board[2][j] is CellState.Strong &&
                (board[1][j] as CellState.Strong).player == firstCell.player &&
                (board[2][j] as CellState.Strong).player == firstCell.player) {
                return firstCell.player
            }
        }
        
        // Check diagonals
        val center = board[1][1]
        if (center is CellState.Strong) {
            // Main diagonal
            if (board[0][0] is CellState.Strong && board[2][2] is CellState.Strong &&
                (board[0][0] as CellState.Strong).player == center.player &&
                (board[2][2] as CellState.Strong).player == center.player) {
                return center.player
            }
            
            // Anti-diagonal
            if (board[0][2] is CellState.Strong && board[2][0] is CellState.Strong &&
                (board[0][2] as CellState.Strong).player == center.player &&
                (board[2][0] as CellState.Strong).player == center.player) {
                return center.player
            }
        }
        
        return null
    }
    
    private fun getWinningPositions(board: Array<Array<CellState>>, winner: Player): List<Position> {
        // Check rows
        for (i in 0..2) {
            val firstCell = board[i][0]
            if (firstCell is CellState.Strong && firstCell.player == winner &&
                board[i][1] is CellState.Strong && 
                board[i][2] is CellState.Strong &&
                (board[i][1] as CellState.Strong).player == winner &&
                (board[i][2] as CellState.Strong).player == winner) {
                return listOf(Position(i, 0), Position(i, 1), Position(i, 2))
            }
        }
        
        // Check columns
        for (j in 0..2) {
            val firstCell = board[0][j]
            if (firstCell is CellState.Strong && firstCell.player == winner &&
                board[1][j] is CellState.Strong && 
                board[2][j] is CellState.Strong &&
                (board[1][j] as CellState.Strong).player == winner &&
                (board[2][j] as CellState.Strong).player == winner) {
                return listOf(Position(0, j), Position(1, j), Position(2, j))
            }
        }
        
        // Check diagonals
        val center = board[1][1]
        if (center is CellState.Strong && center.player == winner) {
            // Main diagonal
            if (board[0][0] is CellState.Strong && board[2][2] is CellState.Strong &&
                (board[0][0] as CellState.Strong).player == winner &&
                (board[2][2] as CellState.Strong).player == winner) {
                return listOf(Position(0, 0), Position(1, 1), Position(2, 2))
            }
            
            // Anti-diagonal
            if (board[0][2] is CellState.Strong && board[2][0] is CellState.Strong &&
                (board[0][2] as CellState.Strong).player == winner &&
                (board[2][0] as CellState.Strong).player == winner) {
                return listOf(Position(0, 2), Position(1, 1), Position(2, 0))
            }
        }
        
        return emptyList()
    }
    
    private fun markLoserSymbolsAsWeak(board: Array<Array<CellState>>, winner: Player): Array<Array<CellState>> {
        val loser = winner.opposite()
        val newBoard = board.map { row -> row.clone() }.toTypedArray()
        
        for (i in 0..2) {
            for (j in 0..2) {
                if (newBoard[i][j] is CellState.Strong && (newBoard[i][j] as CellState.Strong).player == loser) {
                    newBoard[i][j] = CellState.Weak(loser)
                }
            }
        }
        
        return newBoard
    }
}