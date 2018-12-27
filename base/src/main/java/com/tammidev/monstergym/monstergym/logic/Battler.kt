package com.tammidev.monstergym.monstergym.logic

import io.reactivex.Observable

class Battler {
    fun fight(pair: TrainerPair, resetHealth: Boolean): Observable<BattleResult> {
        return Observable.just(Test.doBattle(pair, resetHealth))
    }
}