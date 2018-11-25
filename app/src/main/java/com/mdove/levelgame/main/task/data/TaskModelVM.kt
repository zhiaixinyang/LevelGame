package com.mdove.levelgame.main.task.data

import android.databinding.ObservableField
import com.google.gson.reflect.TypeToken
import com.mdove.levelgame.App
import com.mdove.levelgame.R
import com.mdove.levelgame.greendao.entity.Task
import com.mdove.levelgame.greendao.utils.SrcIconMap
import com.mdove.levelgame.utils.JsonUtil

/**
 * @author MDove on 2018/11/5
 *
 */
class TaskModelVM(task: Task) {
    var name: ObservableField<String> = ObservableField()
    var tips: ObservableField<String> = ObservableField()
    var taskNeed: ObservableField<String> = ObservableField()
    var btnName: ObservableField<String> = ObservableField()
    var awardArmor: ObservableField<Long> = ObservableField()
    var id: ObservableField<Long> = ObservableField()
    var awardAttack: ObservableField<Long> = ObservableField()
    var awardMoney: ObservableField<Long> = ObservableField()
    var awardExp: ObservableField<Long> = ObservableField()
    var awardMaxLife: ObservableField<Long> = ObservableField()
    var taskStatus: ObservableField<Int> = ObservableField()
    var src: ObservableField<Int> = ObservableField()
    var curTaskCount: ObservableField<Long> = ObservableField()

    init {
        id.set(task.id)
        name.set(task.name)
        tips.set(task.tips)
        curTaskCount.set(task.attackCount)
        awardArmor.set(task.awardArmor)
        awardAttack.set(task.awardAttack)
        awardExp.set(task.awardExp)
        awardMaxLife.set(task.awardMaxLife)
        awardMoney.set(task.awardMoney)
        taskStatus.set(task.taskStatus)
        src.set(SrcIconMap.getInstance().getSrc(task.type))
        val need = JsonUtil.decode<List<TaskContentModel>>(task.taskContentType, object : TypeToken<List<TaskContentModel>>() {
        }.type)
        taskNeed.set(String.format(App.getAppContext().getString(R.string.string_task_count)
                , curTaskCount.get(), need[0].count))
        when {
            taskStatus.get() == 0 -> btnName.set("接任务")
            taskStatus.get() == 1 -> btnName.set("任务进行中")
            taskStatus.get() == 2 -> btnName.set("完成任务")
        }
    }

    fun resetTaskStatus(taskStatus: Int) {
        when (taskStatus) {
            0 -> btnName.set("接任务")
            1 -> btnName.set("任务进行中")
            2 -> btnName.set("完成任务")
        }
        this.taskStatus.set(taskStatus)
    }
}