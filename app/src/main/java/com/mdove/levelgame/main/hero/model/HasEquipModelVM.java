package com.mdove.levelgame.main.hero.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;

/**
 * @author MDove on 2018/10/23
 */
public class HasEquipModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<Long> strengthen = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();

    public HasEquipModelVM(Long id, long strengthen, String name, int attack, int armor, String type, boolean isEquip,boolean isWeapons) {
        this.id.set(id);
        this.strengthen.set(strengthen);
        this.name.set(name);
        if (strengthen > 0) {
            this.name.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_name_strengthen), name, strengthen));
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack),  (int)((1 + strengthen * 0.2) * attack)));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor),  (int)((1 + strengthen * 0.2) * armor)));
        } else {
            this.name.set(name);
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack), attack));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor), armor));
        }
        if (!isEquip) {
            if (isWeapons) {
                this.attack.set(App.getAppContext().getString(R.string.string_no_hold_on_attack));
                this.armor.set(App.getAppContext().getString(R.string.string_no_hold_on_attack));
            }else{
                this.attack.set(App.getAppContext().getString(R.string.string_no_hold_on_armor));
                this.armor.set(App.getAppContext().getString(R.string.string_no_hold_on_armor));
            }
        }
        this.type.set(type);
    }
}
