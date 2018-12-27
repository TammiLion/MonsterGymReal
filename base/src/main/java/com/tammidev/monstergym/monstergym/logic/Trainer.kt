package com.tammidev.monstergym.monstergym.logic

class Trainer(monsters: List<MonsterInstance>) {
    var name = "name"
    val monsters: MutableList<MonsterInstance> = ArrayList()

    init {
        this.monsters.addAll(monsters)
    }
}