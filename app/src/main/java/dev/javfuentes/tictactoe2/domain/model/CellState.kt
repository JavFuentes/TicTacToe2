package dev.javfuentes.tictactoe2.domain.model

sealed class CellState {
    object Empty : CellState()
    data class Strong(val player: Player) : CellState()
    data class Weak(val player: Player) : CellState()
    
    fun toDisplayString(): String = when (this) {
        is Empty -> ""
        is Strong -> player.name
        is Weak -> "${player.name}_weak"
    }
}