package com.mdove.levelgame.main.shop.manager;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
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
    private static final String TO_COPPER_TYPE = "A10";
    private static final String TO_STRENGTHEN_TYPE = "A11";

    private static class SingletonHolder {
        static final BlacksmithManager INSTANCE = new BlacksmithManager();
    }

    public static BlacksmithManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<BlacksmithResp> goodsUpdate(String type, Long id) {
        Object oj = AllGoodsToDBIdUtils.getInstance().getObjectFromType(type);
        if (oj != null && oj instanceof Weapons) {
            return attackUpdate(id);
        } else {
            return armorUpdate(id);
        }
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
                        resetPkSelectStatus(hasMaterials);
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
                        // 重置pk中材料的选择状态
                        resetPkSelectStatus(hasMaterials);
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

    private void resetPkSelectStatus(List<HasMaterial> hasMaterials) {
        for (HasMaterial hasMaterial : hasMaterials) {
            Packages pk = DatabaseManager.getInstance().getPackagesDao().queryBuilder().where(PackagesDao.Properties.Id.eq(hasMaterial.id)).unique();
            if (pk != null) {
                pk.isSelect = 1;
                DatabaseManager.getInstance().getPackagesDao().update(pk);
            }
        }
    }

    private void newPk(Weapons weapon) {
        Packages packages = new Packages();
        packages.isEquip = 1;
        packages.isSelect = 1;
        packages.strengthenLevel = 0;
        if (TextUtils.equals(weapon.type, TO_COPPER_TYPE)) {
            packages.type = "E2";
        } else if (TextUtils.equals(weapon.type, TO_STRENGTHEN_TYPE)) {
            packages.type = "E3";
        } else {
            packages.type = weapon.type;
        }
        DatabaseManager.getInstance().getPackagesDao().insert(packages);
    }

    private void newPk(Armors armors) {
        Packages packages = new Packages();
        packages.isEquip = 1;
        packages.strengthenLevel = 0;
        packages.type = armors.type;
        DatabaseManager.getInstance().getPackagesDao().insert(packages);
    }

    private void deleteMaterial(List<HasMaterial> hasMaterials) {
        for (HasMaterial material : hasMaterials) {
            PackagesDao packagesDao = DatabaseManager.getInstance().getPackagesDao();
            Packages packages = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(material.id)).unique();
            if (packages != null) {
                // 如果删除的装备是已经装备的，则先脱掉装备
                if (packages.isEquip == 0) {
                    Object oj = AllGoodsToDBIdUtils.getInstance().getObjectFromType(packages.type);
                    if (oj != null && oj instanceof Weapons) {
                        HeroAttributesManager.getInstance().takeOffAttack((Weapons) oj);
                    } else if (oj != null && oj instanceof Armors) {
                        HeroAttributesManager.getInstance().takeOffArmor((Armors) oj);
                    }
                }
                packagesDao.delete(packages);
            }
        }
    }

    private HasMaterial hasMaterial(String type) {
        HasMaterial material = new HasMaterial();
        List<Packages> packages = DatabaseManager.getInstance().getPackagesDao().queryBuilder()
                .where(PackagesDao.Properties.Type.eq(type), PackagesDao.Properties.IsSelect.eq(1)).list();
        Packages pk = null;
        if (packages != null && packages.size() > 0) {
            pk = packages.get(0);
        }
        if (pk != null) {
            material.isHas = true;
            material.id = pk.id;
            pk.isSelect = 0;
            DatabaseManager.getInstance().getPackagesDao().update(pk);
        } else {
            material.isHas = false;
        }
        return material;
    }


    public Observable<BlacksmithResp> armorUpdate(Long id) {
        final BlacksmithResp blacksmithResp = new BlacksmithResp();
        Armors armor = DatabaseManager.getInstance().getArmorsDao().queryBuilder().where(ArmorsDao.Properties.Id.eq(id)).unique();
        if (armor != null) {
            // 可升级
            if (armor.isCanUpdate == 0) {
                // 判断材料是否完全
                String formula = armor.updateFormula;
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
                        newPk(armor);
                        blacksmithResp.isSuc = true;
                        blacksmithResp.title = "升级成功";
                        blacksmithResp.content = armor.name + "升级成功。";
                    } else {
                        resetPkSelectStatus(hasMaterials);
                        blacksmithResp.isSuc = false;
                        blacksmithResp.title = "升级失败";
                        blacksmithResp.content = "缺少材料：请确认所需材料都持有。";
                    }
                } else {
                    blacksmithResp.isSuc = false;
                    blacksmithResp.title = "未知错误";
                    blacksmithResp.content = "获取升级材料异常：null。";
                }
            } else if (armor.isCanMixture == 0) {
                // 可合成
                // 判断材料是否完全
                String formula = armor.mixtureFormula;
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
                        newPk(armor);
                        blacksmithResp.isSuc = true;
                        blacksmithResp.title = "合成成功";
                        blacksmithResp.content = armor.name + "合成成功。";
                    } else {
                        resetPkSelectStatus(hasMaterials);
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
