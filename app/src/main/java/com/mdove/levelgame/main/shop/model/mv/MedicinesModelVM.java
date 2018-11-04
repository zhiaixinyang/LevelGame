package com.mdove.levelgame.main.shop.model.mv;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.utils.WeaponsIconMap;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class MedicinesModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public ObservableField<String> lifeUp = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();

    public MedicinesModelVM(Medicines medicines) {
        id.set(medicines.id);
        tips.set(medicines.tips);
        name.set(medicines.name);
        price.set(String.format(App.getAppContext().getString(R.string.medicines_msg_price), medicines.price));
        life.set(String.format(App.getAppContext().getString(R.string.medicines_msg_life), medicines.life));
        lifeUp.set(String.format(App.getAppContext().getString(R.string.medicines_msg_life_up), medicines.lifeUp));
        src.set(WeaponsIconMap.getInstance().getSrc(medicines.type));
    }
}
