package com.mdove.levelgame.greendao.entity;

import com.mdove.levelgame.greendao.utils.IntegerConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/20.
 */
@Entity
public class Monsters {
    @Id(autoincrement = true)
    public Long id;
    // 生命值
    public int life;
    // 怪物类型 标识什么样子的怪物。比如：普通小怪（369等），BOSS...
    public String type;
    // 攻击力
    public int attack;
    // 护甲
    public int armor;
    // 掉落金钱
    public int money;
    // 描述
    public String tips;
    // 名字
    public String name;
    // 特殊掉落的物品的id（json串可以掉落多个）
    public long dropGoodsId;
    // 对应野区的Id
    public long monsterPlaceId;
    // 经验
    public long exp;
    // 体力消耗
    public int consumePower;
    // 是否是商人(1表示普通怪物，0表示商人，2表示副本)
    public int isBusinessman;
    // 卖出商品的json
    public String sellGoodsJson;
    public int isShow;
    // 是否限制次数
    public int isLimitCount;
    // 限制几次
    public int limitCount;
    // 当前还剩了
    public int curCount;
    // 攻击速度，毫秒级（对应Rx的弹射间隔，此值越来越低）
    public long attackSpeed;
    // 经验随机，下标0，1
    @Convert(columnType = String.class, converter = IntegerConverter.class)
    public List<Integer> expLiLiang;
    @Convert(columnType = String.class, converter = IntegerConverter.class)
    public List<Integer> expMinJie;
    @Convert(columnType = String.class, converter = IntegerConverter.class)
    public List<Integer> expZhiHui;
    @Convert(columnType = String.class, converter = IntegerConverter.class)
    public List<Integer> expQiangZhuang;
    @Generated(hash = 750128625)
    public Monsters(Long id, int life, String type, int attack, int armor,
            int money, String tips, String name, long dropGoodsId,
            long monsterPlaceId, long exp, int consumePower, int isBusinessman,
            String sellGoodsJson, int isShow, int isLimitCount, int limitCount,
            int curCount, long attackSpeed, List<Integer> expLiLiang,
            List<Integer> expMinJie, List<Integer> expZhiHui,
            List<Integer> expQiangZhuang) {
        this.id = id;
        this.life = life;
        this.type = type;
        this.attack = attack;
        this.armor = armor;
        this.money = money;
        this.tips = tips;
        this.name = name;
        this.dropGoodsId = dropGoodsId;
        this.monsterPlaceId = monsterPlaceId;
        this.exp = exp;
        this.consumePower = consumePower;
        this.isBusinessman = isBusinessman;
        this.sellGoodsJson = sellGoodsJson;
        this.isShow = isShow;
        this.isLimitCount = isLimitCount;
        this.limitCount = limitCount;
        this.curCount = curCount;
        this.attackSpeed = attackSpeed;
        this.expLiLiang = expLiLiang;
        this.expMinJie = expMinJie;
        this.expZhiHui = expZhiHui;
        this.expQiangZhuang = expQiangZhuang;
    }
    @Generated(hash = 1349461748)
    public Monsters() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLife() {
        return this.life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getAttack() {
        return this.attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getArmor() {
        return this.armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public String getTips() {
        return this.tips;
    }
    public void setTips(String tips) {
        this.tips = tips;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getDropGoodsId() {
        return this.dropGoodsId;
    }
    public void setDropGoodsId(long dropGoodsId) {
        this.dropGoodsId = dropGoodsId;
    }
    public long getMonsterPlaceId() {
        return this.monsterPlaceId;
    }
    public void setMonsterPlaceId(long monsterPlaceId) {
        this.monsterPlaceId = monsterPlaceId;
    }
    public long getExp() {
        return this.exp;
    }
    public void setExp(long exp) {
        this.exp = exp;
    }
    public int getConsumePower() {
        return this.consumePower;
    }
    public void setConsumePower(int consumePower) {
        this.consumePower = consumePower;
    }
    public int getIsBusinessman() {
        return this.isBusinessman;
    }
    public void setIsBusinessman(int isBusinessman) {
        this.isBusinessman = isBusinessman;
    }
    public String getSellGoodsJson() {
        return this.sellGoodsJson;
    }
    public void setSellGoodsJson(String sellGoodsJson) {
        this.sellGoodsJson = sellGoodsJson;
    }
    public int getIsShow() {
        return this.isShow;
    }
    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
    public int getIsLimitCount() {
        return this.isLimitCount;
    }
    public void setIsLimitCount(int isLimitCount) {
        this.isLimitCount = isLimitCount;
    }
    public int getLimitCount() {
        return this.limitCount;
    }
    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }
    public int getCurCount() {
        return this.curCount;
    }
    public void setCurCount(int curCount) {
        this.curCount = curCount;
    }
    public long getAttackSpeed() {
        return this.attackSpeed;
    }
    public void setAttackSpeed(long attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
    public List<Integer> getExpLiLiang() {
        return this.expLiLiang;
    }
    public void setExpLiLiang(List<Integer> expLiLiang) {
        this.expLiLiang = expLiLiang;
    }
    public List<Integer> getExpMinJie() {
        return this.expMinJie;
    }
    public void setExpMinJie(List<Integer> expMinJie) {
        this.expMinJie = expMinJie;
    }
    public List<Integer> getExpZhiHui() {
        return this.expZhiHui;
    }
    public void setExpZhiHui(List<Integer> expZhiHui) {
        this.expZhiHui = expZhiHui;
    }
    public List<Integer> getExpQiangZhuang() {
        return this.expQiangZhuang;
    }
    public void setExpQiangZhuang(List<Integer> expQiangZhuang) {
        this.expQiangZhuang = expQiangZhuang;
    }

}
