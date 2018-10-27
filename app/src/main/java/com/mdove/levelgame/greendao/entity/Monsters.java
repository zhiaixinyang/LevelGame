package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.mdove.levelgame.greendao.DaoSession;
import com.mdove.levelgame.greendao.DropGoodsDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.mdove.levelgame.greendao.MonstersDao;

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
    @ToOne(joinProperty = "dropGoodsId")
    public DropGoods dropGoods;
    // 对应野区的Id
    public long monsterPlaceId;
    // 经验
    public long exp;
    // 体力消耗
    public int consumePower;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 367133519)
    private transient MonstersDao myDao;
    @Generated(hash = 1476846195)
    public Monsters(Long id, int life, String type, int attack, int armor,
            int money, String tips, String name, long dropGoodsId,
            long monsterPlaceId, long exp, int consumePower) {
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
    @Generated(hash = 37712599)
    private transient Long dropGoods__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1204493026)
    public DropGoods getDropGoods() {
        long __key = this.dropGoodsId;
        if (dropGoods__resolvedKey == null
                || !dropGoods__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DropGoodsDao targetDao = daoSession.getDropGoodsDao();
            DropGoods dropGoodsNew = targetDao.load(__key);
            synchronized (this) {
                dropGoods = dropGoodsNew;
                dropGoods__resolvedKey = __key;
            }
        }
        return dropGoods;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 889873400)
    public void setDropGoods(@NotNull DropGoods dropGoods) {
        if (dropGoods == null) {
            throw new DaoException(
                    "To-one property 'dropGoodsId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.dropGoods = dropGoods;
            dropGoodsId = dropGoods.getId();
            dropGoods__resolvedKey = dropGoodsId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 108176358)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMonstersDao() : null;
    }

}
