package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.shop.manager.BlacksmithManager;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.contract.BlacksmithArmorContract;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/10/30.
 */

public class BlacksmithArmorPresenter implements BlacksmithArmorContract.IBlacksmithArmorPresenter {
    private BlacksmithArmorContract.IBlacksmithArmorView view;

    public BlacksmithArmorPresenter() {
    }

    @Override
    public void subscribe(BlacksmithArmorContract.IBlacksmithArmorView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        List<BlacksmithModelVM> data = new ArrayList<>();
        // 因为铁匠铺是死的，所以BelongMonsterId直接匹配字符串
        List<Armors> armors = DatabaseManager.getInstance().getArmorsDao().queryBuilder()
                .where(ArmorsDao.Properties.BelongMonsterId.eq("1002,"))
                .orderAsc(ArmorsDao.Properties.Position).list();
        if (armors != null && armors.size() > 0) {
            for (Armors armor : armors) {
                data.add(new BlacksmithModelVM(armor));
            }
        }
        view.showData(data);
    }

    @Override
    public void onItemBtnClick(String type, Long id) {
        BlacksmithManager.getInstance().goodsUpdate(type).subscribe(new Consumer<BlacksmithManager.BlacksmithResp>() {
            @Override
            public void accept(BlacksmithManager.BlacksmithResp blacksmithResp) throws Exception {
                MyDialog.showMyDialog(view.getContext(), blacksmithResp.title, blacksmithResp.content, true);
            }
        });
    }
}
