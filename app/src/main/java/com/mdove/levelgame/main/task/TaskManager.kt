package com.mdove.levelgame.main.task

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.mdove.levelgame.App
import com.mdove.levelgame.R
import com.mdove.levelgame.greendao.PackagesDao
import com.mdove.levelgame.greendao.TaskDao
import com.mdove.levelgame.greendao.entity.Task
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroManager
import com.mdove.levelgame.main.shop.manager.BlacksmithManager
import com.mdove.levelgame.main.task.data.TaskContentModel
import com.mdove.levelgame.utils.JsonUtil
import io.reactivex.Observable

/**
 * Created by MDove on 2018/11/25.
 */
class TaskManager {
    private object Holder {
        val INSTANCE = TaskManager()
    }

    companion object {
        // 开始任务
        val STATUS_CODE_START_TASK = 0
        // 开始任务失败
        val STATUS_CODE_START_TASK_ERROR = 4
        // 任务已经开始
        val STATUS_CODE_HAS_START = 1
        val STATUS_CODE_SUC_TASK = 2
        val STATUS_CODE_SUC_ERROR = 3
        val instance: TaskManager by lazy {
            Holder.INSTANCE
        }
    }

    fun startTask(id: Long): Observable<TaskResp> {
        var resp = TaskResp()
        var task = DatabaseManager.getInstance().taskDao.queryBuilder()
                .where(TaskDao.Properties.Id.eq(id)).unique()
        resp.status = STATUS_CODE_HAS_START
        task?.let {
            // 没被领取过
            when {
                task.taskStatus == 0 -> {
                    var hasMaterial = hasMaterials(task)
                    if (hasPower(task.consumePower) && hasMoney(task.consumeMoney) && hasMaterial.has) {
                        resp.status = STATUS_CODE_START_TASK
                        resp.id = task.id
                        task.taskStatus = 1
                        resp.tips = task.tips
                        HeroManager.getInstance().heroAttributes.bodyPower -= task.consumePower
                        HeroManager.getInstance().heroAttributes.money -= task.consumeMoney
                        if (hasMaterial.hasMaterial != null) {
                            DatabaseManager.getInstance().packagesDao.delete(
                                    DatabaseManager.getInstance().packagesDao.queryBuilder()
                                            .where(PackagesDao.Properties.Id.eq(hasMaterial.hasMaterial.id)).unique())
                        }
                        HeroManager.getInstance().save()
                        DatabaseManager.getInstance().taskDao.update(task)
                    } else {
                        resp.status = STATUS_CODE_START_TASK_ERROR
                        resp.tips = App.getAppContext().getString(R.string.string_task_has_start_error)
                    }
                }
                task.taskStatus == 1 -> {
                    resp.status = STATUS_CODE_HAS_START
                    resp.tips = App.getAppContext().getString(R.string.string_task_has_start)
                }
                task.taskStatus == 2 -> {
                    task.taskStatus = 0
                    task.attackCount = 0
                    comptuteAward(task, resp)
                }
            }
        }
        return Observable.create {
            it.onNext(resp)
        }
    }

    fun hasPower(power: Int): Boolean {
        return HeroManager.getInstance().heroAttributes.bodyPower >= power
    }

    fun hasMoney(money: Int): Boolean {
        return HeroManager.getInstance().heroAttributes.money >= money
    }

    fun hasMaterials(task: Task): HasMaterialsResp {
        var resp = HasMaterialsResp()
        resp.has = if (!TextUtils.isEmpty(task.consumeFormula)) {
            val taskContentTypes = JsonUtil.decode<List<TaskContentModel>>(task.taskContentType, object : TypeToken<List<TaskContentModel>>() {
            }.type)
            taskContentTypes.any {
                resp.hasMaterial = BlacksmithManager.getInstance().hasMaterial(it.type)
                resp.hasMaterial.isHas
            }
        } else {
            true
        }
        return resp
    }

    inner class HasMaterialsResp {
        var has = false
        lateinit var hasMaterial: BlacksmithManager.HasMaterial
    }

    fun comptuteAward(task: Task, resp: TaskResp) {
        var heroAttr = HeroManager.getInstance().heroAttributes
        heroAttr.armor += task.awardArmor.toInt()
        heroAttr.attack += task.awardAttack.toInt()
        heroAttr.money += task.awardMoney.toInt()
        heroAttr.experience += task.awardExp.toInt()
        heroAttr.maxLife += task.awardMaxLife.toInt()
        HeroManager.getInstance().save()
        resp.status = STATUS_CODE_SUC_TASK
        resp.tips = String.format(App.getAppContext().getString(R.string.string_task_suc)
                , task.awardAttack, task.awardArmor, task.awardMoney, task.awardExp, task.awardMaxLife)

        var hasMaterial = hasMaterials(task)
        if (hasMaterial.has && hasMaterial.hasMaterial != null) {
            DatabaseManager.getInstance().packagesDao.delete(
                    DatabaseManager.getInstance().packagesDao.queryBuilder()
                            .where(PackagesDao.Properties.Id.eq(hasMaterial.hasMaterial.id)).unique())
        }

        task.taskStatus = 0
        task.attackCount = 0
        DatabaseManager.getInstance().taskDao.update(task)
    }

    fun computeTask(deadMonsterType: String) {
        val hasTasks = DatabaseManager.getInstance().taskDao.queryBuilder().where(TaskDao.Properties.TaskStatus.eq(1)).list()
        hasTasks.forEach { task ->
            val taskContentTypes = JsonUtil.decode<List<TaskContentModel>>(task.taskContentType, object : TypeToken<List<TaskContentModel>>() {
            }.type)
            var taskContentType = taskContentTypes.first { monster ->
                monster.type == deadMonsterType
            }
            if (taskContentType != null) {
                task.attackCount++
                if (task.attackCount >= taskContentType.count && hasMaterials(task).has) {
                    task.taskStatus = 2
                }
                DatabaseManager.getInstance().taskDao.update(task)
            }
        }
    }

    inner class TaskResp {
        var status: Int = -1
        var id: Long = -1
        lateinit var tips: String
        var leakCount: Int = -1
    }
}