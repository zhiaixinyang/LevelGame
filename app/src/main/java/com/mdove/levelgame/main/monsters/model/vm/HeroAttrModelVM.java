package com.mdove.levelgame.main.monsters.model.vm;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Monsters;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/11/2.
 */

public class HeroAttrModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> exp = new ObservableField<>();
    public ObservableField<Long> monsterPlaceId = new ObservableField<>();

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();

    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public int lifeInit;
    public int curLife;
    public ObservableField<Integer> lifeProgress = new ObservableField<>();
    public ObservableField<String> harm = new ObservableField<>();
    public ObservableField<String> btnText = new ObservableField<>();

    public HeroAttrModelVM(HeroAttributes model) {
        lifeInit = model.maxLife;
        curLife = model.curLife;
        id.set(model.id);
        name.set(App.getAppContext().getString(R.string.string_hero_name));
        attack.set(String.format(App.getAppContext().getString(R.string.monsters_msg_attack), model.attack));
        armor.set(String.format(App.getAppContext().getString(R.string.monsters_msg_armor), model.armor));
        money.set(String.format(App.getAppContext().getString(R.string.monsters_msg_money), model.money));
        life.set(String.format(App.getAppContext().getString(R.string.hero_msg_has_life), model.curLife, model.maxLife));
        resetLifeProgress(model.curLife);
        harm.set(0 + "");
    }

    public void resetLifeProgress(int curLife) {
        float progress = (float) curLife / lifeInit;
        BigDecimal bg = new BigDecimal(progress);
        float real = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        lifeProgress.set((int) (real * 100));
    }

    public void resetLife(int consume) {
        curLife -= consume;
        resetLifeProgress(curLife);
        life.set(String.format(App.getAppContext().getString(R.string.hero_msg_has_life), curLife, lifeInit));
    }
}
