package com.mdove.levelgame.main.home.presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.CityDao;
import com.mdove.levelgame.greendao.MainMenuDao;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.City;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.HeroAttributesActivity;
import com.mdove.levelgame.main.hero.HeroPackagesActivity;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.home.SettingActivity;
import com.mdove.levelgame.main.home.city.CityDialog;
import com.mdove.levelgame.main.home.city.model.CityReps;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;
import com.mdove.levelgame.main.home.model.MainMenuModelVM;
import com.mdove.levelgame.main.monsters.MonstersActivity;
import com.mdove.levelgame.main.shop.BlacksmithActivity;
import com.mdove.levelgame.main.shop.ShopActivity;
import com.mdove.levelgame.main.skill.HomeSkillActivity;
import com.mdove.levelgame.main.task.TaskActivity;
import com.mdove.levelgame.update.UpdateDialog;
import com.mdove.levelgame.update.UpdateStatusManager;
import com.mdove.levelgame.update.model.RealUpdate;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.BigFightingDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author MDove on 2018/10/31
 */
public class HomePresenter implements HomeContract.IHomePresenter {
    private static final int INTENT_TO_MONSTERS_PLACE = 1;
    private static final int INTENT_TO_SHOP = 2;
    private static final int INTENT_TO_BLACKSMITH = 3;
    private static final int INTENT_TO_HERO_ATTR = 4;
    private static final int INTENT_TO_HERO_PACKAGE = 5;
    private static final int INTENT_TO_HOME_SKILL = 6;
    private static final int INTENT_TO_HOME_TASK = 7;
    private HomeContract.IHomeView view;

    @Override
    public void onClickItemId(MainMenuModelVM vm) {
        switch (vm.id.get().intValue()) {
            case INTENT_TO_MONSTERS_PLACE:
                new CityDialog(view.getContext(), R.style.fb_dialog).show();
                break;
            case INTENT_TO_SHOP: {
                ShopActivity.Companion.start(view.getContext());
                break;
            }
            case INTENT_TO_BLACKSMITH: {
                BlacksmithActivity.start(view.getContext());
                break;
            }
            case INTENT_TO_HERO_ATTR: {
                HeroAttributesActivity.start(view.getContext());
                break;
            }
            case INTENT_TO_HERO_PACKAGE: {
                HeroPackagesActivity.start(view.getContext());
                break;
            }
            case INTENT_TO_HOME_SKILL: {
                HomeSkillActivity.Companion.start(view.getContext());
                break;
            }
            case INTENT_TO_HOME_TASK: {
                TaskActivity.Companion.start(view.getContext());
                break;
            }
            default:
                break;
        }
    }

    private CityReps preCityResp;

    @Override
    public void subscribe(HomeContract.IHomeView view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initMenu(CityReps cityReps) {
        if (cityReps.isMonsterPlace()) {
            MonstersActivity.start(view.getContext(), cityReps.getPlaceId(), cityReps.getPlaceTitle());
            return;
        }
        if (preCityResp != null && preCityResp.getPlaceId() == cityReps.getPlaceId()) {
            return;
        }
        preCityResp = cityReps;
        view.showLoadingDialog(view.getString(R.string.string_init_home_loading));
        Observable.just(cityReps.getPlaceId()).map(aLong -> {
            City city = DatabaseManager.getInstance().getCityDao().queryBuilder().where(CityDao.Properties.Id.eq(aLong)).unique();
            List<Integer> btnIds = null;
            if (city != null) {
                btnIds = city.menuBtnListId;
            }
            return btnIds;
        }).map(integers -> {
            List<MainMenuModelVM> mainMenus = null;
            if (integers != null) {
                mainMenus = new ArrayList<>();
                MainMenuDao mainMenuDao = DatabaseManager.getInstance().getMainMenuDao();
                List<MainMenu> tempMainMenu = new ArrayList<>();
                StringBuilder npcGudie = new StringBuilder();
                for (Integer id : integers) {
                    MainMenu mainMenu = mainMenuDao.queryBuilder().where(MainMenuDao.Properties.Id.eq(id)).unique();
                    if (mainMenu != null) {
                        tempMainMenu.add(mainMenu);
                        npcGudie.append(mainMenu.name).append(" -> ");
                    }
                }
                if (npcGudie.toString().endsWith(" -> ")) {
                    npcGudie = new StringBuilder(npcGudie.substring(0, npcGudie.length() - 4));
                }
                for (MainMenu mainMenu : tempMainMenu) {
                    mainMenus.add(new MainMenuModelVM(mainMenu, npcGudie.toString()));
                }
            }
            return mainMenus;
        }).compose(RxTransformerHelper.schedulerTransf())
                .subscribe(mainMenuModelVMS -> {
                    view.showMenu(mainMenuModelVMS);
                    view.dismissLoadingDialog();
                }, throwable -> {
                    ToastHelper.shortToast("出现异常情况，请清除数据再次尝试进入。");
                    view.dismissLoadingDialog();
                });
    }

    @Override
    public void initBigMonster() {
        if (!AppConfig.enableBigMonster()) {
            return;
        }
        if (HeroAttributesManager.getInstance().goneBigMonster()) {
            view.showBigMonsters(null);
        } else {
            BigMonsters bigMonsters = DatabaseManager.getInstance().getBigMonstersDao().queryBuilder().where(BigMonstersDao.Properties.IsGone.eq(1)).unique();
            if (bigMonsters != null) {
                view.showBigMonsters(new BigMonstersModelVM(bigMonsters));
            }
        }
    }

    @Override
    public void initBigMonsterInvade() {
        if (!AppConfig.enableBigMonster()) {
            return;
        }
        BigMonstersDao bigMonstersDao = DatabaseManager.getInstance().getBigMonstersDao();
        List<BigMonsters> bigMonsters = bigMonstersDao.queryBuilder().where(BigMonstersDao.Properties.IsDead.eq(1)).list();
        if (bigMonsters != null && bigMonsters.size() > 0) {
            BigMonsters bigMonster = bigMonsters.get(0);
            if (bigMonster.isGone == 0) {
                view.showBigMonsterInvade(String.format(view.getString(R.string.string_big_monsters_distance_invade),
                        bigMonster.days - HeroManager.getInstance().getHeroAttributes().days));
                return;
            } else {
                view.showBigMonsterInvade(view.getString(R.string.string_big_monsters_is_show));
            }
        }
    }

    @Override
    public void onClickSetting() {
        SettingActivity.Companion.start(view.getContext());
    }

    @Override
    public void onClickBigMonsters(BigMonstersModelVM vm) {
        initBigMonster();
        final BigFightingDialog dialog = new BigFightingDialog(view.getContext(), vm.bigMonsters);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                initBigMonster();
                initBigMonsterInvade();
            }
        });
        dialog.show();
        return;

//        AttackResp resp = HeroAttributesManager.getInstance().attackBigMonsters(vm.id.get());
//        switch (resp.attackStatus) {
//            case HeroAttributesManager.ATTACK_STATUS_ERROR: {
//                ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
//                break;
//            }
//            case HeroAttributesManager.ATTACK_STATUS_NO_POWER: {
//                ToastHelper.shortToast(view.getContext().getString(R.string.string_no_power));
//                break;
//            }
//            case HeroAttributesManager.ATTACK_STATUS_FAIL: {
//                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_attack_big_monster_fail)
//                        , view.getContext().getString(R.string.string_attack_fail), true);
//                break;
//            }
//            case HeroAttributesManager.ATTACK_STATUS_WIN: {
//                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_attack_big_monster_suc)
//                        , String.format(view.getContext().getString(R.string.string_attack_win), resp.money, resp.exp, resp.life), true);
//                // 刷新时间
//                initBigMonsterInvade();
//                break;
//            }
//            case HeroAttributesManager.ATTACK_STATUS_HAS_DROP_GOODS: {
//                String dropGood = "";
//                for (String name : resp.dropGoods) {
//                    dropGood = name + "、";
//                }
//                dropGood = dropGood.substring(0, dropGood.length() - 1);
//                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_attack_big_monster_suc)
//                        , String.format(view.getContext().getString(R.string.string_attack_win_has_goods), resp.money, resp.exp, resp.life, dropGood), true);
//                break;
//            }
//            default:
//                break;
//        }
    }

    @Override
    public void initGuide() {
        if (!AppConfig.isShowGuide()) {
            AppConfig.setShowGuide();
            view.showGuide();
        }
    }

    @Override
    public void checkUpdate(String version) {
        App.getApiServer().checkUpdate(version)
                .compose(RxTransformerHelper.<RealUpdate>schedulerTransf())
                .subscribe(new Consumer<RealUpdate>() {
                    @Override
                    public void accept(RealUpdate realUpdate) throws Exception {
                        switch (realUpdate.getCheck()) {
                            case "true": {
                                if (UpdateStatusManager.isShowUpdateDialog()) {
                                    showUpgradeDialog(realUpdate);
                                }
                                break;
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


    private void showUpgradeDialog(final RealUpdate result) {
        new UpdateDialog(view.getContext(), result.getSrc()).show();
    }


    @Override
    public void unSubscribe() {

    }
}
