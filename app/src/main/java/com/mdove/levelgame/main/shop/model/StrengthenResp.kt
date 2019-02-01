package com.mdove.levelgame.main.shop.model

/**
 * @author MDove on 2018/10/29
 */
class StrengthenResp {
    var status: Int = -1
    // 强化石Id，用于外部更新UI
    var strengthId: Long = -1
    // 是删除强化石，还是payload
    var isDelete: Boolean = false
    var level: Long = 0
}
