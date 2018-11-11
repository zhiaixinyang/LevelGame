package com.mdove.levelgame.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.DialogBigFightingBinding;
import com.mdove.levelgame.databinding.DialogFightingBinding;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.monsters.manager.MonsterAttackManager;
import com.mdove.levelgame.main.monsters.manager.exception.AttackMonsterException;
import com.mdove.levelgame.main.monsters.model.vm.BigFightMonstersVM;
import com.mdove.levelgame.main.monsters.model.vm.FightMonstersVM;
import com.mdove.levelgame.main.monsters.model.vm.HeroAttrModelVM;
import com.mdove.levelgame.main.monsters.utils.BigMonsterHelper;
import com.mdove.levelgame.utils.SystemUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by MDove on 2018/11/11.
 */

public class BigFightingDialog extends AppCompatDialog {
    private DialogBigFightingBinding binding;
    private HeroAttrModelVM myVm;
    private BigFightMonstersVM enVm;
    private BigMonsters monster;
    private Context context;
    private Disposable heroDisposable;
    private Disposable monstersDisposable;

    public BigFightingDialog(Context context, BigMonsters monster) {
        super(context,R.style.BaseDialog);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_big_fighting,
                null, false);
        setContentView(binding.getRoot());
        WindowManager.LayoutParams paramsWindow = getWindow().getAttributes();
        paramsWindow.width = getWindowWidth();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        this.monster = monster;
        heroDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myVm = new HeroAttrModelVM(HeroManager.getInstance().getHeroAttributes());
        enVm = new BigFightMonstersVM(monster);

        binding.setEnemyVm(enVm);
        binding.setMyVm(myVm);
        computeAttack(BigMonsterHelper.Companion.bigMonsterToMonster(monster));
    }

    protected int getWindowWidth() {
        float percent = 0.9f;
        WindowManager wm = this.getWindow().getWindowManager();
        int screenWidth = SystemUtils.getScreenWidth(wm);
        int screenHeight = SystemUtils.getScreenHeight(wm);
        return (int) (screenWidth > screenHeight
                ? screenHeight * percent
                : screenWidth * percent);
    }

    private void computeAttack(final Monsters monster) {
        MonsterAttackManager.getInstance().attackEnemy(monster).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                heroDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                enVm.resetLife(integer);
                if (integer == 0) {
                    enVm.harm.set(context.getString(R.string.string_attack_no_harm));
                } else {
                    enVm.harm.set(-integer + "");
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof AttackMonsterException) {
                    AttackMonsterException exception = (AttackMonsterException) e;
                    dismiss();
                    switch (exception.errorCode) {
                        case AttackMonsterException.ERROR_CODE_HERO_IS_NO_LIFE:
                        case AttackMonsterException.ERROR_CODE_HERO_NO_POWER:
                        case AttackMonsterException.ERROR_CODE_HERO_NO_COUNT:
                        case AttackMonsterException.ERROR_CODE_MONSTERS_IS_DEAD_IS_DROP:
                        case AttackMonsterException.ERROR_CODE_MONSTERS_IS_DEAD: {
                            if (heroDisposable.isDisposed()) {
                                return;
                            }
                            if (!monstersDisposable.isDisposed()) {
                                monstersDisposable.dispose();
                            }
                            MyDialog.showMyDialog(context, exception.errorTitle, exception.errorMsg, true);
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
        MonsterAttackManager.getInstance().attackHero(monster).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                monstersDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                myVm.resetLife(integer);
                if (integer == 0) {
                    myVm.harm.set(context.getString(R.string.string_attack_no_harm));
                } else {
                    myVm.harm.set(-integer + "");
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof AttackMonsterException) {
                    AttackMonsterException exception = (AttackMonsterException) e;
                    dismiss();
                    if (exception.errorCode == AttackMonsterException.ERROR_CODE_HERO_IS_DEAD) {
                        if (monstersDisposable.isDisposed()) {
                            return;
                        }
                        if (!heroDisposable.isDisposed()) {
                            heroDisposable.dispose();
                        }
                        MyDialog.showMyDialog(context, exception.errorTitle, exception.errorMsg, true);
                    }
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

}