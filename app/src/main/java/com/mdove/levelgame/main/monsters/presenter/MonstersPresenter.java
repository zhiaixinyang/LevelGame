package com.mdove.levelgame.main.monsters.presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseNormalDialog;
import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.HeroAttributesActivity;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.home.HomeActivity;
import com.mdove.levelgame.main.home.MainActivity;
import com.mdove.levelgame.main.home.model.vm.TopMainMenuModelVM;
import com.mdove.levelgame.main.monsters.manager.MonsterAttackManager;
import com.mdove.levelgame.main.monsters.manager.SpecialMonsterManager;
import com.mdove.levelgame.main.monsters.manager.exception.AttackMonsterException;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM;
import com.mdove.levelgame.main.monsters.model.vm.FightMonstersVM;
import com.mdove.levelgame.main.monsters.model.vm.MenuMonsterModelVM;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.shop.BusinessmanActivity;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.CustomMonsterDialog;
import com.mdove.levelgame.view.FightingDialog;
import com.mdove.levelgame.view.MyDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPresenter implements MonstersConstract.IMonstersPresenter {
    private MonstersConstract.IMonstersView view;
    private List<BaseMonsterModelVM> realData;
    private long monstersPlaceId;

    @Override
    public void subscribe(MonstersConstract.IMonstersView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
    }

    public void setPlaceId(long monstersPlaceId) {
        this.monstersPlaceId = monstersPlaceId;
    }

    @Override
    public void initData(long monstersPlaceId) {
        // 特殊怪物出现设置
        SpecialMonsterManager.getInstance().setShowSpecialMonster();

        this.monstersPlaceId = monstersPlaceId;
        List<Monsters> monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder()
                .where(MonstersDao.Properties.MonsterPlaceId.eq(monstersPlaceId), MonstersDao.Properties.IsShow.eq(0)).list();
        realData = new ArrayList<>();
        if (monsters == null || monsters.size() == 0) {
            return;
        }
        realData.add(new MenuMonsterModelVM());
        for (Monsters monster : monsters) {
            if (monster.monsterPlaceId == monstersPlaceId && monster.isShow == 0) {
                realData.add(new MonstersModelVM(monster));
            }
        }
        view.showData(realData);
    }

    @Override
    public void initPower() {
        view.showPowerText(String.format(view.getString(R.string.string_activity_monsters_power), HeroManager.getInstance().getHeroAttributes().bodyPower, 100));
    }

    @Override
    public void initMoney() {
        view.showMoneyText(String.format(view.getString(R.string.string_activity_monsters_money), HeroManager.getInstance().getHeroAttributes().money));
    }

    @Override
    public void initlife() {
        HeroAttributes heroAttributes = HeroManager.getInstance().getHeroAttributes();
        float progress = (float) heroAttributes.curLife / heroAttributes.maxLife;
        BigDecimal bg = new BigDecimal(progress);
        float real = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        view.showLifeText((int) (real * 100), String.format(view.getString(R.string.string_activity_monsters_hero_life), heroAttributes.curLife, heroAttributes.maxLife));
    }

    @Override
    public void heroRest() {
        final int restStatus = HeroAttributesManager.getInstance().heroRest();
        if (restStatus == 0 || restStatus == 2) {
            view.showLoadingDialog(view.getContext().getString(R.string.string_rest_loading_msg));
        } else if (restStatus == 1) {
            MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_no_can_rest_title),
                    view.getString(R.string.string_no_can_rest_content),
                    view.getString(R.string.string_no_can_rest_nav_btn),
                    view.getString(R.string.string_no_can_rest_pos_btn), true, () -> HomeActivity.start(view.getContext()));
        }

        Observable.just(1)
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (restStatus == 2 && AppConfig.enableBigMonster()) {
                        MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_big_monsters_show_title),
                                view.getString(R.string.string_big_monsters_show_content), true);
                    }
                    initPower();
                    initData(monstersPlaceId);
                    view.dismissLoadingDialog();
                });

    }

    @Override
    public void onItemMyPackage() {
        view.showMyPackage();
    }

    @Override
    public void onItemMyAttr() {
        HeroAttributesActivity.start(view.getContext());
    }

    @SuppressLint("CheckResult")
    @Override
    public void onItemBtnClick(String type, final Long id) {
        // 野外商人
        Monsters monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder().where(MonstersDao.Properties.Id.eq(id)).unique();
        if (monsters != null && monsters.isBusinessman == 0) {
            BusinessmanActivity.start(view.getContext(), id);
            return;
        }
        // 战斗逻辑
        final FightingDialog dialog = new FightingDialog(view.getContext(), monsters);
        dialog.setOnDismissListener(dialog1 -> updateUI());

        MonsterAttackManager.getInstance().attackEnemyPre(monsters)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        dialog.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof AttackMonsterException) {
                            AttackMonsterException exception = (AttackMonsterException) e;
                            switch (exception.errorCode) {
                                case AttackMonsterException.ERROR_CODE_HERO_IS_NO_LIFE:
                                case AttackMonsterException.ERROR_CODE_HERO_NO_POWER:
                                case AttackMonsterException.ERROR_CODE_HERO_NO_COUNT:
                                case AttackMonsterException.ERROR_CODE_MONSTER_IS_QUICK_ATTACK:
                                case AttackMonsterException.ERROR_CODE_HERO_IS_QUICK_ATTACK_IS_DROP:
                                case AttackMonsterException.ERROR_CODE_MONSTERS_IS_DEAD: {
                                    MyDialog.showMyDialog(view.getContext(), exception.errorTitle, exception.errorMsg, true);
                                    updateUI();
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onItemLongBtnOnClick(Long id) {
        new CustomMonsterDialog(view.getContext(), id).show();
    }

    private void updateUI() {
        initPower();
        initMoney();
        initlife();
        initData(monstersPlaceId);
    }
}
