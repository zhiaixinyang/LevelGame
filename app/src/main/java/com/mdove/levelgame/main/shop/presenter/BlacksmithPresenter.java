package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.shop.manager.BlacksmithManager;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
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
        List<BlacksmithModelVM> data = new ArrayList<>();
        List<Weapons> weapons = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.IsSpecial.eq(0)).list();
        if (weapons != null && weapons.size() > 0) {
            for (Weapons weapon : weapons) {
                data.add(new BlacksmithModelVM(weapon));
            }
        }
        view.showData(data);
    }

    @Override
    public void onItemBtnClick(Long id) {
        BlacksmithManager.getInstance().attackUpdate(id).subscribe(new Consumer<BlacksmithManager.BlacksmithResp>() {
            @Override
            public void accept(BlacksmithManager.BlacksmithResp blacksmithResp) throws Exception {
                MyDialog.showMyDialog(view.getContext(),blacksmithResp.title, blacksmithResp.content, true);
            }
        });
    }
}
