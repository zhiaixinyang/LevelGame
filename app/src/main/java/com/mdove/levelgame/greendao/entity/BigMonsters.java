package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.mdove.levelgame.greendao.DaoSession;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;

/**
 * @author MDove on 2018/10/26
 */
@Entity
public class BigMonsters {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String tips;
    public int attack;
    public int armor;
    public int life;
    public String type;
    // 对应野区的Id
    public long monsterPlaceId;
    // 经验
    public long exp;
    public int money;
    public Long dropGoodsId;
    @ToOne(joinProperty = "dropGoodsId")
    public DropGoods dropGoods;
    public long consumePower;
    // 魔王是否消失
    public int isGone;
    // 魔王是否死亡
    public int isDead;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 996161192)
    private transient BigMonstersDao myDao;
    @Generated(hash = 1800320174)
    public BigMonsters(Long id, String name, String tips, int attack, int armor,
            int life, String type, long monsterPlaceId, long exp, int money,
            Long dropGoodsId, long consumePower, int isGone, int isDead) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.attack = attack;
        this.armor = armor;
        this.life = life;
        this.type = type;
        this.monsterPlaceId = monsterPlaceId;
        this.exp = exp;
        this.money = money;
        this.dropGoodsId = dropGoodsId;
        this.consumePower = consumePower;
        this.isGone = isGone;
        this.isDead = isDead;
    }
    @Generated(hash = 888610162)
    public BigMonsters() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTips() {
        return this.tips;
    }
    public void setTips(String tips) {
        this.tips = tips;
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
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public Long getDropGoodsId() {
        return this.dropGoodsId;
    }
    public void setDropGoodsId(Long dropGoodsId) {
        this.dropGoodsId = dropGoodsId;
    }
    public long getConsumePower() {
        return this.consumePower;
    }
    public void setConsumePower(long consumePower) {
        this.consumePower = consumePower;
    }
    public int getIsGone() {
        return this.isGone;
    }
    public void setIsGone(int isGone) {
        this.isGone = isGone;
    }
    public int getIsDead() {
        return this.isDead;
    }
    public void setIsDead(int isDead) {
        this.isDead = isDead;
    }
    @Generated(hash = 37712599)
    private transient Long dropGoods__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1064084073)
    public DropGoods getDropGoods() {
        Long __key = this.dropGoodsId;
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
    @Generated(hash = 983783664)
    public void setDropGoods(DropGoods dropGoods) {
        synchronized (this) {
            this.dropGoods = dropGoods;
            dropGoodsId = dropGoods == null ? null : dropGoods.getId();
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
    @Generated(hash = 327110467)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBigMonstersDao() : null;
    }

}
