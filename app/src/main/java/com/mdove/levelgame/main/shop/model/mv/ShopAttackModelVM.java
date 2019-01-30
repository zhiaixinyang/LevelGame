package com.mdove.levelgame.main.shop.model.mv;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.SrcIconMap;

/**
 * @author MDove on 2018/10/22
 */
public class ShopAttackModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<Boolean> isLock = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();

    public ShopAttackModelVM(Weapons model) {
        id.set(model.id);
        tips.set(model.tips);
        name.set(model.name);
        attack.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_attack), model.attack));
        if (model.armor > 0) {
            armor.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_armor), model.armor));
        } else {
            armor.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_armor), 0));
        }
        if (model.isLock == 1) {
            isLock.set(false);
        } else {
            isLock.set(true);
        }
        price.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_price), model.price));
        src.set(SrcIconMap.getInstance().getSrc(model.type));
    }
}
