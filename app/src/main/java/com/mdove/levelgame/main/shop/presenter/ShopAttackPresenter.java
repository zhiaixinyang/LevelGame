package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroBuyManager;
import com.mdove.levelgame.main.hero.model.BuyAttackResp;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.contract.ShopAttackContract;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.MyDialog;

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
        view.showLoadingDialog(view.getString(R.string.string_init_data_loading));
        // 因为武器是死的，所以BelongMonsterId直接匹配字符串
        List<Weapons> weapons = DatabaseManager.getInstance().getWeaponsDao().queryBuilder()
                .where(WeaponsDao.Properties.BelongMonsterId.eq("1000,")).list();
        List<ShopAttackModelVM> data = new ArrayList<>();
        for (Weapons model : weapons) {
            data.add(new ShopAttackModelVM(model));
        }
        view.showData(data);
        view.dismissLoadingDialog();
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
                MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_buy_title_error),
                        view.getContext().getString(R.string.string_buy_content_error), true);
                break;
            }
            case HeroBuyManager.BUY_ATTACK_STATUS_SUC: {
                MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_buy_title_suc),
                        String.format(view.getContext().getString(R.string.string_buy_content_suc), resp.name, resp.price), true);
                break;
            }
            default:
                break;
        }
    }
}
