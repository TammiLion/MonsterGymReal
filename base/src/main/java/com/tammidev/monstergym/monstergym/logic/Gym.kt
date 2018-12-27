package com.tammidev.monstergym.monstergym.logic

import android.util.Log
import com.tammidev.monstergym.monstergym.logic.monstermodifiers.Big
import com.tammidev.monstergym.monstergym.logic.monsters.Panthares
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class Gym {

    private var rooms: MutableList<Room> = ArrayList()
    private val monsterList: MutableList<MonsterInstance> = mutableListOf(MonsterInstance(Panthares))

    fun test(): Room {
        rooms.add(Room(mutableListOf(Trainer(monsterList), Trainer(monsterList), Trainer(monsterList), Trainer(monsterList))))
        rooms[0].trainerWon()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.won) Log.d("Debug", "Whoohoo! " + it.challenger.name + " won!") else
                        Log.d("Debug", it.challenger.name + " lost :(")
                }
        for (i in 1..30) {
            val challenger = Trainer(listOf(MonsterInstance(Big(Panthares))))
            challenger.name = "name" + i
            rooms[0].enter(challenger)
        }

        return rooms[0]
    }
}
