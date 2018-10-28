package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.entity.Armors;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ARMORS".
*/
public class ArmorsDao extends AbstractDao<Armors, Long> {

    public static final String TABLENAME = "ARMORS";

    /**
     * Properties of entity Armors.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Tips = new Property(2, String.class, "tips", false, "TIPS");
        public final static Property Attack = new Property(3, int.class, "attack", false, "ATTACK");
        public final static Property Armor = new Property(4, int.class, "armor", false, "ARMOR");
        public final static Property Price = new Property(5, long.class, "price", false, "PRICE");
        public final static Property Type = new Property(6, String.class, "type", false, "TYPE");
        public final static Property Strengthen = new Property(7, int.class, "strengthen", false, "STRENGTHEN");
        public final static Property IsMy = new Property(8, int.class, "isMy", false, "IS_MY");
    }


    public ArmorsDao(DaoConfig config) {
        super(config);
    }
    
    public ArmorsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ARMORS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TIPS\" TEXT," + // 2: tips
                "\"ATTACK\" INTEGER NOT NULL ," + // 3: attack
                "\"ARMOR\" INTEGER NOT NULL ," + // 4: armor
                "\"PRICE\" INTEGER NOT NULL ," + // 5: price
                "\"TYPE\" TEXT," + // 6: type
                "\"STRENGTHEN\" INTEGER NOT NULL ," + // 7: strengthen
                "\"IS_MY\" INTEGER NOT NULL );"); // 8: isMy
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ARMORS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Armors entity) {
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
        stmt.bindLong(4, entity.getAttack());
        stmt.bindLong(5, entity.getArmor());
        stmt.bindLong(6, entity.getPrice());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(7, type);
        }
        stmt.bindLong(8, entity.getStrengthen());
        stmt.bindLong(9, entity.getIsMy());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Armors entity) {
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
        stmt.bindLong(4, entity.getAttack());
        stmt.bindLong(5, entity.getArmor());
        stmt.bindLong(6, entity.getPrice());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(7, type);
        }
        stmt.bindLong(8, entity.getStrengthen());
        stmt.bindLong(9, entity.getIsMy());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Armors readEntity(Cursor cursor, int offset) {
        Armors entity = new Armors( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tips
            cursor.getInt(offset + 3), // attack
            cursor.getInt(offset + 4), // armor
            cursor.getLong(offset + 5), // price
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // type
            cursor.getInt(offset + 7), // strengthen
            cursor.getInt(offset + 8) // isMy
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Armors entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTips(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAttack(cursor.getInt(offset + 3));
        entity.setArmor(cursor.getInt(offset + 4));
        entity.setPrice(cursor.getLong(offset + 5));
        entity.setType(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setStrengthen(cursor.getInt(offset + 7));
        entity.setIsMy(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Armors entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Armors entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Armors entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
