package com.mdove.levelgame.main.shop.manager;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.shop.model.StrengthenNeedModel;
import com.mdove.levelgame.main.shop.model.StrengthenResp;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by MDove on 2018/10/28.
 */

public class BlacksmithManager {
    private static final String TO_COPPER_TYPE = "A9";
    private static final String TO_STRENGTHEN_TYPE = "A11";

    // 强化已满级
    public static final int STRENGTHEN_STATUS_MAX = 1;
    // 强化成功
    public static final int STRENGTHEN_STATUS_SUC = 2;
    public static final int STRENGTHEN_STATUS_FAIL = 3;
    // 材料不足
    public static final int STRENGTHEN_STATUS_NO_MATERIAL = 5;
    public static final int STRENGTHEN_STATUS_ERROR = 4;

    private static class SingletonHolder {
        static final BlacksmithManager INSTANCE = new BlacksmithManager();
    }

    public static BlacksmithManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<BlacksmithResp> goodsUpdate(String type) {
        Object oj = AllGoodsToDBIdUtils.getInstance().getObjectFromType(type);
        if (oj != null && oj instanceof Weapons) {
            return attackUpdate((Weapons) oj);
        } else if (oj != null && oj instanceof Armors) {
            return armorUpdate((Armors) oj);
        } else {
            return armorUpdate((Accessories) oj);
        }
    }

    private Observable<BlacksmithResp> attackUpdate(Weapons weapon) {
        final BlacksmithResp blacksmithResp = new BlacksmithResp();
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
                        resetPkSelectStatus();
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
                        resetPkSelectStatus();
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

    private Observable<BlacksmithResp> armorUpdate(Armors armor) {
        final BlacksmithResp blacksmithResp = new BlacksmithResp();
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
                        resetPkSelectStatus();
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
                        resetPkSelectStatus();
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

    private Observable<BlacksmithResp> armorUpdate(Accessories accessories) {
        final BlacksmithResp blacksmithResp = new BlacksmithResp();
        if (accessories != null) {
            // 可升级
            if (accessories.isCanUpdate == 0) {
                // 判断材料是否完全
                String formula = accessories.updateFormula;
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
                        newPk(accessories);
                        blacksmithResp.isSuc = true;
                        blacksmithResp.title = "升级成功";
                        blacksmithResp.content = accessories.name + "升级成功。";
                    } else {
                        resetPkSelectStatus();
                        blacksmithResp.isSuc = false;
                        blacksmithResp.title = "升级失败";
                        blacksmithResp.content = "缺少材料：请确认所需材料都持有。";
                    }
                } else {
                    blacksmithResp.isSuc = false;
                    blacksmithResp.title = "未知错误";
                    blacksmithResp.content = "获取升级材料异常：null。";
                }
            } else if (accessories.isCanMixture == 0) {
                // 可合成
                // 判断材料是否完全
                String formula = accessories.mixtureFormula;
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
                        newPk(accessories);
                        blacksmithResp.isSuc = true;
                        blacksmithResp.title = "合成成功";
                        blacksmithResp.content = accessories.name + "合成成功。";
                    } else {
                        resetPkSelectStatus();
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


    private void resetPkSelectStatus() {
        List<Packages> packages = DatabaseManager.getInstance().getPackagesDao().queryBuilder().list();
        if (packages != null && packages.size() > 0) {
            for (Packages pk : packages) {
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
        // 这俩个是[服务：换]铜矿石...
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

    private void newPk(Accessories accessories) {
        Packages packages = new Packages();
        packages.isEquip = 1;
        packages.strengthenLevel = 0;
        packages.type = accessories.type;
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
                        HeroAttributesManager.getInstance().takeOffAttack(packages.strengthenLevel, (Weapons) oj);
                    } else if (oj != null && oj instanceof Armors) {
                        HeroAttributesManager.getInstance().takeOffArmor(packages.strengthenLevel, (Armors) oj);
                    }
                }
                packagesDao.delete(packages);
            }
        }
    }

    public StrengthenResp strengthenGoods(long pkId, String type) {
        StrengthenResp resp = new StrengthenResp();
        resp.status = STRENGTHEN_STATUS_ERROR;
        Object ob = AllGoodsToDBIdUtils.getInstance().getObjectFromType(type);
        PackagesDao packagesDao = DatabaseManager.getInstance().getPackagesDao();
        Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(pkId)).unique();
        if (ob != null && pk != null) {
            // 强化装备为：武器
            if (ob instanceof Weapons) {
                Weapons model = (Weapons) ob;
                if (model.isCanStrengthen == 0) {
                    List<StrengthenNeedModel> needModels = JsonUtil.decode(model.strengthenFormula, new TypeToken<List<StrengthenNeedModel>>() {
                    }.getType());
                    int nextLevel = pk.strengthenLevel + 1;
                    // 可强化
                    if (nextLevel <= needModels.size()) {
                        // 数组越界问题
                        StrengthenNeedModel needModel = needModels.get(nextLevel - 1);
                        HasMaterial hasMaterial = hasMaterial(needModel.type);
                        if (!hasMaterial.isHas) {
                            resp.status = STRENGTHEN_STATUS_NO_MATERIAL;
                            return resp;
                        } else {
                            // 材料足够，先删强化石
                            packagesDao.delete(packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(hasMaterial.id)).unique());
                            float probability = needModel.probability * 100;
                            int random = new Random(System.currentTimeMillis()).nextInt(100);
                            // 强化成功
                            if (random <= probability) {
                                resp.status = STRENGTHEN_STATUS_SUC;
                                pk.strengthenLevel = nextLevel;
                                resp.level = pk.strengthenLevel;
                                resp.strengthId=hasMaterial.id;
                                packagesDao.update(pk);
                                return resp;
                            } else {
                                resp.status = STRENGTHEN_STATUS_FAIL;
                                return resp;
                            }
                        }
                    } else {
                        resp.status = STRENGTHEN_STATUS_MAX;
                        return resp;
                    }
                }
            } else if (ob instanceof Armors) {// 强化装备为：防具
                Armors model = (Armors) ob;
                if (model.isCanStrengthen == 0) {
                    List<StrengthenNeedModel> needModels = JsonUtil.decode(model.strengthenFormula, new TypeToken<List<StrengthenNeedModel>>() {
                    }.getType());
                    int nextLevel = pk.strengthenLevel + 1;
                    // 可强化
                    if (nextLevel <= needModels.size()) {
                        // 数组越界问题
                        StrengthenNeedModel needModel = needModels.get(nextLevel - 1);
                        HasMaterial hasMaterial = hasMaterial(needModel.type);
                        if (!hasMaterial.isHas) {
                            resp.status = STRENGTHEN_STATUS_NO_MATERIAL;
                            return resp;
                        } else {
                            // 材料足够，先删强化石
                            packagesDao.delete(packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(hasMaterial.id)).unique());
                            float probability = needModel.probability * 100;
                            int random = new Random(System.currentTimeMillis()).nextInt(100);
                            // 强化成功
                            if (random <= probability) {
                                resp.status = STRENGTHEN_STATUS_SUC;
                                pk.strengthenLevel = nextLevel;
                                resp.level = pk.strengthenLevel;
                                packagesDao.update(pk);
                                return resp;
                            } else {
                                resp.status = STRENGTHEN_STATUS_FAIL;
                                return resp;
                            }
                        }
                    } else {
                        resp.status = STRENGTHEN_STATUS_MAX;
                        return resp;
                    }
                }
            }
        }
        return resp;
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
