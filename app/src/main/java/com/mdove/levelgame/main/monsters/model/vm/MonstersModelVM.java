package com.mdove.levelgame.main.monsters.model.vm;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class MonstersModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> exp = new ObservableField<>();
    public ObservableField<Long> monsterPlaceId = new ObservableField<>();

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> dropGoodsId = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();

    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();

    public ObservableField<String> btnText = new ObservableField<>();

    public MonstersModelVM(MonstersModel model) {
        id.set(model.id);
        exp.set(String.format(App.getAppContext().getString(R.string.monsters_msg_exp),model.exp));
        monsterPlaceId.set(model.monsterPlaceId);

        name.set(model.name);
        tips.set(model.tips);
        dropGoodsId.set(model.dropGoodsId);

        attack.set(String.format(App.getAppContext().getString(R.string.monsters_msg_attack),model.attack));
        armor.set(String.format(App.getAppContext().getString(R.string.monsters_msg_armor),model.armor));
        money.set(String.format(App.getAppContext().getString(R.string.monsters_msg_money),model.money));
        life.set(String.format(App.getAppContext().getString(R.string.monsters_msg_life),model.life));
        type.set(model.type);

        btnText.set("攻击");
    }
}
