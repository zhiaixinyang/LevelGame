package com.mdove.levelgame.main.hero.util

import com.mdove.levelgame.main.hero.manager.HeroManager

/**
 * Created by MDove on 2018/12/27.
 */
data class EnableEquipResp(var respCode: Int) {
    // 0表示成功，1表示失败
}

class EquipUtils {
    companion object {
        @JvmStatic fun enableEquip(needLevel: Int, needLiLiang: Int, needMinJie: Int, needZhiHui: Int, needQIangZhuang: Int): EnableEquipResp {
            val hero = HeroManager.getInstance().heroAttributes
            var resp = EnableEquipResp(1)
            if (hero.level >= needLevel && hero.liLiang >= needLiLiang && hero.minJie >= needMinJie
                    && hero.zhiHui >= needZhiHui && hero.qiangZhuang >= needQIangZhuang) {
                resp.respCode = 0
            }
            return resp
        }
    }
}

