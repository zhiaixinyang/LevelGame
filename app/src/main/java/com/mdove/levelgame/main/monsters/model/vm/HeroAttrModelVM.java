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
    public ObservableField<Long> dropGoodsId = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();

    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public int lifeInit;
    public ObservableField<String> limitCount = new ObservableField<>();
    public ObservableField<String> power = new ObservableField<>();
    public ObservableField<Integer> lifeProgress = new ObservableField<>();

    public ObservableField<String> btnText = new ObservableField<>();
    public ObservableField<Boolean> isSpecial = new ObservableField<>();
    public ObservableField<Boolean> isBusinessman = new ObservableField<>();
    public ObservableField<Boolean> isLimitCount = new ObservableField<>();
    private int limitCountInt;

    public HeroAttrModelVM(HeroAttributes model) {
        id.set(model.id);
        attack.set(String.format(App.getAppContext().getString(R.string.monsters_msg_attack), model.attack));
        armor.set(String.format(App.getAppContext().getString(R.string.monsters_msg_armor), model.armor));
        money.set(String.format(App.getAppContext().getString(R.string.monsters_msg_money), model.money));
        life.set(String.format(App.getAppContext().getString(R.string.monsters_msg_life), model.maxLife));
        lifeProgress.set(100);
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        lifeProgress.set((int) (100-aLong));
                    }
                });
    }

    public void resetLifeProgress(int curLife) {
        float progress = (float) curLife / lifeInit;
        BigDecimal bg = new BigDecimal(progress);
        float real = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        lifeProgress.set((int) (real * 100));
    }
}
