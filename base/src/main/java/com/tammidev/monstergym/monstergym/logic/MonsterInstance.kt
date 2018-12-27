package com.tammidev.monstergym.monstergym.logic

class MonsterInstance(private val monster: Monster) : Monster by monster {

    private var level = 1
    private var experience = 0
    private var extraHp: Int = 0
    private var instanceHp: Int = monster.hp + extraHp

    override var hp: Int
        get() = instanceHp
        set(value) {
            instanceHp = value
        }

    fun resetHealth() {
        instanceHp = monster.hp + extraHp
    }

    fun getLevel() = level

    fun addExperience(experience: Int) {
        this.experience += experience
        checkForLevelUp()
    }

    private fun checkForLevelUp() {

    }
}