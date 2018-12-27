package com.tammidev.monstergym.monstergym.logic.monsters

import com.tammidev.monstergym.monstergym.logic.Ailment
import com.tammidev.monstergym.monstergym.logic.LevelUpDifficulty
import com.tammidev.monstergym.monstergym.logic.Monster
import com.tammidev.monstergym.monstergym.logic.Move
import com.tammidev.monstergym.monstergym.logic.moves.Headbutt

object Crocoseidon : Monster {
    override val name = "Crocoseidon"
    override val sprite = 0
    override val levelUpDifficulty = LevelUpDifficulty.Average
    override var hp = 200
    override var power = 20
    override var stamina = 10
    override val ailments: MutableList<Ailment>
        get() = mutableListOf()
    override val moves: MutableList<Move>
        get() = mutableListOf(Headbutt)
}