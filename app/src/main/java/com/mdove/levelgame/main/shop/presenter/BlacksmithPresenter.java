package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.shop.manager.BlacksmithManager;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.contract.BlacksmithContract;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/10/28.
 */

public class BlacksmithPresenter implements BlacksmithContract.IBlacksmithPresenter {
    private BlacksmithContract.IBlacksmithView view;

    public BlacksmithPresenter() {
    }

    @Override
    public void subscribe(BlacksmithContract.IBlacksmithView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        view.showLoadingDialog(view.getString(R.string.string_init_data_loading));

        List<BlacksmithModelVM> data = new ArrayList<>();
        // 因为铁匠铺是死的，所以BelongMonsterId直接匹配字符串
        List<Weapons> weapons = DatabaseManager.getInstance().getWeaponsDao().queryBuilder()
                .where(WeaponsDao.Properties.BelongMonsterId.eq("1002,")).list();
        if (weapons != null && weapons.size() > 0) {
            for (Weapons weapon : weapons) {
                data.add(new BlacksmithModelVM(weapon));
            }
        }
        List<Armors> armors = DatabaseManager.getInstance().getArmorsDao().queryBuilder()
                .where(ArmorsDao.Properties.BelongMonsterId.eq("1002,")).list();
        if (armors != null && armors.size() > 0) {
            for (Armors armor : armors) {
                data.add(new BlacksmithModelVM(armor));
            }
        }
        view.showData(data);
        view.dismissLoadingDialog();
    }

    @Override
    public void onItemBtnClick(String type, Long id) {
        BlacksmithManager.Companion.getInstance().goodsUpdate(type).subscribe(blacksmithResp -> MyDialog.showMyDialog(view.getContext(), blacksmithResp.getTitle(), blacksmithResp.getContent(), true));
    }
}
