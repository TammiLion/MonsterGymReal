package com.tammidev.monstergym.monstergym.logic

import android.util.Log
import java.util.*

data class BattleResult(val challenger: Trainer, val waitingFor: Trainer, val won: Boolean = false)
data class RoomResult(val challenger: Trainer, val room: Room, val won: Boolean = false)
data class TrainerPair(val challenger: Trainer, val challenged: Trainer)

const val RAND_VAL = 100

object Test {
    val random = Random()
    val nextValue: Int
        get() = random.nextInt(RAND_VAL)

    fun doBattle(pair: TrainerPair, resetHealth: Boolean): BattleResult {
        val monster1 = pair.challenger.monsters.get(0)
        val monster2 = pair.challenged.monsters.get(0)

        val challengerWins = battle(monster1, monster2) == monster1
        if (resetHealth) {
            monster1.resetHealth()
            monster2.resetHealth()
        }
        return BattleResult(pair.challenger, pair.challenged, challengerWins)
    }
}

/*
    Returns the winner of the battle
 */
fun battle(monster: MonsterInstance, secondMonster: MonsterInstance): Monster {
    while (monster.hp > 0 && secondMonster.hp > 0) {
        doTurn(monster, monster.moves.get(0), secondMonster, secondMonster.moves.get(0))
    }
    return if (monster.hp > 0) monster else secondMonster
}

fun performAilment(performer: MonsterInstance, ailment: Ailment, target: MonsterInstance) {
    val succeeded = Test.random.nextInt(RAND_VAL) <= ailment.chance
    if (succeeded) target.ailments.add(ailment)
}

fun performDamage(performer: MonsterInstance, damage: Damage, target: MonsterInstance) {
    Log.d("Debug", "damage: " + performer.power * (damage.power * 0.5).toInt() / target.stamina)
    target.hp -= performer.power * (damage.power * 0.5).toInt() / target.stamina
}

fun doTurn(firstMonster: MonsterInstance, firstMonsterMove: Move, secondMonster: MonsterInstance, secondMonsterMove: Move) {
    val firstMonsterStarts = Test.random.nextBoolean()

    if (firstMonsterStarts) doTurnInOrder(firstMonster, firstMonsterMove, secondMonster, secondMonsterMove)
    else doTurnInOrder(secondMonster, secondMonsterMove, firstMonster, firstMonsterMove)
}

fun doTurnInOrder(firstMonster: MonsterInstance, firstMonsterMove: Move, secondMonster: MonsterInstance, secondMonsterMove: Move) {
    Log.d("Debug", firstMonster.name + " attacks " + secondMonster.name + " with " + firstMonsterMove.name)
    performMove(firstMonster, firstMonsterMove, secondMonster)
    Log.d("Debug", secondMonster.name + " has " + secondMonster.hp + " left")
    if (secondMonster.hp > 0) {
        Log.d("Debug", secondMonster.name + " attacks " + firstMonster.name + " with " + secondMonsterMove.name)
        performMove(secondMonster, secondMonsterMove, firstMonster)
        Log.d("Debug", firstMonster.name + " has " + firstMonster.hp + " left")
    }
}

fun performMove(performer: MonsterInstance, move: Move, target: MonsterInstance) {
    //TODO check ailments
    //TODO check disabled
    if (moveHits(move, target)) {
        if (move is Ailment) {
            performAilment(performer, move, target)
        }
        if (move is Damage) {
            performDamage(performer, move, target)
        }
    }
}

fun moveHits(move: Move, target: MonsterInstance): Boolean {
    return Test.nextValue <= move.accuracy
}