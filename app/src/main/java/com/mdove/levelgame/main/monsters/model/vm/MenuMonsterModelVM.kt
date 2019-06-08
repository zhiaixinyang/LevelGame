package com.mdove.levelgame.main.monsters.model.vm

/**
 * Created by MDove on 2019/1/27.
 */

data class MenuMonsterModelVM(val type:MonsterMenuType = MonsterMenuType.SHOP) : BaseMonsterModelVM()

enum class MonsterMenuType{
    SHOP
}