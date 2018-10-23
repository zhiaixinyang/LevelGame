package com.mdove.levelgame.main.shop.model.mv;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.main.shop.model.ShopArmorModel;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;

/**
 * @author zhaojing on 2018/10/22
 */
public class ShopArmorModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();

    public ShopArmorModelVM(ShopArmorModel model) {
        id.set(model.id);
        tips.set(model.tips);
        name.set(model.name);
        attack.set("");
        if (model.attack > 0) {
            attack.set(String.format(App.getAppContext().getString(R.string.shop_armor_msg_attack), model.attack));
        }
        armor.set(String.format(App.getAppContext().getString(R.string.shop_armor_msg_armor), model.armor));
        price.set(String.format(App.getAppContext().getString(R.string.shop_armor_msg_price), model.price));
    }
}
