package com.tammidev.monstergym.monstergym.logic

interface Monster {

    val name: String
    val sprite: Int
    val levelUpDifficulty: LevelUpDifficulty

    var hp: Int
    var power: Int
    var stamina: Int

    val ailments: MutableList<Ailment>
    val moves: MutableList<Move>
}

sealed class LevelUpDifficulty(val modifier: Double) {
    object Easy : LevelUpDifficulty(1.2)
    object Average : LevelUpDifficulty(1.0)
    object Hard : LevelUpDifficulty(0.8)
    object Extreme : LevelUpDifficulty(0.5)
}