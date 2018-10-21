package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.HeroAttributesManager;
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp;
import com.mdove.levelgame.main.shop.model.MedicinesModelVM;
import com.mdove.levelgame.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
                BuyMedicinesResp resp = HeroAttributesManager.getInstance().buyMedicines(id);
                e.onNext(resp);
            }
        }).subscribe(new Consumer<BuyMedicinesResp>() {
            @Override
            public void accept(BuyMedicinesResp buyMedicinesResp) throws Exception {
                switch (buyMedicinesResp.buyStatus) {
                    case HeroAttributesManager.BUY_MEDICINES_STATUS_ERROR: {
                        ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
                        break;
                    }
                    case HeroAttributesManager.BUY_MEDICINES_STATUS_FAIL: {
                        ToastHelper.shortToast(view.getContext().getString(R.string.string_buy_medicines_fail));
                        break;
                    }
                    case HeroAttributesManager.BUY_MEDICINES_STATUS_SUC: {
                        ToastHelper.shortToast(String.format(view.getContext().getString(R.string.string_buy_medicines_suc), buyMedicinesResp.life, buyMedicinesResp.price));
                        break;
                    }
                }
            }
        });
    }
}
