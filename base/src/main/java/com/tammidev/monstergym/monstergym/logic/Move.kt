package com.tammidev.monstergym.monstergym.logic

interface Move {
    val name: String
    val sprite: Int
    val accuracy: Int
}

interface Ailment {
    val chance: Int

    /**
     * In here do what the ailment is supposed to do, ie. remove HP if ailment is poison
     * return TRUE if monster recovers of ailment or FALSE if the ailment will remain for another turn
     */
    fun handleAilment(monster: Monster): Boolean

    fun execute(target: Monster) {}

}

interface Damage {
    val power: Int
}

open class SimpleDamage(override val power: Int) : Damage
