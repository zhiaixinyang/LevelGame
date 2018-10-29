package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.entity.Monsters;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MONSTERS".
*/
public class MonstersDao extends AbstractDao<Monsters, Long> {

    public static final String TABLENAME = "MONSTERS";

    /**
     * Properties of entity Monsters.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Life = new Property(1, int.class, "life", false, "LIFE");
        public final static Property Type = new Property(2, String.class, "type", false, "TYPE");
        public final static Property Attack = new Property(3, int.class, "attack", false, "ATTACK");
        public final static Property Armor = new Property(4, int.class, "armor", false, "ARMOR");
        public final static Property Money = new Property(5, int.class, "money", false, "MONEY");
        public final static Property Tips = new Property(6, String.class, "tips", false, "TIPS");
        public final static Property Name = new Property(7, String.class, "name", false, "NAME");
        public final static Property DropGoodsId = new Property(8, long.class, "dropGoodsId", false, "DROP_GOODS_ID");
        public final static Property MonsterPlaceId = new Property(9, long.class, "monsterPlaceId", false, "MONSTER_PLACE_ID");
        public final static Property Exp = new Property(10, long.class, "exp", false, "EXP");
        public final static Property ConsumePower = new Property(11, int.class, "consumePower", false, "CONSUME_POWER");
        public final static Property IsBusinessman = new Property(12, int.class, "isBusinessman", false, "IS_BUSINESSMAN");
        public final static Property SellGoodsJson = new Property(13, String.class, "sellGoodsJson", false, "SELL_GOODS_JSON");
        public final static Property IsShow = new Property(14, int.class, "isShow", false, "IS_SHOW");
    }


    public MonstersDao(DaoConfig config) {
        super(config);
    }
    
    public MonstersDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MONSTERS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"LIFE\" INTEGER NOT NULL ," + // 1: life
                "\"TYPE\" TEXT," + // 2: type
                "\"ATTACK\" INTEGER NOT NULL ," + // 3: attack
                "\"ARMOR\" INTEGER NOT NULL ," + // 4: armor
                "\"MONEY\" INTEGER NOT NULL ," + // 5: money
                "\"TIPS\" TEXT," + // 6: tips
                "\"NAME\" TEXT," + // 7: name
                "\"DROP_GOODS_ID\" INTEGER NOT NULL ," + // 8: dropGoodsId
                "\"MONSTER_PLACE_ID\" INTEGER NOT NULL ," + // 9: monsterPlaceId
                "\"EXP\" INTEGER NOT NULL ," + // 10: exp
                "\"CONSUME_POWER\" INTEGER NOT NULL ," + // 11: consumePower
                "\"IS_BUSINESSMAN\" INTEGER NOT NULL ," + // 12: isBusinessman
                "\"SELL_GOODS_JSON\" TEXT," + // 13: sellGoodsJson
                "\"IS_SHOW\" INTEGER NOT NULL );"); // 14: isShow
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MONSTERS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Monsters entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getLife());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(3, type);
        }
        stmt.bindLong(4, entity.getAttack());
        stmt.bindLong(5, entity.getArmor());
        stmt.bindLong(6, entity.getMoney());
 
        String tips = entity.getTips();
        if (tips != null) {
            stmt.bindString(7, tips);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
        stmt.bindLong(9, entity.getDropGoodsId());
        stmt.bindLong(10, entity.getMonsterPlaceId());
        stmt.bindLong(11, entity.getExp());
        stmt.bindLong(12, entity.getConsumePower());
        stmt.bindLong(13, entity.getIsBusinessman());
 
        String sellGoodsJson = entity.getSellGoodsJson();
        if (sellGoodsJson != null) {
            stmt.bindString(14, sellGoodsJson);
        }
        stmt.bindLong(15, entity.getIsShow());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Monsters entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getLife());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(3, type);
        }
        stmt.bindLong(4, entity.getAttack());
        stmt.bindLong(5, entity.getArmor());
        stmt.bindLong(6, entity.getMoney());
 
        String tips = entity.getTips();
        if (tips != null) {
            stmt.bindString(7, tips);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
        stmt.bindLong(9, entity.getDropGoodsId());
        stmt.bindLong(10, entity.getMonsterPlaceId());
        stmt.bindLong(11, entity.getExp());
        stmt.bindLong(12, entity.getConsumePower());
        stmt.bindLong(13, entity.getIsBusinessman());
 
        String sellGoodsJson = entity.getSellGoodsJson();
        if (sellGoodsJson != null) {
            stmt.bindString(14, sellGoodsJson);
        }
        stmt.bindLong(15, entity.getIsShow());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Monsters readEntity(Cursor cursor, int offset) {
        Monsters entity = new Monsters( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // life
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // type
            cursor.getInt(offset + 3), // attack
            cursor.getInt(offset + 4), // armor
            cursor.getInt(offset + 5), // money
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // tips
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // name
            cursor.getLong(offset + 8), // dropGoodsId
            cursor.getLong(offset + 9), // monsterPlaceId
            cursor.getLong(offset + 10), // exp
            cursor.getInt(offset + 11), // consumePower
            cursor.getInt(offset + 12), // isBusinessman
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // sellGoodsJson
            cursor.getInt(offset + 14) // isShow
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Monsters entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLife(cursor.getInt(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAttack(cursor.getInt(offset + 3));
        entity.setArmor(cursor.getInt(offset + 4));
        entity.setMoney(cursor.getInt(offset + 5));
        entity.setTips(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDropGoodsId(cursor.getLong(offset + 8));
        entity.setMonsterPlaceId(cursor.getLong(offset + 9));
        entity.setExp(cursor.getLong(offset + 10));
        entity.setConsumePower(cursor.getInt(offset + 11));
        entity.setIsBusinessman(cursor.getInt(offset + 12));
        entity.setSellGoodsJson(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsShow(cursor.getInt(offset + 14));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Monsters entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Monsters entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Monsters entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
