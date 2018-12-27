package com.tammidev.monstergym.monstergym.logic.monsters

import com.tammidev.monstergym.monstergym.logic.Ailment
import com.tammidev.monstergym.monstergym.logic.LevelUpDifficulty
import com.tammidev.monstergym.monstergym.logic.Monster
import com.tammidev.monstergym.monstergym.logic.Move
import com.tammidev.monstergym.monstergym.logic.moves.Headbutt

object Panthares : Monster {
    override val name = "Panthares"
    override val sprite = 0
    override val levelUpDifficulty = LevelUpDifficulty.Average
    override var hp = 80
    override var power = 30
    override var stamina = 20
    override val ailments: MutableList<Ailment>
        get() = mutableListOf()
    override val moves: MutableList<Move>
        get() = mutableListOf(Headbutt)
}