package com.tammidev.monstergym.monstergym.logic.moves

import com.tammidev.monstergym.monstergym.logic.Ailment
import com.tammidev.monstergym.monstergym.logic.Damage
import com.tammidev.monstergym.monstergym.logic.Monster
import com.tammidev.monstergym.monstergym.logic.Move

object Headbutt : Move, Ailment by Stun, Damage by HeadbuttDamage {
    override val name: String = "Headbutt"
    override val sprite: Int = 0
    override val accuracy: Int = 80
}

object Stun : Ailment {
    override val chance: Int = 15
    override fun handleAilment(monster: Monster): Boolean {
        //todo monster disabled
        return true
    }
}

object HeadbuttDamage : Damage {
    override val power: Int = 75
}



