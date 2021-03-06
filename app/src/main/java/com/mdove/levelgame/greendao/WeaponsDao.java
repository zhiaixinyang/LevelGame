package com.mdove.levelgame.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mdove.levelgame.greendao.entity.Weapons;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WEAPONS".
*/
public class WeaponsDao extends AbstractDao<Weapons, Long> {

    public static final String TABLENAME = "WEAPONS";

    /**
     * Properties of entity Weapons.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Tips = new Property(2, String.class, "tips", false, "TIPS");
        public final static Property Attack = new Property(3, int.class, "attack", false, "ATTACK");
        public final static Property Armor = new Property(4, int.class, "armor", false, "ARMOR");
        public final static Property Price = new Property(5, long.class, "price", false, "PRICE");
        public final static Property IsLock = new Property(6, int.class, "isLock", false, "IS_LOCK");
        public final static Property Type = new Property(7, String.class, "type", false, "TYPE");
        public final static Property IsCanStrengthen = new Property(8, int.class, "isCanStrengthen", false, "IS_CAN_STRENGTHEN");
        public final static Property IsCanUpdate = new Property(9, int.class, "isCanUpdate", false, "IS_CAN_UPDATE");
        public final static Property IsCanMixture = new Property(10, int.class, "isCanMixture", false, "IS_CAN_MIXTURE");
        public final static Property IsSpecial = new Property(11, int.class, "isSpecial", false, "IS_SPECIAL");
        public final static Property MixtureFormula = new Property(12, String.class, "mixtureFormula", false, "MIXTURE_FORMULA");
        public final static Property UpdateFormula = new Property(13, String.class, "updateFormula", false, "UPDATE_FORMULA");
        public final static Property StrengthenFormula = new Property(14, String.class, "strengthenFormula", false, "STRENGTHEN_FORMULA");
        public final static Property BelongMonsterId = new Property(15, String.class, "belongMonsterId", false, "BELONG_MONSTER_ID");
        public final static Property AttackSpeed = new Property(16, long.class, "attackSpeed", false, "ATTACK_SPEED");
        public final static Property Position = new Property(17, int.class, "position", false, "POSITION");
        public final static Property NeedLevel = new Property(18, int.class, "needLevel", false, "NEED_LEVEL");
        public final static Property NeedLiLiang = new Property(19, int.class, "needLiLiang", false, "NEED_LI_LIANG");
        public final static Property NeedMinJie = new Property(20, int.class, "needMinJie", false, "NEED_MIN_JIE");
        public final static Property NeedZhiHui = new Property(21, int.class, "needZhiHui", false, "NEED_ZHI_HUI");
        public final static Property NeedQiangZhuang = new Property(22, int.class, "needQiangZhuang", false, "NEED_QIANG_ZHUANG");
    }


    public WeaponsDao(DaoConfig config) {
        super(config);
    }
    
    public WeaponsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WEAPONS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TIPS\" TEXT," + // 2: tips
                "\"ATTACK\" INTEGER NOT NULL ," + // 3: attack
                "\"ARMOR\" INTEGER NOT NULL ," + // 4: armor
                "\"PRICE\" INTEGER NOT NULL ," + // 5: price
                "\"IS_LOCK\" INTEGER NOT NULL ," + // 6: isLock
                "\"TYPE\" TEXT," + // 7: type
                "\"IS_CAN_STRENGTHEN\" INTEGER NOT NULL ," + // 8: isCanStrengthen
                "\"IS_CAN_UPDATE\" INTEGER NOT NULL ," + // 9: isCanUpdate
                "\"IS_CAN_MIXTURE\" INTEGER NOT NULL ," + // 10: isCanMixture
                "\"IS_SPECIAL\" INTEGER NOT NULL ," + // 11: isSpecial
                "\"MIXTURE_FORMULA\" TEXT," + // 12: mixtureFormula
                "\"UPDATE_FORMULA\" TEXT," + // 13: updateFormula
                "\"STRENGTHEN_FORMULA\" TEXT," + // 14: strengthenFormula
                "\"BELONG_MONSTER_ID\" TEXT," + // 15: belongMonsterId
                "\"ATTACK_SPEED\" INTEGER NOT NULL ," + // 16: attackSpeed
                "\"POSITION\" INTEGER NOT NULL ," + // 17: position
                "\"NEED_LEVEL\" INTEGER NOT NULL ," + // 18: needLevel
                "\"NEED_LI_LIANG\" INTEGER NOT NULL ," + // 19: needLiLiang
                "\"NEED_MIN_JIE\" INTEGER NOT NULL ," + // 20: needMinJie
                "\"NEED_ZHI_HUI\" INTEGER NOT NULL ," + // 21: needZhiHui
                "\"NEED_QIANG_ZHUANG\" INTEGER NOT NULL );"); // 22: needQiangZhuang
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WEAPONS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Weapons entity) {
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
        stmt.bindLong(7, entity.getIsLock());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(8, type);
        }
        stmt.bindLong(9, entity.getIsCanStrengthen());
        stmt.bindLong(10, entity.getIsCanUpdate());
        stmt.bindLong(11, entity.getIsCanMixture());
        stmt.bindLong(12, entity.getIsSpecial());
 
        String mixtureFormula = entity.getMixtureFormula();
        if (mixtureFormula != null) {
            stmt.bindString(13, mixtureFormula);
        }
 
        String updateFormula = entity.getUpdateFormula();
        if (updateFormula != null) {
            stmt.bindString(14, updateFormula);
        }
 
        String strengthenFormula = entity.getStrengthenFormula();
        if (strengthenFormula != null) {
            stmt.bindString(15, strengthenFormula);
        }
 
        String belongMonsterId = entity.getBelongMonsterId();
        if (belongMonsterId != null) {
            stmt.bindString(16, belongMonsterId);
        }
        stmt.bindLong(17, entity.getAttackSpeed());
        stmt.bindLong(18, entity.getPosition());
        stmt.bindLong(19, entity.getNeedLevel());
        stmt.bindLong(20, entity.getNeedLiLiang());
        stmt.bindLong(21, entity.getNeedMinJie());
        stmt.bindLong(22, entity.getNeedZhiHui());
        stmt.bindLong(23, entity.getNeedQiangZhuang());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Weapons entity) {
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
        stmt.bindLong(7, entity.getIsLock());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(8, type);
        }
        stmt.bindLong(9, entity.getIsCanStrengthen());
        stmt.bindLong(10, entity.getIsCanUpdate());
        stmt.bindLong(11, entity.getIsCanMixture());
        stmt.bindLong(12, entity.getIsSpecial());
 
        String mixtureFormula = entity.getMixtureFormula();
        if (mixtureFormula != null) {
            stmt.bindString(13, mixtureFormula);
        }
 
        String updateFormula = entity.getUpdateFormula();
        if (updateFormula != null) {
            stmt.bindString(14, updateFormula);
        }
 
        String strengthenFormula = entity.getStrengthenFormula();
        if (strengthenFormula != null) {
            stmt.bindString(15, strengthenFormula);
        }
 
        String belongMonsterId = entity.getBelongMonsterId();
        if (belongMonsterId != null) {
            stmt.bindString(16, belongMonsterId);
        }
        stmt.bindLong(17, entity.getAttackSpeed());
        stmt.bindLong(18, entity.getPosition());
        stmt.bindLong(19, entity.getNeedLevel());
        stmt.bindLong(20, entity.getNeedLiLiang());
        stmt.bindLong(21, entity.getNeedMinJie());
        stmt.bindLong(22, entity.getNeedZhiHui());
        stmt.bindLong(23, entity.getNeedQiangZhuang());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Weapons readEntity(Cursor cursor, int offset) {
        Weapons entity = new Weapons( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tips
            cursor.getInt(offset + 3), // attack
            cursor.getInt(offset + 4), // armor
            cursor.getLong(offset + 5), // price
            cursor.getInt(offset + 6), // isLock
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // type
            cursor.getInt(offset + 8), // isCanStrengthen
            cursor.getInt(offset + 9), // isCanUpdate
            cursor.getInt(offset + 10), // isCanMixture
            cursor.getInt(offset + 11), // isSpecial
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // mixtureFormula
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // updateFormula
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // strengthenFormula
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // belongMonsterId
            cursor.getLong(offset + 16), // attackSpeed
            cursor.getInt(offset + 17), // position
            cursor.getInt(offset + 18), // needLevel
            cursor.getInt(offset + 19), // needLiLiang
            cursor.getInt(offset + 20), // needMinJie
            cursor.getInt(offset + 21), // needZhiHui
            cursor.getInt(offset + 22) // needQiangZhuang
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Weapons entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTips(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAttack(cursor.getInt(offset + 3));
        entity.setArmor(cursor.getInt(offset + 4));
        entity.setPrice(cursor.getLong(offset + 5));
        entity.setIsLock(cursor.getInt(offset + 6));
        entity.setType(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsCanStrengthen(cursor.getInt(offset + 8));
        entity.setIsCanUpdate(cursor.getInt(offset + 9));
        entity.setIsCanMixture(cursor.getInt(offset + 10));
        entity.setIsSpecial(cursor.getInt(offset + 11));
        entity.setMixtureFormula(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUpdateFormula(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setStrengthenFormula(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setBelongMonsterId(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setAttackSpeed(cursor.getLong(offset + 16));
        entity.setPosition(cursor.getInt(offset + 17));
        entity.setNeedLevel(cursor.getInt(offset + 18));
        entity.setNeedLiLiang(cursor.getInt(offset + 19));
        entity.setNeedMinJie(cursor.getInt(offset + 20));
        entity.setNeedZhiHui(cursor.getInt(offset + 21));
        entity.setNeedQiangZhuang(cursor.getInt(offset + 22));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Weapons entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Weapons entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Weapons entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
