package com.mdove.levelgame.main.shop.model.mv;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.utils.SrcIconMap;

/**
 * Created by MDove on 2018/10/21.
 */

public class MedicinesModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> lifeUp = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();
    public ObservableField<String> limitCount = new ObservableField<>();
    public ObservableField<Boolean> isLimitCount = new ObservableField<>();
    public ObservableField<Boolean> isLock = new ObservableField<>();
    private int limitCountInt;
    private int curlimitCountInt;

    public MedicinesModelVM(Medicines medicines) {
        id.set(medicines.id);
        tips.set(medicines.tips);
        name.set(medicines.name);
        if (medicines.isLock == 1) {
            isLock.set(false);
        } else {
            isLock.set(true);
        }
        price.set(String.format(App.getAppContext().getString(R.string.medicines_msg_price), medicines.price));
        life.set(String.format(App.getAppContext().getString(R.string.medicines_msg_life), medicines.life));
        armor.set(String.format(App.getAppContext().getString(R.string.medicines_msg_armor), medicines.armor));
        attack.set(String.format(App.getAppContext().getString(R.string.medicines_msg_attack), medicines.attack));
        lifeUp.set(String.format(App.getAppContext().getString(R.string.medicines_msg_life_up), medicines.lifeUp));
        src.set(SrcIconMap.getInstance().getSrc(medicines.type));
        limitCountInt = medicines.limitCount;
        curlimitCountInt = medicines.curCount;
        if (medicines.isLimitCount == 0) {
            isLimitCount.set(true);
            limitCount.set(String.format(App.getAppContext().getString(R.string.string_buy_msg_has_count), medicines.curCount, medicines.limitCount));
        } else {
            isLimitCount.set(false);
        }
    }

    public void resetLimitCount() {
        curlimitCountInt--;
        if (isLimitCount.get()) {
            limitCount.set(String.format(App.getAppContext().getString(R.string.string_buy_msg_has_count), curlimitCountInt, limitCountInt));
        }
    }
}
