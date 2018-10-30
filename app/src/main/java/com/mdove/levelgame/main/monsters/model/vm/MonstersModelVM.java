package com.mdove.levelgame.main.monsters.model.vm;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Monsters;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> exp = new ObservableField<>();
    public ObservableField<Long> monsterPlaceId = new ObservableField<>();

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<Long> dropGoodsId = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();

    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public ObservableField<String> limitCount = new ObservableField<>();

    public ObservableField<String> btnText = new ObservableField<>();

    public MonstersModelVM(Monsters model) {
        id.set(model.id);
        exp.set(String.format(App.getAppContext().getString(R.string.monsters_msg_exp), model.exp));
        monsterPlaceId.set(model.monsterPlaceId);

        name.set(model.name);
        tips.set(model.tips);
        dropGoodsId.set(model.dropGoodsId);
        if (model.isLimitCount==0) {
            limitCount.set(String.format(App.getAppContext().getString(R.string.monsters_msg_has_count), model.curCount, model.limitCount));
        }else{
            limitCount.set("");
        }
        attack.set(String.format(App.getAppContext().getString(R.string.monsters_msg_attack), model.attack));
        armor.set(String.format(App.getAppContext().getString(R.string.monsters_msg_armor), model.armor));
        money.set(String.format(App.getAppContext().getString(R.string.monsters_msg_money), model.money));
        life.set(String.format(App.getAppContext().getString(R.string.monsters_msg_life), model.life));
        type.set(model.type);

        if (model.isBusinessman==0){
            btnText.set("购买");
        }else {
            btnText.set("攻击");
        }
    }
}
