package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.manager.HeroBuyManager;
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp;
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;
import com.mdove.levelgame.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/10/21.
 */

public class MedicinesShopPresenter implements MedicinesShopContract.IMedicinesShopPresenter {
    private MedicinesShopContract.IMedicinesShopView view;
    private List<MedicinesModelVM> data;

    public MedicinesShopPresenter() {
    }

    @Override
    public void subscribe(MedicinesShopContract.IMedicinesShopView view) {
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
                        ToastHelper.shortToast(view.getContext().getString(R.string.string_buy_medicines_fail));
                        break;
                    }
                    case HeroBuyManager.BUY_MEDICINES_STATUS_SUC: {
                        if (buyMedicinesResp.lifeUp > 0) {
                            ToastHelper.shortToast(String.format(view.getContext().getString(R.string.string_buy_medicines_suc_and_up), buyMedicinesResp.life,
                                    buyMedicinesResp.price, buyMedicinesResp.lifeUp));
                        } else {
                            ToastHelper.shortToast(String.format(view.getContext().getString(R.string.string_buy_medicines_suc), buyMedicinesResp.life, buyMedicinesResp.price));
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }
}
