package com.mdove.levelgame.main.monsters.manager.exception;

/**
 * @author MDove on 2018/11/5
 */
public class AttackMonsterException extends RuntimeException {
    public static final int ERROR_CODE_MONSTERS_IS_DEAD = 1;
    public static final String ERROR_MSG_MONSTERS_IS_DEAD = "战斗胜利，怪物死亡。";
    public static final String ERROR_TITLE_MONSTERS_IS_DEAD = "战斗胜利";
    public int errorCode;
    public String errorMsg;
    public String errorTitle;

    public AttackMonsterException(int errorCode, String errMsg, String errorTitle) {
        this.errorCode = errorCode;
        errorMsg = errMsg;
        this.errorTitle = errorTitle;
    }
}
