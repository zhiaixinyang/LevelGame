package com.mdove.levelgame.main.hero.model;

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
    public String nameInit;
    public ObservableField<String> attack = new ObservableField<>();
    private int attackInit;
    public ObservableField<String> armor = new ObservableField<>();
    private int armorInit;
    public ObservableField<String> life = new ObservableField<>();
    private int lifeInit;
    public ObservableField<String> type = new ObservableField<>();

    public HeroPackageModelVM(Long id, long strengthen, String name, int attack, int armor, int life, String type) {
        pkId.set(id);
        attackInit = attack;
        armorInit = armor;
        lifeInit = life;
        nameInit = name;
        this.strengthen.set(strengthen);
        if (strengthen > 0) {
            this.name.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_name_strengthen), name, strengthen));
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack), (int) ((1 + strengthen * 0.2) * attack)));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor), (int) ((1 + strengthen * 0.2) * armor)));
            this.life.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_life), (int) ((1 + strengthen * 0.2) * life)));
        } else {
            this.name.set(name);
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack), attack));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor), armor));
            this.life.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_life), life));
        }
        this.type.set(type);
    }

    public void reName(long strengthen) {
        if (strengthen > 0) {
            this.strengthen.set(strengthen);
            this.name.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_name_strengthen), nameInit, strengthen));
            this.attack.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_attack), (int) ((1 + strengthen * 0.2) * attackInit)));
            this.armor.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_armor), (int) ((1 + strengthen * 0.2) * armorInit)));
            this.life.set(String.format(App.getAppContext().getString(R.string.string_pk_msg_life), (int) ((1 + strengthen * 0.2) * lifeInit)));
        }
    }
}
