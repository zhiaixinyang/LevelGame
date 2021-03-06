package com.mdove.levelgame.main.monsters.model.vm;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.Monsters;

import java.math.BigDecimal;

/**
 * Created by MDove on 2018/10/21.
 */

public class FightMonstersVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> exp = new ObservableField<>();
    public ObservableField<Long> monsterPlaceId = new ObservableField<>();

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<Long> dropGoodsId = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();

    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public int lifeInit;
    public int curLife;
    public ObservableField<String> limitCount = new ObservableField<>();
    public ObservableField<String> power = new ObservableField<>();
    public ObservableField<Integer> lifeProgress = new ObservableField<>();
    public ObservableField<String> harm = new ObservableField<>();
    public ObservableField<String> btnText = new ObservableField<>();
    public ObservableField<Boolean> isSpecial = new ObservableField<>();
    public ObservableField<Boolean> isBusinessman = new ObservableField<>();
    public ObservableField<Boolean> isLimitCount = new ObservableField<>();
    private int limitCountInt;

    public FightMonstersVM(Monsters model) {
        id.set(model.id);
        exp.set(String.format(App.getAppContext().getString(R.string.monsters_msg_exp), model.exp));
        monsterPlaceId.set(model.monsterPlaceId);
        limitCountInt = model.limitCount;
        name.set(model.name);
        tips.set(model.tips);
        power.set(String.format(App.getAppContext().getString(R.string.monsters_msg_use_power), model.consumePower));
        dropGoodsId.set(model.dropGoodsId);
        lifeInit = model.life;
        curLife = model.life;
        if (model.isLimitCount == 0) {
            isLimitCount.set(true);
            limitCount.set(String.format(App.getAppContext().getString(R.string.monsters_msg_has_count), model.curCount, model.limitCount));
        } else {
            isLimitCount.set(false);
        }
        attack.set(String.format(App.getAppContext().getString(R.string.monsters_msg_attack), model.attack));
        armor.set(String.format(App.getAppContext().getString(R.string.monsters_msg_armor), model.armor));
        money.set(String.format(App.getAppContext().getString(R.string.monsters_msg_money), model.money));
        life.set(String.format(App.getAppContext().getString(R.string.monsters_msg_has_life), curLife, lifeInit));
        type.set(model.type);
        lifeProgress.set(100);

        if (model.isBusinessman == 0) {
            btnText.set("进入");
            isBusinessman.set(true);
        } else {
            btnText.set("攻击");
            isBusinessman.set(false);
        }
        harm.set("");
    }

    private void resetLifeProgress(int curLife) {
        float progress = (float) curLife / lifeInit;
        BigDecimal bg = new BigDecimal(progress);
        float real = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        lifeProgress.set((int) (real * 100));
    }

    public void resetLife(int consume) {
        curLife += consume;
        resetLifeProgress(curLife);
        life.set(String.format(App.getAppContext().getString(R.string.monsters_msg_has_life), curLife, lifeInit));
    }

    public void resetLimitCount(int curCount) {
        if (isLimitCount.get()) {
            limitCount.set(String.format(App.getAppContext().getString(R.string.monsters_msg_has_count), curCount, limitCountInt));
        }
    }
}
