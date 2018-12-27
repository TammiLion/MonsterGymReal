package com.tammidev.monstergym.monstergym.logic

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject

open class Room(val trainers: List<Trainer>) {

    private val observable: ReplaySubject<RoomResult> = ReplaySubject.create()
    private val queued: MutableList<TrainerPair> = ArrayList()
    private val fightingPairs: MutableList<TrainerPair> = ArrayList()

    /*
    Allows you to subscribe to an event that is emitted when a trainer defeated all trainers in a room.
     */
    fun trainerWon(): Observable<RoomResult> {
        return observable
    }

    /*
    Call this function when a (challenger) trainer enters this room.
     */
    fun enter(challenger: Trainer) {
        handleChallenger(challenger, 0)
    }

    private fun handleChallenger(challenger: Trainer, posOfChallenged: Int) {
        val challenged = trainers[posOfChallenged]
        val pair = TrainerPair(challenger, challenged)
        if (isTrainerFighting(challenged))
            queued.add(pair) else {
            fightingPairs.add(pair)
            handleBattle(pair, posOfChallenged + 1)
        }
    }

    private fun handleBattle(pair: TrainerPair, nextToChallenge: Int) {
        Battler().fight(pair, true)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    fightingPairs.remove(pair)
                    advanceQueue()
                    if (it.won) {
                        if (trainers.size == nextToChallenge) {
                            observable.onNext(RoomResult(pair.challenger, this, true))
                        } else {
                            handleChallenger(pair.challenger, nextToChallenge)
                        }
                    } else {
                        observable.onNext(RoomResult(pair.challenger, this, false))
                    }
                }
    }

    private fun advanceQueue() {
        var pair: TrainerPair? = null
        queued.forEach {
            if (!isTrainerFighting(it.challenged)) {
                pair = it
            }
        }
        pair?.apply {
            queued.remove(this)
            handleChallenger(this.challenger, trainers.indexOf(this.challenged))
        }
    }

    private fun isTrainerFighting(trainer: Trainer): Boolean {
        fightingPairs.forEach {
            if (it.challenged == trainer) {
                return true
            }
        }
        return false
    }
}