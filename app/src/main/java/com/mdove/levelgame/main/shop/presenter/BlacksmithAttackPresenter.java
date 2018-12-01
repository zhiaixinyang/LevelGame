package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.shop.manager.BlacksmithManager;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/10/30.
 */

public class BlacksmithAttackPresenter implements BlacksmithAttackContract.IBlacksmithAttackPresenter {
    private BlacksmithAttackContract.IBlacksmithAttackView view;

    public BlacksmithAttackPresenter() {
    }

    @Override
    public void subscribe(BlacksmithAttackContract.IBlacksmithAttackView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        List<BlacksmithModelVM> data = new ArrayList<>();
        // 因为铁匠铺是死的，所以BelongMonsterId直接匹配字符串
        List<Weapons> weapons = DatabaseManager.getInstance().getWeaponsDao().queryBuilder()
                .where(WeaponsDao.Properties.BelongMonsterId.eq("1002,"))
                .orderAsc(WeaponsDao.Properties.Position).list();
        if (weapons != null && weapons.size() > 0) {
            for (Weapons weapon : weapons) {
                data.add(new BlacksmithModelVM(weapon));
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
