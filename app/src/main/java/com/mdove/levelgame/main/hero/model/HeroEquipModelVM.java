package com.mdove.levelgame.main.hero.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.utils.SrcIconMap;

/**
 * @author MDove on 2018/10/23
 */
public class HeroEquipModelVM {
    public ObservableField<Long> pkId = new ObservableField<>();
    public ObservableField<Long> strengthen = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();

    // goodType:1武器2防具3饰品
    public HeroEquipModelVM(long pkId,  String tips, long strengthen, String name, int attack, int armor, int life, String type, boolean isEquip, int goodType) {
        this.pkId.set(pkId);
        this.strengthen.set(strengthen);
        this.name.set(name);
        this.tips.set(tips);
        src.set(SrcIconMap.getInstance().getSrc(type));
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
        if (!isEquip) {
            if (goodType == 1) {
                this.attack.set(App.getAppContext().getString(R.string.string_no_hold_on_attack));
                this.armor.set(App.getAppContext().getString(R.string.string_no_hold_on_attack));
                this.life.set(App.getAppContext().getString(R.string.string_no_hold_on_attack));
            } else if (goodType == 2) {
                this.attack.set(App.getAppContext().getString(R.string.string_no_hold_on_armor));
                this.armor.set(App.getAppContext().getString(R.string.string_no_hold_on_armor));
                this.life.set(App.getAppContext().getString(R.string.string_no_hold_on_armor));
            } else if (goodType == 3) {
                this.attack.set(App.getAppContext().getString(R.string.string_no_hold_on_accessories));
                this.armor.set(App.getAppContext().getString(R.string.string_no_hold_on_accessories));
                this.life.set(App.getAppContext().getString(R.string.string_no_hold_on_accessories));
            }
        }
        this.type.set(type);
    }
}
