package com.mdove.levelgame.main.hero.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;

/**
 * @author MDove on 2018/10/23
 */
public class HasEquipModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();

    public HasEquipModelVM(Long id, String name, String attack, String armor, String type) {
        this.id.set(id);
        this.name.set(name);

        this.attack.set(String.format(App.getAppContext().getString(R.string.string_equip_msg_attack), attack));
        this.armor.set(String.format(App.getAppContext().getString(R.string.string_equip_msg_armor), armor));
        this.type.set(type);
    }
}
