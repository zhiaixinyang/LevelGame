package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.mdove.levelgame.greendao.DaoSession;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.MyWeaponsDao;

/**
 * Created by MDove on 2018/10/27.
 */
@Entity
public class MyWeapons {
    @Id(autoincrement = true)
    public Long id;
    public Long weaponsId;
    @ToOne(joinProperty = "weaponsId")
    public Weapons weapons;
    // 强化级别
    public int strengthen;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 404748284)
    private transient MyWeaponsDao myDao;
    @Generated(hash = 757094624)
    public MyWeapons(Long id, Long weaponsId, int strengthen) {
        this.id = id;
        this.weaponsId = weaponsId;
        this.strengthen = strengthen;
    }
    @Generated(hash = 624130921)
    public MyWeapons() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getWeaponsId() {
        return this.weaponsId;
    }
    public void setWeaponsId(Long weaponsId) {
        this.weaponsId = weaponsId;
    }
    public int getStrengthen() {
        return this.strengthen;
    }
    public void setStrengthen(int strengthen) {
        this.strengthen = strengthen;
    }
    @Generated(hash = 68919286)
    private transient Long weapons__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1574562698)
    public Weapons getWeapons() {
        Long __key = this.weaponsId;
        if (weapons__resolvedKey == null || !weapons__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WeaponsDao targetDao = daoSession.getWeaponsDao();
            Weapons weaponsNew = targetDao.load(__key);
            synchronized (this) {
                weapons = weaponsNew;
                weapons__resolvedKey = __key;
            }
        }
        return weapons;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1279451665)
    public void setWeapons(Weapons weapons) {
        synchronized (this) {
            this.weapons = weapons;
            weaponsId = weapons == null ? null : weapons.getId();
            weapons__resolvedKey = weaponsId;
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
    @Generated(hash = 415989118)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMyWeaponsDao() : null;
    }
}
