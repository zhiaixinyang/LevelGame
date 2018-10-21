package com.mdove.levelgame.main.hero.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.HeroAttributes;

/**
 * Created by MDove on 2018/10/21.
 */

public class HeroAttributesModelVM {
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> level = new ObservableField<>();
    public ObservableField<String> needExperience = new ObservableField<>();

    public HeroAttributesModelVM(HeroAttributes heroAttributes) {
        attack.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_attack), heroAttributes.attack, heroAttributes.attackIncrease));
        armor.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_armor), heroAttributes.armor, heroAttributes.armorIncrease));
        life.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_life), heroAttributes.curLife, heroAttributes.maxLife, heroAttributes.lifeIncrease));
        money.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_money), heroAttributes.money));
        level.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_level), heroAttributes.level));
        needExperience.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_need_exp), heroAttributes.experience, getLevelExp(heroAttributes)));
    }

    private long getLevelExp(HeroAttributes heroAttributes) {
        long levelExp = heroAttributes.baseExp;
        if (heroAttributes.level > 1) {
            levelExp = heroAttributes.level * heroAttributes.expMultiple * heroAttributes.baseExp;
        }
        return levelExp;
    }
}
