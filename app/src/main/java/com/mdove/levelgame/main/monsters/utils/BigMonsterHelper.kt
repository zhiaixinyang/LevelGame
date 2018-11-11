package com.mdove.levelgame.main.monsters.utils

import com.mdove.levelgame.greendao.entity.BigMonsters
import com.mdove.levelgame.greendao.entity.Monsters

/**
 * Created by MDove on 2018/11/11.
 */
class BigMonsterHelper {
    companion object {
        fun bigMonsterToMonster(bigMonsters: BigMonsters): Monsters {
            var monster = Monsters()
            monster.armor = bigMonsters.armor
            monster.attack = bigMonsters.attack
            monster.attackSpeed = bigMonsters.attackSpeed
            monster.money = bigMonsters.money
            monster.exp = bigMonsters.exp
            monster.consumePower = 0
            monster.dropGoodsId = bigMonsters.dropGoodsId
            monster.type = bigMonsters.type
            monster.name = bigMonsters.name

            return monster
        }
    }
}