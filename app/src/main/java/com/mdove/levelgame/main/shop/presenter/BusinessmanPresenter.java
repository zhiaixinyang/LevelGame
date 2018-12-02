package com.mdove.levelgame.main.shop.presenter;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.Skill;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroBuyManager;
import com.mdove.levelgame.main.hero.model.BaseBuy;
import com.mdove.levelgame.main.shop.manager.BlacksmithManager;
import com.mdove.levelgame.main.shop.model.mv.SellGoodsModelVM;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.utils.JsonUtil;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @author MDove on 2018/10/29
 */
public class BusinessmanPresenter implements BusinessmanContract.IBusinessmanPresenter {
    private BusinessmanContract.IBusinessmanView view;

    @Override
    public void initData(Long monstersId) {
        if (monstersId == -1) {
            return;
        }
        List<SellGoodsModelVM> data = new ArrayList<>();
        Monsters monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder()
                .where(MonstersDao.Properties.Id.eq(monstersId), MonstersDao.Properties.IsBusinessman.eq(0)).unique();
        if (monsters != null && !TextUtils.isEmpty(monsters.sellGoodsJson)) {
            view.showTitle(monsters.name);
            List<SellGoodsTemp> sellGoodsTemps = JsonUtil.decode(monsters.sellGoodsJson, new TypeToken<List<SellGoodsTemp>>() {
            }.getType());
            for (SellGoodsTemp temp : sellGoodsTemps) {
                Object oj = AllGoodsToDBIdUtils.getInstance().getBlacksmithModelFromType(temp.type);
                if (oj != null) {
                    if (oj instanceof Weapons) {
                        Weapons weapons = (Weapons) oj;
                        int status = 0;
                        if (weapons.isCanMixture == 0) {
                            status = 1;
                        } else if (weapons.isCanUpdate == 0) {
                            status = 2;
                        }
                        data.add(new SellGoodsModelVM(temp.price, weapons.name, weapons.tips, weapons.type, status));
                    } else if (oj instanceof Armors) {
                        Armors armors = (Armors) oj;
                        int status = 0;
                        if (armors.isCanMixture == 0) {
                            status = 1;
                        } else if (armors.isCanUpdate == 0) {
                            status = 2;
                        }
                        data.add(new SellGoodsModelVM(temp.price, armors.name, armors.tips, armors.type, status));
                    } else if (oj instanceof Material) {
                        Material material = (Material) oj;
                        int status = 0;
                        if (material.isCanMixture == 0) {
                            status = 1;
                        }
                        data.add(new SellGoodsModelVM(temp.price, material.name, material.tips, material.type, status));
                    } else if (oj instanceof Accessories) {
                        Accessories accessories = (Accessories) oj;
                        int status = 0;
                        if (accessories.isCanMixture == 0) {
                            status = 1;
                        } else if (accessories.isCanUpdate == 0) {
                            status = 2;
                        }
                        data.add(new SellGoodsModelVM(temp.price, accessories.name, accessories.tips, accessories.type, status));
                    } else if (oj instanceof Skill) {
                        Skill skill = (Skill) oj;
                        int status = 3;
                        data.add(new SellGoodsModelVM(temp.price, skill.name, skill.tips, skill.type, status));
                    }
                }
            }
            view.showData(data);
        }
    }

    @Override
    public void onItemBtnClick(int status, String type, long price) {
        switch (status) {
            // 3表示技能
            case 3:
            case 0: {
                HeroBuyManager.getInstance().buy(type, price).subscribe(new Consumer<BaseBuy>() {
                    @Override
                    public void accept(BaseBuy baseBuy) throws Exception {
                        switch (baseBuy.buyStatus) {
                            case HeroBuyManager.BUY_BASE_STATUS_SUC: {
                                MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_buy_title_suc),
                                        String.format(view.getContext().getString(R.string.string_buy_content_suc), baseBuy.name, baseBuy.price), true);

                                break;
                            }
                            case HeroBuyManager.BUY_BASE_STATUS_FAIL: {
                                MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_buy_title_error),
                                        view.getContext().getString(R.string.string_buy_content_error), true);
                                break;
                            }
                            case HeroBuyManager.BUY_BASE_STATUS_ERROR: {
                                ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
                                break;
                            }
                            default:
                                break;
                        }
                    }
                });
                break;
            }
            // 装备对应升级/合成
            case 1:
            case 2: {
                BlacksmithManager.getInstance().goodsUpdate(type).subscribe(new Consumer<BlacksmithManager.BlacksmithResp>() {
                    @Override
                    public void accept(BlacksmithManager.BlacksmithResp blacksmithResp) throws Exception {
                        MyDialog.showMyDialog(view.getContext(), blacksmithResp.title, blacksmithResp.content, true);
                    }
                });
            }
        }

    }

    @Override
    public void subscribe(BusinessmanContract.IBusinessmanView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
        view = null;
    }

    public class SellGoodsTemp {
        public String type;
        public long price;
    }
}
