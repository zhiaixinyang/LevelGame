package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.utils.IntegerConverter;
import java.util.List;

import com.mdove.levelgame.greendao.entity.City;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CITY".
*/
public class CityDao extends AbstractDao<City, Long> {

    public static final String TABLENAME = "CITY";

    /**
     * Properties of entity City.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Tips = new Property(2, String.class, "tips", false, "TIPS");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property MenuBtnListId = new Property(4, String.class, "menuBtnListId", false, "MENU_BTN_LIST_ID");
        public final static Property EnableOpen = new Property(5, int.class, "enableOpen", false, "ENABLE_OPEN");
        public final static Property ClickId = new Property(6, int.class, "clickId", false, "CLICK_ID");
        public final static Property IsShow = new Property(7, int.class, "isShow", false, "IS_SHOW");
        public final static Property IsAdventure = new Property(8, int.class, "isAdventure", false, "IS_ADVENTURE");
        public final static Property Position = new Property(9, int.class, "position", false, "POSITION");
        public final static Property IsMonsterPlace = new Property(10, int.class, "isMonsterPlace", false, "IS_MONSTER_PLACE");
    }

    private final IntegerConverter menuBtnListIdConverter = new IntegerConverter();

    public CityDao(DaoConfig config) {
        super(config);
    }
    
    public CityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TIPS\" TEXT," + // 2: tips
                "\"TYPE\" TEXT," + // 3: type
                "\"MENU_BTN_LIST_ID\" TEXT," + // 4: menuBtnListId
                "\"ENABLE_OPEN\" INTEGER NOT NULL ," + // 5: enableOpen
                "\"CLICK_ID\" INTEGER NOT NULL ," + // 6: clickId
                "\"IS_SHOW\" INTEGER NOT NULL ," + // 7: isShow
                "\"IS_ADVENTURE\" INTEGER NOT NULL ," + // 8: isAdventure
                "\"POSITION\" INTEGER NOT NULL ," + // 9: position
                "\"IS_MONSTER_PLACE\" INTEGER NOT NULL );"); // 10: isMonsterPlace
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, City entity) {
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
 
        List menuBtnListId = entity.getMenuBtnListId();
        if (menuBtnListId != null) {
            stmt.bindString(5, menuBtnListIdConverter.convertToDatabaseValue(menuBtnListId));
        }
        stmt.bindLong(6, entity.getEnableOpen());
        stmt.bindLong(7, entity.getClickId());
        stmt.bindLong(8, entity.getIsShow());
        stmt.bindLong(9, entity.getIsAdventure());
        stmt.bindLong(10, entity.getPosition());
        stmt.bindLong(11, entity.getIsMonsterPlace());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, City entity) {
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
 
        List menuBtnListId = entity.getMenuBtnListId();
        if (menuBtnListId != null) {
            stmt.bindString(5, menuBtnListIdConverter.convertToDatabaseValue(menuBtnListId));
        }
        stmt.bindLong(6, entity.getEnableOpen());
        stmt.bindLong(7, entity.getClickId());
        stmt.bindLong(8, entity.getIsShow());
        stmt.bindLong(9, entity.getIsAdventure());
        stmt.bindLong(10, entity.getPosition());
        stmt.bindLong(11, entity.getIsMonsterPlace());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public City readEntity(Cursor cursor, int offset) {
        City entity = new City( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tips
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
            cursor.isNull(offset + 4) ? null : menuBtnListIdConverter.convertToEntityProperty(cursor.getString(offset + 4)), // menuBtnListId
            cursor.getInt(offset + 5), // enableOpen
            cursor.getInt(offset + 6), // clickId
            cursor.getInt(offset + 7), // isShow
            cursor.getInt(offset + 8), // isAdventure
            cursor.getInt(offset + 9), // position
            cursor.getInt(offset + 10) // isMonsterPlace
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, City entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTips(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMenuBtnListId(cursor.isNull(offset + 4) ? null : menuBtnListIdConverter.convertToEntityProperty(cursor.getString(offset + 4)));
        entity.setEnableOpen(cursor.getInt(offset + 5));
        entity.setClickId(cursor.getInt(offset + 6));
        entity.setIsShow(cursor.getInt(offset + 7));
        entity.setIsAdventure(cursor.getInt(offset + 8));
        entity.setPosition(cursor.getInt(offset + 9));
        entity.setIsMonsterPlace(cursor.getInt(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(City entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(City entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(City entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}