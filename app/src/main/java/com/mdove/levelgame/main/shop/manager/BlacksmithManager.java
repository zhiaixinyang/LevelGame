package com.mdove.levelgame.main.shop.manager;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.utils.JsonUtil;

import java.util.ArrayList;
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
            if (weapon.isCanUpdate == 0) {
                // 判断材料是否完全
                String formula = weapon.updateFormula;
                List<NeedMaterial> need = JsonUtil.decode(formula, new TypeToken<List<NeedMaterial>>() {
                }.getType());
                if (need != null && need.size() > 0) {
                    List<HasMaterial> hasMaterials = new ArrayList<>();
                    for (NeedMaterial type : need) {
                        hasMaterials.add(hasMaterial(type.type));
                    }
                    boolean isSuc = true;
                    for (HasMaterial material : hasMaterials) {
                        if (!material.isHas) {
                            isSuc = false;
                            break;
                        }
                    }
                    // 升级成功
                    if (isSuc) {
                        // 删除低级材料（装备+石头，直接从Pk中）
                        deleteMaterial(hasMaterials);
                        // 生成新装备
                        newPk(weapon);
                        blacksmithResp.isSuc = true;
                        blacksmithResp.title = "升级成功";
                        blacksmithResp.content = weapon.name + "升级成功。";
                    } else {
                        blacksmithResp.isSuc = false;
                        blacksmithResp.title = "升级失败";
                        blacksmithResp.content = "缺少材料：请确认所需材料都持有。";
                    }
                } else {
                    blacksmithResp.isSuc = false;
                    blacksmithResp.title = "未知错误";
                    blacksmithResp.content = "获取升级材料异常：null。";
                }
            } else if (weapon.isCanMixture == 0) {
                // 可合成
                // 判断材料是否完全
                String formula = weapon.mixtureFormula;
                List<NeedMaterial> need = JsonUtil.decode(formula, new TypeToken<List<NeedMaterial>>() {
                }.getType());
                if (need != null && need.size() > 0) {
                    List<HasMaterial> hasMaterials = new ArrayList<>();
                    for (NeedMaterial type : need) {
                        hasMaterials.add(hasMaterial(type.type));
                    }
                    boolean isSuc = true;
                    for (HasMaterial attack : hasMaterials) {
                        if (!attack.isHas) {
                            isSuc = false;
                            break;
                        }
                    }
                    // 合成成功
                    if (isSuc) {
                        // 删除低级材料（装备+石头，直接从Pk中）
                        deleteMaterial(hasMaterials);
                        // 生成新装备
                        newPk(weapon);
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

    private void newPk(Weapons weapon) {
        Packages packages = new Packages();
        packages.isEquip = 1;
        packages.strengthenLevel = 0;
        packages.type = weapon.type;
        DatabaseManager.getInstance().getPackagesDao().insert(packages);
    }

    private void deleteMaterial(List<HasMaterial> hasMaterials) {
        for (HasMaterial material : hasMaterials) {
            PackagesDao packagesDao = DatabaseManager.getInstance().getPackagesDao();
            Packages packages = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(material.id)).unique();
            if (packages != null) {
                packagesDao.delete(packages);
            }
        }
    }

    private HasMaterial hasMaterial(String type) {
        HasMaterial material = new HasMaterial();
        List<Packages> packages = DatabaseManager.getInstance().getPackagesDao().queryBuilder().where(PackagesDao.Properties.Type.eq(type)).list();
        Packages pk = null;
        if (packages != null && packages.size() > 0) {
            pk = packages.get(0);
        }
        if (pk != null) {
            material.isHas = true;
            material.id = pk.id;
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
        // 拥有Packages中的id
        public Long id;
    }
}
