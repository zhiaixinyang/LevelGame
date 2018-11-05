package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.entity.HeroAttributes;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HERO_ATTRIBUTES".
*/
public class HeroAttributesDao extends AbstractDao<HeroAttributes, Long> {

    public static final String TABLENAME = "HERO_ATTRIBUTES";

    /**
     * Properties of entity HeroAttributes.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Attack = new Property(1, int.class, "attack", false, "ATTACK");
        public final static Property AttackIncrease = new Property(2, int.class, "attackIncrease", false, "ATTACK_INCREASE");
        public final static Property Armor = new Property(3, int.class, "armor", false, "ARMOR");
        public final static Property ArmorIncrease = new Property(4, int.class, "armorIncrease", false, "ARMOR_INCREASE");
        public final static Property CurLife = new Property(5, int.class, "curLife", false, "CUR_LIFE");
        public final static Property LifeIncrease = new Property(6, int.class, "lifeIncrease", false, "LIFE_INCREASE");
        public final static Property MaxLife = new Property(7, int.class, "maxLife", false, "MAX_LIFE");
        public final static Property Money = new Property(8, int.class, "money", false, "MONEY");
        public final static Property Level = new Property(9, int.class, "level", false, "LEVEL");
        public final static Property Experience = new Property(10, long.class, "experience", false, "EXPERIENCE");
        public final static Property BaseExp = new Property(11, long.class, "baseExp", false, "BASE_EXP");
        public final static Property ExpMultiple = new Property(12, int.class, "expMultiple", false, "EXP_MULTIPLE");
        public final static Property BodyPower = new Property(13, int.class, "bodyPower", false, "BODY_POWER");
        public final static Property Days = new Property(14, int.class, "days", false, "DAYS");
        public final static Property AttackSpeed = new Property(15, long.class, "attackSpeed", false, "ATTACK_SPEED");
    }


    public HeroAttributesDao(DaoConfig config) {
        super(config);
    }
    
    public HeroAttributesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HERO_ATTRIBUTES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ATTACK\" INTEGER NOT NULL ," + // 1: attack
                "\"ATTACK_INCREASE\" INTEGER NOT NULL ," + // 2: attackIncrease
                "\"ARMOR\" INTEGER NOT NULL ," + // 3: armor
                "\"ARMOR_INCREASE\" INTEGER NOT NULL ," + // 4: armorIncrease
                "\"CUR_LIFE\" INTEGER NOT NULL ," + // 5: curLife
                "\"LIFE_INCREASE\" INTEGER NOT NULL ," + // 6: lifeIncrease
                "\"MAX_LIFE\" INTEGER NOT NULL ," + // 7: maxLife
                "\"MONEY\" INTEGER NOT NULL ," + // 8: money
                "\"LEVEL\" INTEGER NOT NULL ," + // 9: level
                "\"EXPERIENCE\" INTEGER NOT NULL ," + // 10: experience
                "\"BASE_EXP\" INTEGER NOT NULL ," + // 11: baseExp
                "\"EXP_MULTIPLE\" INTEGER NOT NULL ," + // 12: expMultiple
                "\"BODY_POWER\" INTEGER NOT NULL ," + // 13: bodyPower
                "\"DAYS\" INTEGER NOT NULL ," + // 14: days
                "\"ATTACK_SPEED\" INTEGER NOT NULL );"); // 15: attackSpeed
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HERO_ATTRIBUTES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HeroAttributes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAttack());
        stmt.bindLong(3, entity.getAttackIncrease());
        stmt.bindLong(4, entity.getArmor());
        stmt.bindLong(5, entity.getArmorIncrease());
        stmt.bindLong(6, entity.getCurLife());
        stmt.bindLong(7, entity.getLifeIncrease());
        stmt.bindLong(8, entity.getMaxLife());
        stmt.bindLong(9, entity.getMoney());
        stmt.bindLong(10, entity.getLevel());
        stmt.bindLong(11, entity.getExperience());
        stmt.bindLong(12, entity.getBaseExp());
        stmt.bindLong(13, entity.getExpMultiple());
        stmt.bindLong(14, entity.getBodyPower());
        stmt.bindLong(15, entity.getDays());
        stmt.bindLong(16, entity.getAttackSpeed());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HeroAttributes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAttack());
        stmt.bindLong(3, entity.getAttackIncrease());
        stmt.bindLong(4, entity.getArmor());
        stmt.bindLong(5, entity.getArmorIncrease());
        stmt.bindLong(6, entity.getCurLife());
        stmt.bindLong(7, entity.getLifeIncrease());
        stmt.bindLong(8, entity.getMaxLife());
        stmt.bindLong(9, entity.getMoney());
        stmt.bindLong(10, entity.getLevel());
        stmt.bindLong(11, entity.getExperience());
        stmt.bindLong(12, entity.getBaseExp());
        stmt.bindLong(13, entity.getExpMultiple());
        stmt.bindLong(14, entity.getBodyPower());
        stmt.bindLong(15, entity.getDays());
        stmt.bindLong(16, entity.getAttackSpeed());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public HeroAttributes readEntity(Cursor cursor, int offset) {
        HeroAttributes entity = new HeroAttributes( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // attack
            cursor.getInt(offset + 2), // attackIncrease
            cursor.getInt(offset + 3), // armor
            cursor.getInt(offset + 4), // armorIncrease
            cursor.getInt(offset + 5), // curLife
            cursor.getInt(offset + 6), // lifeIncrease
            cursor.getInt(offset + 7), // maxLife
            cursor.getInt(offset + 8), // money
            cursor.getInt(offset + 9), // level
            cursor.getLong(offset + 10), // experience
            cursor.getLong(offset + 11), // baseExp
            cursor.getInt(offset + 12), // expMultiple
            cursor.getInt(offset + 13), // bodyPower
            cursor.getInt(offset + 14), // days
            cursor.getLong(offset + 15) // attackSpeed
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HeroAttributes entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAttack(cursor.getInt(offset + 1));
        entity.setAttackIncrease(cursor.getInt(offset + 2));
        entity.setArmor(cursor.getInt(offset + 3));
        entity.setArmorIncrease(cursor.getInt(offset + 4));
        entity.setCurLife(cursor.getInt(offset + 5));
        entity.setLifeIncrease(cursor.getInt(offset + 6));
        entity.setMaxLife(cursor.getInt(offset + 7));
        entity.setMoney(cursor.getInt(offset + 8));
        entity.setLevel(cursor.getInt(offset + 9));
        entity.setExperience(cursor.getLong(offset + 10));
        entity.setBaseExp(cursor.getLong(offset + 11));
        entity.setExpMultiple(cursor.getInt(offset + 12));
        entity.setBodyPower(cursor.getInt(offset + 13));
        entity.setDays(cursor.getInt(offset + 14));
        entity.setAttackSpeed(cursor.getLong(offset + 15));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HeroAttributes entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HeroAttributes entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HeroAttributes entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
