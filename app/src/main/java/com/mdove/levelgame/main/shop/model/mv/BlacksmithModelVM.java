package com.mdove.levelgame.main.shop.model.mv;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.SrcIconMap;

/**
 * @author zhaojing on 2018/10/28
 */
public class BlacksmithModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public ObservableField<String> btnName = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();

    public BlacksmithModelVM(Weapons model) {
        id.set(model.id);
        tips.set(model.tips);
        // 应对特殊换矿服务
        if (model.type.startsWith("E")) {
            name.set("[服务：换]" + model.name);
        } else {
            name.set(model.name);
        }

        attack.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_attack), model.attack));
        life.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_life), 0));
        armor.set("");
        type.set(model.type);
        src.set(SrcIconMap.getInstance().getSrc(model.type));
        if (model.armor > 0) {
            armor.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_armor), model.armor));
        }
        if (model.isCanUpdate == 0) {
            btnName.set(App.getAppContext().getString(R.string.string_is_can_update));
        } else if (model.isCanMixture == 0) {
            btnName.set(App.getAppContext().getString(R.string.string_is_can_mixture));
        }
    }

    public BlacksmithModelVM(Armors model) {
        id.set(model.id);
        tips.set(model.tips);
        // 应对特殊换矿服务
        if (model.type.startsWith("E")) {
            name.set("[服务：换]" + model.name);
        } else {
            name.set(model.name);
        }
        attack.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_attack), model.attack));
        life.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_life), 0));
        armor.set("");
        type.set(model.type);
        src.set(SrcIconMap.getInstance().getSrc(model.type));
        if (model.armor > 0) {
            armor.set(String.format(App.getAppContext().getString(R.string.shop_attack_msg_armor), model.armor));
        }
        if (model.isCanUpdate == 0) {
            btnName.set(App.getAppContext().getString(R.string.string_is_can_update));
        } else if (model.isCanMixture == 0) {
            btnName.set(App.getAppContext().getString(R.string.string_is_can_mixture));
        }
    }

    public BlacksmithModelVM(Accessories model) {
        id.set(model.id);
        tips.set(model.tips);
        // 应对特殊换矿服务
        if (model.type.startsWith("E")) {
            name.set("[服务：换]" + model.name);
        } else {
            name.set(model.name);
        }
        attack.set(String.format(App.getAppContext().getString(R.string.shop_accessories_msg_attack), model.attack));
        armor.set(String.format(App.getAppContext().getString(R.string.shop_accessories_msg_armor), model.armor));
        life.set(String.format(App.getAppContext().getString(R.string.shop_accessories_msg_life), model.life));
        type.set(model.type);
        src.set(SrcIconMap.getInstance().getSrc(model.type));
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
