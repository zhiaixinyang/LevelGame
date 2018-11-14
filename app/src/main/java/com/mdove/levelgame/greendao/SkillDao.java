package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.entity.Skill;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SKILL".
*/
public class SkillDao extends AbstractDao<Skill, Long> {

    public static final String TABLENAME = "SKILL";

    /**
     * Properties of entity Skill.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Tips = new Property(2, String.class, "tips", false, "TIPS");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property AttackHeavy = new Property(4, float.class, "attackHeavy", false, "ATTACK_HEAVY");
        public final static Property AttackHeavyProbability = new Property(5, float.class, "attackHeavyProbability", false, "ATTACK_HEAVY_PROBABILITY");
        public final static Property BloodSuckProbability = new Property(6, float.class, "bloodSuckProbability", false, "BLOOD_SUCK_PROBABILITY");
        public final static Property IgnoreArmorProbability = new Property(7, float.class, "ignoreArmorProbability", false, "IGNORE_ARMOR_PROBABILITY");
        public final static Property IgnoreAttackProbability = new Property(8, float.class, "ignoreAttackProbability", false, "IGNORE_ATTACK_PROBABILITY");
        public final static Property RealAttack = new Property(9, long.class, "realAttack", false, "REAL_ATTACK");
        public final static Property Dizziness = new Property(10, long.class, "dizziness", false, "DIZZINESS");
        public final static Property DizzinessProbability = new Property(11, float.class, "dizzinessProbability", false, "DIZZINESS_PROBABILITY");
        public final static Property BelongType = new Property(12, String.class, "belongType", false, "BELONG_TYPE");
        public final static Property NeedSkillCount = new Property(13, int.class, "needSkillCount", false, "NEED_SKILL_COUNT");
    }


    public SkillDao(DaoConfig config) {
        super(config);
    }
    
    public SkillDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SKILL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TIPS\" TEXT," + // 2: tips
                "\"TYPE\" TEXT," + // 3: type
                "\"ATTACK_HEAVY\" REAL NOT NULL ," + // 4: attackHeavy
                "\"ATTACK_HEAVY_PROBABILITY\" REAL NOT NULL ," + // 5: attackHeavyProbability
                "\"BLOOD_SUCK_PROBABILITY\" REAL NOT NULL ," + // 6: bloodSuckProbability
                "\"IGNORE_ARMOR_PROBABILITY\" REAL NOT NULL ," + // 7: ignoreArmorProbability
                "\"IGNORE_ATTACK_PROBABILITY\" REAL NOT NULL ," + // 8: ignoreAttackProbability
                "\"REAL_ATTACK\" INTEGER NOT NULL ," + // 9: realAttack
                "\"DIZZINESS\" INTEGER NOT NULL ," + // 10: dizziness
                "\"DIZZINESS_PROBABILITY\" REAL NOT NULL ," + // 11: dizzinessProbability
                "\"BELONG_TYPE\" TEXT," + // 12: belongType
                "\"NEED_SKILL_COUNT\" INTEGER NOT NULL );"); // 13: needSkillCount
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SKILL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Skill entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String tips = entity.getTips();
        if (tips != null) {
            stmt.bindString(3, tips);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
        stmt.bindDouble(5, entity.getAttackHeavy());
        stmt.bindDouble(6, entity.getAttackHeavyProbability());
        stmt.bindDouble(7, entity.getBloodSuckProbability());
        stmt.bindDouble(8, entity.getIgnoreArmorProbability());
        stmt.bindDouble(9, entity.getIgnoreAttackProbability());
        stmt.bindLong(10, entity.getRealAttack());
        stmt.bindLong(11, entity.getDizziness());
        stmt.bindDouble(12, entity.getDizzinessProbability());
 
        String belongType = entity.getBelongType();
        if (belongType != null) {
            stmt.bindString(13, belongType);
        }
        stmt.bindLong(14, entity.getNeedSkillCount());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Skill entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String tips = entity.getTips();
        if (tips != null) {
            stmt.bindString(3, tips);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
        stmt.bindDouble(5, entity.getAttackHeavy());
        stmt.bindDouble(6, entity.getAttackHeavyProbability());
        stmt.bindDouble(7, entity.getBloodSuckProbability());
        stmt.bindDouble(8, entity.getIgnoreArmorProbability());
        stmt.bindDouble(9, entity.getIgnoreAttackProbability());
        stmt.bindLong(10, entity.getRealAttack());
        stmt.bindLong(11, entity.getDizziness());
        stmt.bindDouble(12, entity.getDizzinessProbability());
 
        String belongType = entity.getBelongType();
        if (belongType != null) {
            stmt.bindString(13, belongType);
        }
        stmt.bindLong(14, entity.getNeedSkillCount());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Skill readEntity(Cursor cursor, int offset) {
        Skill entity = new Skill( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tips
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
            cursor.getFloat(offset + 4), // attackHeavy
            cursor.getFloat(offset + 5), // attackHeavyProbability
            cursor.getFloat(offset + 6), // bloodSuckProbability
            cursor.getFloat(offset + 7), // ignoreArmorProbability
            cursor.getFloat(offset + 8), // ignoreAttackProbability
            cursor.getLong(offset + 9), // realAttack
            cursor.getLong(offset + 10), // dizziness
            cursor.getFloat(offset + 11), // dizzinessProbability
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // belongType
            cursor.getInt(offset + 13) // needSkillCount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Skill entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTips(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAttackHeavy(cursor.getFloat(offset + 4));
        entity.setAttackHeavyProbability(cursor.getFloat(offset + 5));
        entity.setBloodSuckProbability(cursor.getFloat(offset + 6));
        entity.setIgnoreArmorProbability(cursor.getFloat(offset + 7));
        entity.setIgnoreAttackProbability(cursor.getFloat(offset + 8));
        entity.setRealAttack(cursor.getLong(offset + 9));
        entity.setDizziness(cursor.getLong(offset + 10));
        entity.setDizzinessProbability(cursor.getFloat(offset + 11));
        entity.setBelongType(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setNeedSkillCount(cursor.getInt(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Skill entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Skill entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Skill entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
