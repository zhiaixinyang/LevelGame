package com.mdove.levelgame.main.lilian.adapter

import com.mdove.levelgame.base.recyclerview.diff.IDataSnapshot
import com.mdove.levelgame.base.recyclerview.diff.ListDiffCallback
import com.mdove.levelgame.main.lilian.bean.PracticePlaceVM

/**
 * Created by MDove on 2019/2/2.
 */
class PracticePlaceDiffCallback : ListDiffCallback<PracticePlaceVM>() {
    override fun createSnapshot(item: PracticePlaceVM): IDataSnapshot<PracticePlaceVM> {
        return PracticePlaceSnapshot(item)
    }

    class PracticePlaceSnapshot(vm: PracticePlaceVM) : IDataSnapshot<PracticePlaceVM> {
        val curCount = vm.curCount

        override fun referenceEquals(other: IDataSnapshot<PracticePlaceVM>): Boolean {
            return other is PracticePlaceSnapshot && curCount == other.curCount
        }

        override fun snapshotEquals(other: IDataSnapshot<PracticePlaceVM>): Boolean {
            return true
        }
    }
}