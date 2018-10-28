package com.mdove.levelgame.main.shop.manager;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.utils.JsonUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by MDove on 2018/10/28.
 */

public class BlacksmithManager {
    private static class SingletonHolder {
        static final BlacksmithManager INSTANCE = new BlacksmithManager();
    }

    public static BlacksmithManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<BlacksmithResp> attackUpdate(Long id) {
        final BlacksmithResp blacksmithResp = new BlacksmithResp();
        Weapons weapon = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.Id.eq(id)).unique();
        if (weapon != null) {
            // 可升级
            if ( weapon.isCanUpdate == 0) {
                // 判断材料是否完全
                String formula = weapon.updateFormula;
                List<NeedMaterial> need = JsonUtil.decode(formula, new TypeToken<List<NeedMaterial>>() {
                }.getType());
                if (need != null && need.size() > 0) {
                    HasMaterial hasMaterial = null;
                    HasAttack hasAttack = null;
                    for (NeedMaterial type : need) {
                        if (type.type.startsWith("E")) {
                            hasMaterial = hasMaterial(type.type);
                            continue;
                        }
                        if (type.type.startsWith("A")) {
                            hasAttack = hasAttack(type.type);
                            continue;
                        }
                    }
                    // 升级成功
                    if (hasAttack != null && hasMaterial != null && hasAttack.isHas && hasMaterial.isHas) {
                        // 删除低级装备
                        DatabaseManager.getInstance().getWeaponsDao().delete(hasAttack.weapons);
                        // 删除低级材料
                        DatabaseManager.getInstance().getPackagesDao().delete(hasMaterial.packages);
                        // 生成新装备
                        Packages packages = new Packages();
                        packages.isEquip = 1;
                        packages.strengthenLevel = 0;
                        packages.type = weapon.type;
                        DatabaseManager.getInstance().getPackagesDao().insert(packages);
                        blacksmithResp.isSuc = true;
                        blacksmithResp.title = "合成成功";
                        blacksmithResp.content = weapon.name + "合成成功。";
                    } else {
                        blacksmithResp.isSuc = false;
                        blacksmithResp.title = "合成失败";
                        blacksmithResp.content = "缺少材料：请确认所需材料都持有。";
                    }
                } else {
                    blacksmithResp.isSuc = false;
                    blacksmithResp.title = "未知错误";
                    blacksmithResp.content = "获取升级材料异常：null。";
                }
            }else if (weapon.isCanMixture==0){
                // 可合成
            }
        } else {
            blacksmithResp.isSuc = false;
            blacksmithResp.title = "未知错误";
            blacksmithResp.content = "未找到对应升级的装备...";
        }
        return Observable.create(new ObservableOnSubscribe<BlacksmithResp>() {
            @Override
            public void subscribe(ObservableEmitter<BlacksmithResp> e) throws Exception {
                e.onNext(blacksmithResp);
            }
        });
    }

    private HasMaterial hasMaterial(String type) {
        HasMaterial material = new HasMaterial();
        Packages packages = DatabaseManager.getInstance().getPackagesDao().queryBuilder().where(PackagesDao.Properties.Type.eq(type)).unique();
        if (packages != null) {
            material.isHas = true;
            material.packages = packages;
        } else {
            material.isHas = false;
        }
        return material;
    }

    private HasAttack hasAttack(String type) {
        HasAttack material = new HasAttack();
        Weapons packages = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.Type.eq(type)).unique();
        if (packages != null) {
            material.isHas = true;
            material.weapons = packages;
        } else {
            material.isHas = false;
        }
        return material;
    }

    private class NeedMaterial {
        public String type;
    }

    public class BlacksmithResp {
        public boolean isSuc;
        public String title;
        public String content;
    }

    public class HasMaterial {
        public boolean isHas;
        public Packages packages;
    }

    public class HasAttack {
        public boolean isHas;
        public Weapons weapons;
    }
}
