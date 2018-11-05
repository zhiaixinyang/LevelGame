package com.mdove.levelgame.main.task.data

import android.database.Observable
import android.databinding.ObservableField
import com.mdove.levelgame.greendao.entity.Task

/**
 * @author MDove on 2018/11/5
 *
 */
class TaskModelVM(task: Task) {
    var name: ObservableField<String> = ObservableField()
    var tips: ObservableField<String> = ObservableField()
    var awardArmor: ObservableField<Long> = ObservableField()
    var awardAttack: ObservableField<Long> = ObservableField()
    var awardMoney: ObservableField<Long> = ObservableField()
    var awardExp: ObservableField<Long> = ObservableField()
    var awardMaxLife: ObservableField<Long> = ObservableField()

    init {
        name.set(task.name)
        tips.set(task.tips)
        awardArmor.set(task.awardArmor)
        awardAttack.set(task.awardAttack)
        awardExp.set(task.awardExp)
        awardMaxLife.set(task.awardMaxLife)
        awardMoney.set(task.awardMoney)
    }

}