package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.manager.HeroBuyManager;
import com.mdove.levelgame.main.hero.model.BuyAttackResp;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class ShopAttackPresenter implements ShopAttackContract.IShopAttackPresenter {
    private ShopAttackContract.IShopAttackView view;

    @Override
    public void subscribe(ShopAttackContract.IShopAttackView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        List<Weapons> weapons = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.IsSpecial.eq(1)).list();
        List<ShopAttackModelVM> data = new ArrayList<>();
        for (Weapons model : weapons) {
            data.add(new ShopAttackModelVM(model));
        }
        view.showData(data);
    }

    @Override
    public void onItemBtnClick(Long id) {
        BuyAttackResp resp = HeroBuyManager.getInstance().buyAttack(id);
        switch (resp.buyStatus) {
            case HeroBuyManager.BUY_ATTACK_STATUS_ERROR: {
                ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
                break;
            }
            case HeroBuyManager.BUY_ATTACK_STATUS_FAIL: {
                ToastHelper.shortToast(view.getContext().getString(R.string.string_buy_medicines_fail));
                break;
            }
            case HeroBuyManager.BUY_ATTACK_STATUS_SUC: {
                ToastHelper.shortToast(String.format(view.getContext().getString(R.string.string_buy_attack_suc), resp.price));
                break;
            }
            default:
                break;
        }
    }
}
