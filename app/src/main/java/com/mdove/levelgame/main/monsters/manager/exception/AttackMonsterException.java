package com.mdove.levelgame.main.monsters.manager.exception;

/**
 * @author MDove on 2018/11/5
 */
public class AttackMonsterException extends RuntimeException {
    public static final int ERROR_CODE_MONSTERS_IS_DEAD = 1;
    public static final int ERROR_CODE_HERO_IS_DEAD = 2;
    public static final int ERROR_CODE_HERO_NO_POWER = 3;
    public static final int ERROR_CODE_HERO_NO_COUNT = 4;
    // 濒死，生命0
    public static final int ERROR_CODE_HERO_IS_NO_LIFE = 5;
    public static final String ERROR_MSG_MONSTERS_IS_DEAD = "战斗胜利，怪物死亡。";
    public static final String ERROR_MSG_HERO_NO_POWER = "体力不足，请尝试休息。";
    public static final String ERROR_MSG_HERO_IS_DEAD = "战斗失败，你已死亡。";
    public static final String ERROR_MSG_HERO_NO_COUNT = "此怪物可攻击次数不足，请尝试休息。";
    public static final String ERROR_MSG_HERO_IS_NO_LIFE = "你处于濒死状态，请买些药品恢复生命值。";
    public static final String ERROR_TITLE_MONSTERS_IS_DEAD = "战斗胜利";
    public static final String ERROR_TITLE_HERO_IS_DEAD = "战斗失败";
    public static final String ERROR_TITLE_HERO_NO_POWER = "无法攻击";
    public static final String ERROR_TITLE_HERO_NO_COUNT = "无法攻击";
    public static final String ERROR_TITLE_HERO_IS_NO_LIFE = "无法攻击";
    public int errorCode;
    public String errorMsg;
    public String errorTitle;

    public AttackMonsterException(int errorCode, String errorTitle, String errMsg) {
        this.errorCode = errorCode;
        errorMsg = errMsg;
        this.errorTitle = errorTitle;
    }
}
