package com.mdove.levelgame.main.home.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.HeroAttributesActivity;
import com.mdove.levelgame.main.hero.HeroPackagesActivity;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;
import com.mdove.levelgame.main.home.model.MainMenuModelVM;
import com.mdove.levelgame.main.monsters.MonstersPlaceActivity;
import com.mdove.levelgame.main.shop.BlacksmithActivity;
import com.mdove.levelgame.main.shop.ShopActivity;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MDove on 2018/10/31
 */
public class HomePresenter implements HomeContract.IHomePresenter {
    private static final int INTENT_TO_MONSTERS_PLACE = 1;
    private static final int INTENT_TO_SHOP = 2;
    private static final int INTENT_TO_BLACKSMITH = 3;
    private static final int INTENT_TO_HERO_ATTR = 4;
    private static final int INTENT_TO_HERO_PACKAGE = 5;
    private HomeContract.IHomeView view;

    @Override
    public void onClickItemId(MainMenuModelVM vm) {
        switch (vm.id.get().intValue()) {
            case INTENT_TO_MONSTERS_PLACE:
                MonstersPlaceActivity.start(view.getContext());
                break;
            case INTENT_TO_SHOP: {
                ShopActivity.start(view.getContext());
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
            default:
                break;
        }
    }

    @Override
    public void subscribe(HomeContract.IHomeView view) {
        this.view = view;
    }

    @Override
    public void initMenu() {
        List<MainMenuModelVM> data = new ArrayList<>();

        List<MainMenu> mainMenus = DatabaseManager.getInstance().getMainMenuDao().loadAll();
        if (mainMenus != null && mainMenus.size() > 0) {
            for (MainMenu mainMenu : mainMenus) {
                data.add(new MainMenuModelVM(mainMenu));
            }
        }
        view.showMenu(data);
    }

    @Override
    public void initBigMonster() {
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
        BigMonstersDao bigMonstersDao = DatabaseManager.getInstance().getBigMonstersDao();
        List<BigMonsters> bigMonsters = bigMonstersDao.loadAll();
        if (bigMonsters != null && bigMonsters.size() > 0) {
            BigMonsters one = bigMonsters.get(0);
            if (one.isGone == 0 && one.isDead == 1) {
                view.showBigMonsterInvade(String.format(view.getString(R.string.string_big_monsters_distance_invade),
                        10 - HeroManager.getInstance().getHeroAttributes().days));
                return;
            }
            BigMonsters two = bigMonsters.get(1);
            if (two.isGone == 0 && two.isDead == 1) {
                view.showBigMonsterInvade(String.format(view.getString(R.string.string_big_monsters_distance_invade),
                        30 - HeroManager.getInstance().getHeroAttributes().days));
                return;
            }
            BigMonsters three = bigMonsters.get(2);
            if (three.isGone == 0 && three.isDead == 1) {
                view.showBigMonsterInvade(String.format(view.getString(R.string.string_big_monsters_distance_invade),
                        50 - HeroManager.getInstance().getHeroAttributes().days));
                return;
            }
        }
    }

    @Override
    public void onClickBigMonsters(BigMonstersModelVM vm) {
        AttackResp resp = HeroAttributesManager.getInstance().attackBigMonsters(vm.id.get());
        switch (resp.attackStatus) {
            case HeroAttributesManager.ATTACK_STATUS_ERROR: {
                ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
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
                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_attack_big_monster_suc)
                        , String.format(view.getContext().getString(R.string.string_attack_win), resp.money, resp.exp, resp.life), true);
                // 刷新时间
                initBigMonsterInvade();
                break;
            }
            case HeroAttributesManager.ATTACK_STATUS_HAS_DROP_GOODS: {
                String dropGood = "";
                for (String name : resp.dropGoods) {
                    dropGood = name + "、";
                }
                dropGood = dropGood.substring(0, dropGood.length() - 1);
                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_attack_big_monster_suc)
                        , String.format(view.getContext().getString(R.string.string_attack_win_has_goods), resp.money, resp.exp, resp.life, dropGood), true);
                break;
            }
            default:
                break;
        }
        initBigMonster();
    }


    @Override
    public void unSubscribe() {

    }
}
