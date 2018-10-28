package com.mdove.levelgame.main.shop.model.mv;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;

/**
 * @author zhaojing on 2018/10/28
 */
public class BlacksmithModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> btnName = new ObservableField<>();

    public BlacksmithModelVM(Weapons model) {
        id.set(model.id);
        tips.set(model.tips);
        name.set(model.name);
        attack.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_attack), model.attack));
        armor.set("");
        if (model.armor > 0) {
            armor.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_armor), model.armor));
        }
        if (model.isCanUpdate == 0) {
            btnName.set(App.getAppContext().getString(R.string.string_is_can_update));
        } else if (model.isCanMixture == 0) {
            btnName.set(App.getAppContext().getString(R.string.string_is_can_mixture));
        }
    }
}
