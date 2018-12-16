package com.mdove.levelgame.main.task.data

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.greendao.utils.DatabaseManager

/**
 * Created by MDove on 2018/12/16.
 */

class TaskViewModel :ViewModel(){
    val taskData:MutableLiveData<MutableList<TaskModelVM>> by lazy {
        MutableLiveData<MutableList<TaskModelVM>>()
    }

    fun initData(){
        taskData.value= mutableListOf()
        var data = DatabaseManager.getInstance().taskDao.queryBuilder().list()
        for (it in data) {
            taskData.value?.add(TaskModelVM(it))
        }
    }
}
