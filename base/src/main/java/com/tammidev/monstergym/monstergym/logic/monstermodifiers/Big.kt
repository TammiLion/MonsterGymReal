package com.tammidev.monstergym.monstergym.logic.monstermodifiers

import com.tammidev.monstergym.monstergym.logic.Monster

class Big(private val monster: Monster) : Monster by monster {
    override val name: String
        get() = "Big " + monster.name
    override var hp: Int
        get() = monster.hp + 55
        set(value) {
            monster.hp = value
        }
}
