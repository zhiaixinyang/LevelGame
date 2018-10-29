package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroBuyManager;
import com.mdove.levelgame.main.hero.model.BuyArmorResp;
import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;
import com.mdove.levelgame.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class ShopArmorPresenter implements ShopArmorContract.IShopArmorPresenter {
    private ShopArmorContract.IShopArmorView view;

    @Override
    public void subscribe(ShopArmorContract.IShopArmorView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        // 因为防具是死的，所以BelongMonsterId直接匹配字符串
        List<Armors> list = DatabaseManager.getInstance().getArmorsDao().queryBuilder()
                .where(ArmorsDao.Properties.BelongMonsterId.eq("1001,")).list();
        List<ShopArmorModelVM> data = new ArrayList<>();
        for (Armors model : list) {
            data.add(new ShopArmorModelVM(model));
        }
        view.showData(data);
    }

    @Override
    public void onItemBtnClick(Long id) {
        BuyArmorResp resp = HeroBuyManager.getInstance().buyArmor(id);
        switch (resp.buyStatus) {
            case HeroBuyManager.BUY_ARMOR_STATUS_ERROR: {
                ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
                break;
            }
            case HeroBuyManager.BUY_ARMOR_STATUS_FAIL: {
                ToastHelper.shortToast(view.getContext().getString(R.string.string_buy_medicines_fail));
                break;
            }
            case HeroBuyManager.BUY_ARMOR_STATUS_SUC: {
                ToastHelper.shortToast(String.format(view.getContext().getString(R.string.string_buy_armor_suc),  resp.price));
                break;
            }
            default:
                break;
        }
    }
}
