package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.entity.Task;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TASK".
*/
public class TaskDao extends AbstractDao<Task, Long> {

    public static final String TABLENAME = "TASK";

    /**
     * Properties of entity Task.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AttackCount = new Property(1, long.class, "attackCount", false, "ATTACK_COUNT");
        public final static Property TaskContentType = new Property(2, String.class, "taskContentType", false, "TASK_CONTENT_TYPE");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Tips = new Property(4, String.class, "tips", false, "TIPS");
        public final static Property Type = new Property(5, String.class, "type", false, "TYPE");
        public final static Property AwardMoney = new Property(6, long.class, "awardMoney", false, "AWARD_MONEY");
        public final static Property AwardExp = new Property(7, long.class, "awardExp", false, "AWARD_EXP");
        public final static Property AwardAttack = new Property(8, long.class, "awardAttack", false, "AWARD_ATTACK");
        public final static Property AwardArmor = new Property(9, long.class, "awardArmor", false, "AWARD_ARMOR");
        public final static Property AwardMaxLife = new Property(10, long.class, "awardMaxLife", false, "AWARD_MAX_LIFE");
        public final static Property ConsumePower = new Property(11, int.class, "consumePower", false, "CONSUME_POWER");
        public final static Property ConsumeMoney = new Property(12, int.class, "consumeMoney", false, "CONSUME_MONEY");
        public final static Property TaskStatus = new Property(13, int.class, "taskStatus", false, "TASK_STATUS");
        public final static Property ConsumeFormula = new Property(14, String.class, "consumeFormula", false, "CONSUME_FORMULA");
    }


    public TaskDao(DaoConfig config) {
        super(config);
    }
    
    public TaskDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TASK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ATTACK_COUNT\" INTEGER NOT NULL ," + // 1: attackCount
                "\"TASK_CONTENT_TYPE\" TEXT," + // 2: taskContentType
                "\"NAME\" TEXT," + // 3: name
                "\"TIPS\" TEXT," + // 4: tips
                "\"TYPE\" TEXT," + // 5: type
                "\"AWARD_MONEY\" INTEGER NOT NULL ," + // 6: awardMoney
                "\"AWARD_EXP\" INTEGER NOT NULL ," + // 7: awardExp
                "\"AWARD_ATTACK\" INTEGER NOT NULL ," + // 8: awardAttack
                "\"AWARD_ARMOR\" INTEGER NOT NULL ," + // 9: awardArmor
                "\"AWARD_MAX_LIFE\" INTEGER NOT NULL ," + // 10: awardMaxLife
                "\"CONSUME_POWER\" INTEGER NOT NULL ," + // 11: consumePower
                "\"CONSUME_MONEY\" INTEGER NOT NULL ," + // 12: consumeMoney
                "\"TASK_STATUS\" INTEGER NOT NULL ," + // 13: taskStatus
                "\"CONSUME_FORMULA\" TEXT);"); // 14: consumeFormula
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TASK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Task entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAttackCount());
 
        String taskContentType = entity.getTaskContentType();
        if (taskContentType != null) {
            stmt.bindString(3, taskContentType);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String tips = entity.getTips();
        if (tips != null) {
            stmt.bindString(5, tips);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(6, type);
        }
        stmt.bindLong(7, entity.getAwardMoney());
        stmt.bindLong(8, entity.getAwardExp());
        stmt.bindLong(9, entity.getAwardAttack());
        stmt.bindLong(10, entity.getAwardArmor());
        stmt.bindLong(11, entity.getAwardMaxLife());
        stmt.bindLong(12, entity.getConsumePower());
        stmt.bindLong(13, entity.getConsumeMoney());
        stmt.bindLong(14, entity.getTaskStatus());
 
        String consumeFormula = entity.getConsumeFormula();
        if (consumeFormula != null) {
            stmt.bindString(15, consumeFormula);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Task entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAttackCount());
 
        String taskContentType = entity.getTaskContentType();
        if (taskContentType != null) {
            stmt.bindString(3, taskContentType);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String tips = entity.getTips();
        if (tips != null) {
            stmt.bindString(5, tips);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(6, type);
        }
        stmt.bindLong(7, entity.getAwardMoney());
        stmt.bindLong(8, entity.getAwardExp());
        stmt.bindLong(9, entity.getAwardAttack());
        stmt.bindLong(10, entity.getAwardArmor());
        stmt.bindLong(11, entity.getAwardMaxLife());
        stmt.bindLong(12, entity.getConsumePower());
        stmt.bindLong(13, entity.getConsumeMoney());
        stmt.bindLong(14, entity.getTaskStatus());
 
        String consumeFormula = entity.getConsumeFormula();
        if (consumeFormula != null) {
            stmt.bindString(15, consumeFormula);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Task readEntity(Cursor cursor, int offset) {
        Task entity = new Task( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // attackCount
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // taskContentType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // tips
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // type
            cursor.getLong(offset + 6), // awardMoney
            cursor.getLong(offset + 7), // awardExp
            cursor.getLong(offset + 8), // awardAttack
            cursor.getLong(offset + 9), // awardArmor
            cursor.getLong(offset + 10), // awardMaxLife
            cursor.getInt(offset + 11), // consumePower
            cursor.getInt(offset + 12), // consumeMoney
            cursor.getInt(offset + 13), // taskStatus
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // consumeFormula
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Task entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAttackCount(cursor.getLong(offset + 1));
        entity.setTaskContentType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTips(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAwardMoney(cursor.getLong(offset + 6));
        entity.setAwardExp(cursor.getLong(offset + 7));
        entity.setAwardAttack(cursor.getLong(offset + 8));
        entity.setAwardArmor(cursor.getLong(offset + 9));
        entity.setAwardMaxLife(cursor.getLong(offset + 10));
        entity.setConsumePower(cursor.getInt(offset + 11));
        entity.setConsumeMoney(cursor.getInt(offset + 12));
        entity.setTaskStatus(cursor.getInt(offset + 13));
        entity.setConsumeFormula(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Task entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Task entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Task entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
