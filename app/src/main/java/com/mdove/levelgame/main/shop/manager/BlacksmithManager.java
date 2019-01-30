package com.mdove.levelgame.main.shop.manager;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.model.BaseBlacksmithModel;
import com.mdove.levelgame.main.shop.model.StrengthenNeedModel;
import com.mdove.levelgame.main.shop.model.StrengthenResp;
import com.mdove.levelgame.model.IAttrsModel;
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
    private static final String TO_COPPER_TYPE = "A10";
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
        return updateAndMixture(AllGoodsToDBIdUtils.getInstance().getBlacksmithModelFromType(type));

    }

    private Observable<BlacksmithResp> updateAndMixture(BaseBlacksmithModel weapon) {
        final BlacksmithResp blacksmithResp = new BlacksmithResp();
        if (weapon != null) {
            // 可升级
            if (weapon.canUpdate == 0) {
                // 判断材料是否完全
                String formula = weapon.updateFormulas;
                List<NeedMaterial> need = JsonUtil.decode(formula, new TypeToken<List<NeedMaterial>>() {
                }.getType());
                if (need != null && need.size() > 0) {
                    List<HasMaterial> hasMaterials = new ArrayList<>();
                    for (NeedMaterial type : need) {
                        hasMaterials.add(hasMaterial(type.type, type.count));
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
                        blacksmithResp.title = App.getAppContext().getString(R.string.string_blacksmith_update_suc);
                        blacksmithResp.content = weapon.myName + "升级成功。";
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
            } else if (weapon.canMixture == 0) {
                // 可合成
                // 判断材料是否完全
                String formula = weapon.mixtureFormulas;
                List<NeedMaterial> need = JsonUtil.decode(formula, new TypeToken<List<NeedMaterial>>() {
                }.getType());
                if (need != null && need.size() > 0) {
                    List<HasMaterial> hasMaterials = new ArrayList<>();
                    for (NeedMaterial type : need) {
                        hasMaterials.add(hasMaterial(type.type, type.count));
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
                        blacksmithResp.content = weapon.myName + "合成成功。";
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
            } else {
                blacksmithResp.isSuc = false;
                blacksmithResp.title = "未知错误";
                blacksmithResp.content = "未找到对应升级的装备...";
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

    private void newPk(BaseBlacksmithModel model) {
        Packages packages = new Packages();
        packages.isEquip = 1;
        packages.isSelect = 1;
        packages.strengthenLevel = 0;
        packages.type = model.myType;
        packages.count = 1;
        if (TextUtils.equals(model.myType, TO_COPPER_TYPE)) {
            packages.type = "E2";
        } else if (TextUtils.equals(model.myType, TO_STRENGTHEN_TYPE)) {
            packages.type = "E3";
        } else {
            packages.type = model.myType;
        }
        DatabaseManager.getInstance().getPackagesDao().insert(packages);
    }

    private void deleteMaterial(List<HasMaterial> hasMaterials) {
        for (HasMaterial material : hasMaterials) {
            PackagesDao packagesDao = DatabaseManager.getInstance().getPackagesDao();
            Packages packages = material.pk;
            if (packages != null) {
                // 如果删除的装备是已经装备的，则先脱掉装备
                if (packages.isEquip == 0) {
                    IAttrsModel oj = AllGoodsToDBIdUtils.getInstance().getAttrsModelFromType(packages.type);
                    if (oj != null) {
                        HeroAttributesManager.getInstance().takeOff(packages.strengthenLevel, (IAttrsModel) oj,
                                packages.getRandomAttr());
                    }
                }
                if (packages.count == material.needCount) {
                    packagesDao.delete(packages);
                } else if (packages.count > material.needCount) {
                    packages.count = packages.count - material.needCount;
                    packagesDao.update(packages);
                }
            }
        }
    }

    public StrengthenResp strengthen(long pkId, String type) {
        StrengthenResp resp = new StrengthenResp();
        resp.status = STRENGTHEN_STATUS_ERROR;
        BaseBlacksmithModel model = AllGoodsToDBIdUtils.getInstance().getBlacksmithModelFromType(type);
        PackagesDao packagesDao = DatabaseManager.getInstance().getPackagesDao();
        Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(pkId)).unique();
        if (model != null && pk != null) {
            // 强化装备为：武器
            if (model.canStrengthen == 0) {
                List<StrengthenNeedModel> needModels = JsonUtil.decode(model.strengthenFormulas, new TypeToken<List<StrengthenNeedModel>>() {
                }.getType());
                int nextLevel = pk.strengthenLevel + 1;
                // 可强化
                if (nextLevel <= needModels.size()) {
                    // 数组越界问题
                    StrengthenNeedModel needModel = needModels.get(nextLevel - 1);
                    HasMaterial hasMaterial = hasMaterial(needModel.type, needModel.count);
                    if (!hasMaterial.isHas) {
                        resp.status = STRENGTHEN_STATUS_NO_MATERIAL;
                        return resp;
                    } else {
                        // 材料足够，先删强化石
                        Packages _pk = hasMaterial.pk;
                        if (_pk.count > needModel.count) {
                            _pk.count = _pk.count - needModel.count;
                            packagesDao.update(_pk);
                        } else if (_pk.count == needModel.count) {
                            packagesDao.delete(_pk);
                        }
                        float probability = needModel.probability * 100;
                        int random = new Random(System.currentTimeMillis()).nextInt(100);
                        // 强化成功
                        if (random <= probability) {
                            resp.status = STRENGTHEN_STATUS_SUC;
                            pk.strengthenLevel = nextLevel;
                            resp.level = pk.strengthenLevel;
                            resp.strengthId = hasMaterial.pk.id;
                            packagesDao.update(pk);
                            return resp;
                        } else {
                            resp.strengthId = hasMaterial.pk.id;
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
        return resp;
    }

    public HasMaterial hasMaterial(String type, int needCount) {
        HasMaterial material = new HasMaterial();
        List<Packages> packages = null;
        // 因为龙渊有俩个版本A15和A18
        if (TextUtils.equals(type, "A15") || TextUtils.equals(type, "A18")) {
            packages = DatabaseManager.getInstance().getPackagesDao().queryBuilder()
                    .where(PackagesDao.Properties.Type.eq("A15"), PackagesDao.Properties.IsSelect.eq(1))
                    .whereOr(PackagesDao.Properties.Type.eq("A18"), PackagesDao.Properties.IsSelect.eq(1)).list();
        } else {
            packages = DatabaseManager.getInstance().getPackagesDao().queryBuilder()
                    .where(PackagesDao.Properties.Type.eq(type), PackagesDao.Properties.IsSelect.eq(1)).list();
        }
        Packages pk = null;
        if (packages != null && packages.size() > 0) {
            pk = packages.get(0);
        }
        if (pk != null && pk.count >= needCount) {
            material.isHas = true;
            material.pk = pk;
            material.needCount = needCount;
            pk.isSelect = 0;
            DatabaseManager.getInstance().getPackagesDao().update(pk);
        } else {
            material.isHas = false;
        }
        return material;
    }

    private class NeedMaterial {
        public String type;
        public int count;
    }

    public class BlacksmithResp {
        public boolean isSuc;
        public String title;
        public String content;
    }

    public class HasMaterial {
        public boolean isHas;
        // 拥有Packages
        public Packages pk;
        public int needCount;
    }
}
