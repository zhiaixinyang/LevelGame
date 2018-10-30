package com.mdove.levelgame.main.monsters.presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseNormalDialog;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.home.MainActivity;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.shop.BusinessmanActivity;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPresenter implements MonstersConstract.IMonstersPresenter {
    private MonstersConstract.IMonstersView view;
    private List<MonstersModelVM> realData;

    @Override
    public void subscribe(MonstersConstract.IMonstersView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
    }

    @Override
    public void initData(long monstersPlaceId) {
        List<Monsters> monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder()
                .where(MonstersDao.Properties.MonsterPlaceId.eq(monstersPlaceId), MonstersDao.Properties.IsShow.eq(0)).list();
        realData = new ArrayList<>();
        if (monsters == null || monsters.size() == 0) {
            return;
        }
        for (Monsters monster : monsters) {
            if (monster.monsterPlaceId == monstersPlaceId) {
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
    public void heroRest() {
        boolean isCanRest = HeroAttributesManager.getInstance().heroRest();
        if (isCanRest) {
            view.showLoadingDialog(view.getContext().getString(R.string.string_rest_loading_msg));
        } else {
            MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_no_can_rest_title),
                    view.getString(R.string.string_no_can_rest_content),
                    view.getString(R.string.string_no_can_rest_nav_btn),
                    view.getString(R.string.string_no_can_rest_pos_btn), true,
                    new BaseNormalDialog.BaseDialogListener() {
                        @Override
                        public void onClick() {
                            MainActivity.start(view.getContext());
                        }
                    });
        }

        Observable.just(1)
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        initPower();
                        view.dismissLoadingDialog();
                    }
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void onItemBtnClick(String type, final Long id) {
        Monsters monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder().where(MonstersDao.Properties.Type.eq(type)).unique();
        if (monsters != null && monsters.isBusinessman == 0) {
            BusinessmanActivity.start(view.getContext(), id);
            return;
        }
        MonstersModelVM modelVM = null;
        int uiIndex = -1;
        for (MonstersModelVM monstersModel : realData) {
            if (monstersModel.id.get() == id) {
                modelVM = monstersModel;
                uiIndex = realData.indexOf(monstersModel);
            }
        }

        if (modelVM != null && uiIndex != -1) {
            Observable.just(1)
                    .flatMap(new Function<Integer, ObservableSource<AttackResp>>() {
                        @Override
                        public ObservableSource<AttackResp> apply(Integer integer) throws Exception {
                            return Observable.just(HeroAttributesManager.getInstance().attack(id));
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AttackResp>() {
                        @Override
                        public void accept(AttackResp resp) throws Exception {
                            switch (resp.attackStatus) {
                                case HeroAttributesManager.ATTACK_STATUS_ERROR: {
                                    view.showLoadingDialog(view.getContext().getString(R.string.string_error));
                                    break;
                                }
                                case HeroAttributesManager.ATTACK_STATUS_NO_POWER: {
                                    ToastHelper.shortToast(view.getContext().getString(R.string.string_no_power));
                                    break;
                                }
                                case HeroAttributesManager.ATTACK_STATUS_FAIL: {
                                    ToastHelper.shortToast(view.getContext().getString(R.string.string_attack_fail));
                                    break;
                                }
                                case HeroAttributesManager.ATTACK_STATUS_WIN: {
                                    MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_attack_win_title)
                                            , String.format(view.getContext().getString(R.string.string_attack_win), resp.money, resp.exp, resp.life), true);
                                    break;
                                }
                                case HeroAttributesManager.ATTACK_STATUS_HAS_DROP_GOODS: {
                                    String dropGood = "";
                                    for (String name : resp.dropGoods) {
                                        dropGood = name + "、";
                                    }
                                    dropGood = dropGood.substring(0, dropGood.length() - 1);
                                    MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_attack_win_title)
                                            , String.format(view.getContext().getString(R.string.string_attack_win_has_goods), resp.money, resp.exp, resp.life, dropGood), true);
                                    break;
                                }
                                case HeroAttributesManager.ATTACK_STATUS_NO_COUNT: {
                                    MyDialog.showMyDialog(view.getContext(), view.getContext().getString(R.string.string_attack_win_title)
                                            , R.string.string_attack_win_no_count, true);
                                    break;
                                }
                                default:
                                    break;
                            }
                            initPower();
                            view.dismissLoadingDialog();
                        }
                    });
        }
    }
}
