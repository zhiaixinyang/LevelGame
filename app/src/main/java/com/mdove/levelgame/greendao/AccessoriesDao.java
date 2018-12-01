package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.entity.Accessories;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACCESSORIES".
*/
public class AccessoriesDao extends AbstractDao<Accessories, Long> {

    public static final String TABLENAME = "ACCESSORIES";

    /**
     * Properties of entity Accessories.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Tips = new Property(2, String.class, "tips", false, "TIPS");
        public final static Property Attack = new Property(3, int.class, "attack", false, "ATTACK");
        public final static Property Armor = new Property(4, int.class, "armor", false, "ARMOR");
        public final static Property Life = new Property(5, int.class, "life", false, "LIFE");
        public final static Property Price = new Property(6, long.class, "price", false, "PRICE");
        public final static Property Type = new Property(7, String.class, "type", false, "TYPE");
        public final static Property IsCanStrengthen = new Property(8, int.class, "isCanStrengthen", false, "IS_CAN_STRENGTHEN");
        public final static Property IsCanUpdate = new Property(9, int.class, "isCanUpdate", false, "IS_CAN_UPDATE");
        public final static Property IsCanMixture = new Property(10, int.class, "isCanMixture", false, "IS_CAN_MIXTURE");
        public final static Property MixtureFormula = new Property(11, String.class, "mixtureFormula", false, "MIXTURE_FORMULA");
        public final static Property UpdateFormula = new Property(12, String.class, "updateFormula", false, "UPDATE_FORMULA");
        public final static Property StrengthenFormula = new Property(13, String.class, "strengthenFormula", false, "STRENGTHEN_FORMULA");
        public final static Property IsSpecial = new Property(14, int.class, "isSpecial", false, "IS_SPECIAL");
        public final static Property BelongMonsterId = new Property(15, String.class, "belongMonsterId", false, "BELONG_MONSTER_ID");
        public final static Property Position = new Property(16, int.class, "position", false, "POSITION");
    }


    public AccessoriesDao(DaoConfig config) {
        super(config);
    }
    
    public AccessoriesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCESSORIES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TIPS\" TEXT," + // 2: tips
                "\"ATTACK\" INTEGER NOT NULL ," + // 3: attack
                "\"ARMOR\" INTEGER NOT NULL ," + // 4: armor
                "\"LIFE\" INTEGER NOT NULL ," + // 5: life
                "\"PRICE\" INTEGER NOT NULL ," + // 6: price
                "\"TYPE\" TEXT," + // 7: type
                "\"IS_CAN_STRENGTHEN\" INTEGER NOT NULL ," + // 8: isCanStrengthen
                "\"IS_CAN_UPDATE\" INTEGER NOT NULL ," + // 9: isCanUpdate
                "\"IS_CAN_MIXTURE\" INTEGER NOT NULL ," + // 10: isCanMixture
                "\"MIXTURE_FORMULA\" TEXT," + // 11: mixtureFormula
                "\"UPDATE_FORMULA\" TEXT," + // 12: updateFormula
                "\"STRENGTHEN_FORMULA\" TEXT," + // 13: strengthenFormula
                "\"IS_SPECIAL\" INTEGER NOT NULL ," + // 14: isSpecial
                "\"BELONG_MONSTER_ID\" TEXT," + // 15: belongMonsterId
                "\"POSITION\" INTEGER NOT NULL );"); // 16: position
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCESSORIES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Accessories entity) {
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
        stmt.bindLong(6, entity.getLife());
        stmt.bindLong(7, entity.getPrice());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(8, type);
        }
        stmt.bindLong(9, entity.getIsCanStrengthen());
        stmt.bindLong(10, entity.getIsCanUpdate());
        stmt.bindLong(11, entity.getIsCanMixture());
 
        String mixtureFormula = entity.getMixtureFormula();
        if (mixtureFormula != null) {
            stmt.bindString(12, mixtureFormula);
        }
 
        String updateFormula = entity.getUpdateFormula();
        if (updateFormula != null) {
            stmt.bindString(13, updateFormula);
        }
 
        String strengthenFormula = entity.getStrengthenFormula();
        if (strengthenFormula != null) {
            stmt.bindString(14, strengthenFormula);
        }
        stmt.bindLong(15, entity.getIsSpecial());
 
        String belongMonsterId = entity.getBelongMonsterId();
        if (belongMonsterId != null) {
            stmt.bindString(16, belongMonsterId);
        }
        stmt.bindLong(17, entity.getPosition());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Accessories entity) {
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
        stmt.bindLong(6, entity.getLife());
        stmt.bindLong(7, entity.getPrice());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(8, type);
        }
        stmt.bindLong(9, entity.getIsCanStrengthen());
        stmt.bindLong(10, entity.getIsCanUpdate());
        stmt.bindLong(11, entity.getIsCanMixture());
 
        String mixtureFormula = entity.getMixtureFormula();
        if (mixtureFormula != null) {
            stmt.bindString(12, mixtureFormula);
        }
 
        String updateFormula = entity.getUpdateFormula();
        if (updateFormula != null) {
            stmt.bindString(13, updateFormula);
        }
 
        String strengthenFormula = entity.getStrengthenFormula();
        if (strengthenFormula != null) {
            stmt.bindString(14, strengthenFormula);
        }
        stmt.bindLong(15, entity.getIsSpecial());
 
        String belongMonsterId = entity.getBelongMonsterId();
        if (belongMonsterId != null) {
            stmt.bindString(16, belongMonsterId);
        }
        stmt.bindLong(17, entity.getPosition());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Accessories readEntity(Cursor cursor, int offset) {
        Accessories entity = new Accessories( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tips
            cursor.getInt(offset + 3), // attack
            cursor.getInt(offset + 4), // armor
            cursor.getInt(offset + 5), // life
            cursor.getLong(offset + 6), // price
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // type
            cursor.getInt(offset + 8), // isCanStrengthen
            cursor.getInt(offset + 9), // isCanUpdate
            cursor.getInt(offset + 10), // isCanMixture
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // mixtureFormula
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // updateFormula
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // strengthenFormula
            cursor.getInt(offset + 14), // isSpecial
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // belongMonsterId
            cursor.getInt(offset + 16) // position
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Accessories entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTips(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAttack(cursor.getInt(offset + 3));
        entity.setArmor(cursor.getInt(offset + 4));
        entity.setLife(cursor.getInt(offset + 5));
        entity.setPrice(cursor.getLong(offset + 6));
        entity.setType(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsCanStrengthen(cursor.getInt(offset + 8));
        entity.setIsCanUpdate(cursor.getInt(offset + 9));
        entity.setIsCanMixture(cursor.getInt(offset + 10));
        entity.setMixtureFormula(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUpdateFormula(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setStrengthenFormula(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsSpecial(cursor.getInt(offset + 14));
        entity.setBelongMonsterId(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setPosition(cursor.getInt(offset + 16));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Accessories entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Accessories entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Accessories entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
