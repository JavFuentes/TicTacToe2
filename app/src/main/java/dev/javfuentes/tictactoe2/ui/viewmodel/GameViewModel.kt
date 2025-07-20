package dev.javfuentes.tictactoe2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.javfuentes.tictactoe2.domain.manager.SoundManager
import dev.javfuentes.tictactoe2.domain.model.CellState
import dev.javfuentes.tictactoe2.domain.model.GameState
import dev.javfuentes.tictactoe2.domain.model.Player
import dev.javfuentes.tictactoe2.domain.model.Position
import dev.javfuentes.tictactoe2.domain.usecase.GameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameUseCase: GameUseCase = GameUseCase(),
    private val soundManager: SoundManager? = null
) : ViewModel() {
    
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    
    init {
        initializeGame()
    }
    
    fun makeMove(position: Position) {
        viewModelScope.launch {
            val currentState = _gameState.value
            
            // Only play click sound if the move is valid (not game over and cell is playable)
            if (!currentState.isGameOver && !currentState.isDefinitiveWin) {
                val cellState = currentState.board[position.row][position.col]
                if (cellState is CellState.Empty || cellState is CellState.Weak) {
                    soundManager?.playClickSound()
                }
            }
            
            val newState = gameUseCase.makeMove(currentState, position)
            _gameState.value = newState
            
            // Play appropriate victory sound
            if (newState.winner != null && currentState.winner == null) {
                when {
                    newState.isDefinitiveWin -> soundManager?.playDefinitiveWinSound()
                    else -> soundManager?.playWinSound()
                }
            }
        }
    }
    
    fun resetGame(startingPlayer: Player = Player.X) {
        viewModelScope.launch {
            val newState = gameUseCase.resetGame(startingPlayer, _gameState.value)
            _gameState.value = newState
        }
    }
    
    fun newGame() {
        viewModelScope.launch {
            val newState = gameUseCase.newGame()
            _gameState.value = newState
        }
    }
    
    private fun initializeGame() {
        viewModelScope.launch {
            // Initialize with turn start logic
            val initialState = gameUseCase.resetGame()
            _gameState.value = initialState
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        soundManager?.release()
    }
}