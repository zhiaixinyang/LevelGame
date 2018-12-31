package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.mdove.levelgame.greendao.DaoSession;
import com.mdove.levelgame.greendao.RandomAttrDao;
import com.mdove.levelgame.greendao.PackagesDao;

/**
 * @author MDove on 2018/10/23
 * <p>
 * 开启强化之后：强化过的装备都会对应到MyWeapons上
 */
@Entity
public class Packages {
    @Id(autoincrement = true)
    public Long id;
    // 道具Type（标识唯一物品）
    public String type;
    // 是否装备：0表示装备
    public int isEquip;
    // 装备强化等级
    public int strengthenLevel;
    // 选中，表示在升级/合成 选材料的时候已经被选中了
    public int isSelect;
    public Long randomAttrId;
    @ToOne(joinProperty = "randomAttrId")
    public RandomAttr randomAttr;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1141293423)
    private transient PackagesDao myDao;
    @Generated(hash = 1201544359)
    public Packages(Long id, String type, int isEquip, int strengthenLevel,
            int isSelect, Long randomAttrId) {
        this.id = id;
        this.type = type;
        this.isEquip = isEquip;
        this.strengthenLevel = strengthenLevel;
        this.isSelect = isSelect;
        this.randomAttrId = randomAttrId;
    }
    @Generated(hash = 688242455)
    public Packages() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getIsEquip() {
        return this.isEquip;
    }
    public void setIsEquip(int isEquip) {
        this.isEquip = isEquip;
    }
    public int getStrengthenLevel() {
        return this.strengthenLevel;
    }
    public void setStrengthenLevel(int strengthenLevel) {
        this.strengthenLevel = strengthenLevel;
    }
    public int getIsSelect() {
        return this.isSelect;
    }
    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }
    public Long getRandomAttrId() {
        return this.randomAttrId;
    }
    public void setRandomAttrId(Long randomAttrId) {
        this.randomAttrId = randomAttrId;
    }
    @Generated(hash = 911030224)
    private transient Long randomAttr__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 205846429)
    public RandomAttr getRandomAttr() {
        Long __key = this.randomAttrId;
        if (randomAttr__resolvedKey == null
                || !randomAttr__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RandomAttrDao targetDao = daoSession.getRandomAttrDao();
            RandomAttr randomAttrNew = targetDao.load(__key);
            synchronized (this) {
                randomAttr = randomAttrNew;
                randomAttr__resolvedKey = __key;
            }
        }
        return randomAttr;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1875508825)
    public void setRandomAttr(RandomAttr randomAttr) {
        synchronized (this) {
            this.randomAttr = randomAttr;
            randomAttrId = randomAttr == null ? null : randomAttr.getId();
            randomAttr__resolvedKey = randomAttrId;
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
    @Generated(hash = 93078116)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPackagesDao() : null;
    }

}
