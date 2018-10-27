package com.mdove.levelgame.main.home.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.BigMonsters;

/**
 * Created by MDove on 2018/10/27.
 */

public class BigMonstersModelVM {
    public ObservableField<Long> id=new ObservableField<>();
    public ObservableField<String> name=new ObservableField<>();
    public ObservableField<String> tips=new ObservableField<>();
    public ObservableField<String> attack=new ObservableField<>();
    public ObservableField<String> armor=new ObservableField<>();
    public ObservableField<String> life=new ObservableField<>();

    public BigMonstersModelVM(BigMonsters bigMonsters){
        id.set(bigMonsters.id);
        name.set(String.format(App.getAppContext().getString(R.string.string_big_monsters_name),bigMonsters.name));
        tips.set(bigMonsters.tips);
        attack.set(String.format(App.getAppContext().getString(R.string.string_big_monsters_attack),bigMonsters.attack));
        armor.set(String.format(App.getAppContext().getString(R.string.string_big_monsters_armor),bigMonsters.armor));
        life.set(String.format(App.getAppContext().getString(R.string.string_big_monsters_life),bigMonsters.life));
    }
}
