package com.mdove.levelgame.main.monsters.model.vm;

import android.databinding.ObservableField;

import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class MonstersPlaceModelVM {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<Long> id = new ObservableField<>();

    public MonstersPlaceModelVM(MonstersPlace model) {
        name.set(model.name);
        tips.set(model.tips);
        id.set(model.id);
    }
}
