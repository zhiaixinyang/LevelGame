package com.mdove.levelgame.main.shop.presenter;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroBuyManager;
import com.mdove.levelgame.main.hero.model.BaseBuy;
import com.mdove.levelgame.main.shop.model.mv.SellGoodsModelVM;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.utils.JsonUtil;
import com.mdove.levelgame.utils.ToastHelper;

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
            List<SellGoodsTemp> sellGoodsTemps = JsonUtil.decode(monsters.sellGoodsJson, new TypeToken<List<SellGoodsTemp>>() {
            }.getType());
            for (SellGoodsTemp temp : sellGoodsTemps) {
                Object oj = AllGoodsToDBIdUtils.getInstance().getObjectFromType(temp.type);
                if (oj != null) {
                    if (oj instanceof Weapons) {
                        Weapons weapons = (Weapons) oj;
                        data.add(new SellGoodsModelVM(temp.price, weapons.name, weapons.tips, weapons.type));
                    } else if (oj instanceof Armors) {
                        Armors armors = (Armors) oj;
                        data.add(new SellGoodsModelVM(temp.price, armors.name, armors.tips, armors.type));
                    } else if (oj instanceof Material) {
                        Material material = (Material) oj;
                        data.add(new SellGoodsModelVM(temp.price, material.name, material.tips, material.type));
                    }
                }
            }
        }
        view.showData(data);
    }

    @Override
    public void onItemBtnClick(String type, long price) {
        HeroBuyManager.getInstance().buy(type, price).subscribe(new Consumer<BaseBuy>() {
            @Override
            public void accept(BaseBuy baseBuy) throws Exception {
                switch (baseBuy.buyStatus) {
                    case HeroBuyManager.BUY_BASE_STATUS_SUC: {
                        ToastHelper.shortToast(String.format(view.getContext().getString(R.string.string_buy_base_suc), baseBuy.price));
                        break;
                    }
                    case HeroBuyManager.BUY_BASE_STATUS_FAIL: {
                        ToastHelper.shortToast(view.getContext().getString(R.string.string_buy_base_fail));
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
