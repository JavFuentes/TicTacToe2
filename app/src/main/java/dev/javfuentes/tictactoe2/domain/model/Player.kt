package dev.javfuentes.tictactoe2.domain.model

enum class Player {
    X, O;

    fun opposite(): Player = when (this) {
        X -> O
        O -> X
    }
}