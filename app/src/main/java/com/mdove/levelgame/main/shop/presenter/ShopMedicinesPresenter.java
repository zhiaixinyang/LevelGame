package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.manager.HeroBuyManager;
import com.mdove.levelgame.main.hero.model.BuyAttackResp;
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp;
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/10/30.
 */
public class ShopMedicinesPresenter implements ShopMedicinesContract.IShopMedicinesPresenter {
    private ShopMedicinesContract.IShopMedicinesView view;
    private List<MedicinesModelVM> data;

    @Override
    public void subscribe(ShopMedicinesContract.IShopMedicinesView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        List<Medicines> medicines = InitDataFileUtils.getInitMedicines();
        for (Medicines model : medicines) {
            data.add(new MedicinesModelVM(model));
        }

        view.showData(data);
    }

    @Override
    public void onItemBtnClick(final Long id) {
        Observable.create(new ObservableOnSubscribe<BuyMedicinesResp>() {
            @Override
            public void subscribe(ObservableEmitter<BuyMedicinesResp> e) throws Exception {
                BuyMedicinesResp resp = HeroBuyManager.getInstance().buyMedicines(id);
                e.onNext(resp);
            }
        }).subscribe(new Consumer<BuyMedicinesResp>() {
            @Override
            public void accept(BuyMedicinesResp buyMedicinesResp) throws Exception {
                switch (buyMedicinesResp.buyStatus) {
                    case HeroBuyManager.BUY_MEDICINES_STATUS_ERROR: {
                        ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
                        break;
                    }
                    case HeroBuyManager.BUY_MEDICINES_STATUS_FAIL: {
                        MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_buy_title_error),
                                view.getContext().getString(R.string.string_buy_content_error), true);
                        break;
                    }
                    case HeroBuyManager.BUY_MEDICINES_STATUS_SUC: {
                        String content;
                        if (buyMedicinesResp.lifeUp > 0) {
                            content = String.format(view.getContext().getString(R.string.string_buy_medicines_suc_and_up), buyMedicinesResp.life,
                                    buyMedicinesResp.price, buyMedicinesResp.lifeUp, buyMedicinesResp.name);
                        } else {
                            content = String.format(view.getContext().getString(R.string.string_buy_medicines_suc),
                                    buyMedicinesResp.life, buyMedicinesResp.price, buyMedicinesResp.name);
                        }
                        MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_buy_title_suc),
                                content, true);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }
}
