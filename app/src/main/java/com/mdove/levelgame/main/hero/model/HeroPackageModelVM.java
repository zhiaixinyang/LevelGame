package com.mdove.levelgame.main.hero.model;

import android.annotation.SuppressLint;
import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackageModelVM {
    public ObservableField<Long> pkId = new ObservableField<>();
    public ObservableField<Long> strengthen = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    private int attackInt;
    public ObservableField<String> armor = new ObservableField<>();
    private int armorInt;
    public ObservableField<String> type = new ObservableField<>();

    public HeroPackageModelVM(Long id, long strengthen, String name, int attack, int armor, String type) {
        pkId.set(id);
        attackInt = attack;
        armorInt = armor;
        this.strengthen.set(strengthen);
        if (strengthen > 0) {
            this.name.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_name_strengthen), name, strengthen));
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack), (int) ((1 + strengthen * 0.2) * attack)));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor), (int) ((1 + strengthen * 0.2) * armor)));
        } else {
            this.name.set(name);
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack), attack));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor), armor));
        }
        this.type.set(type);
    }

    public void reName(long strengthen) {
        if (strengthen > 0) {
            this.strengthen.set(strengthen);
            this.name.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_name_strengthen), name.get(), strengthen));
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack), (int) ((1 + strengthen * 0.2) * attackInt)));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor), (int) ((1 + strengthen * 0.2) * armorInt)));
        }
    }
}
