package dev.javfuentes.tictactoe2.domain.model

data class Position(val row: Int, val col: Int) {
    init {
        require(row in 0..2) { "Row must be between 0 and 2" }
        require(col in 0..2) { "Column must be between 0 and 2" }
    }
}