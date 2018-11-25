package com.mdove.levelgame.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.databinding.DialogFightingBinding;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.monsters.manager.MonsterAttackManager;
import com.mdove.levelgame.main.monsters.manager.exception.AttackMonsterException;
import com.mdove.levelgame.main.monsters.model.MonsterWrapper;
import com.mdove.levelgame.main.monsters.model.vm.FightMonstersVM;
import com.mdove.levelgame.main.monsters.model.vm.HeroAttrModelVM;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.utils.AnimationUtils;
import com.mdove.levelgame.utils.SystemUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/11/1.
 */

public class FightingDialog extends AppCompatDialog {
    private DialogFightingBinding binding;
    private HeroAttrModelVM myVm;
    private FightMonstersVM enVm;
    private Monsters monster;
    private Context context;
    private Disposable heroDisposable;
    private Disposable monstersDisposable;
    private AnimationDrawable attackAnim;

    public FightingDialog(Context context, Monsters monster) {
        super(context, R.style.BaseDialog);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_fighting,
                null, false);
        setContentView(binding.getRoot());

        WindowManager.LayoutParams paramsWindow = getWindow().getAttributes();
        paramsWindow.width = getWindowWidth();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        this.monster = monster;
        heroDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myVm = new HeroAttrModelVM(HeroManager.getInstance().getHeroAttributes());
        enVm = new FightMonstersVM(monster);

        binding.setEnemyVm(enVm);
        binding.setMyVm(myVm);

        binding.tvRetreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heroDisposable != null && !heroDisposable.isDisposed()) {
                    heroDisposable.dispose();
                }
                if (monstersDisposable != null && !monstersDisposable.isDisposed()) {
                    monstersDisposable.dispose();
                }
                dismiss();
            }
        });

        initAttackAnim();
        computeAttack(monster);
    }

    private void initAttackAnim() {
        attackAnim = (AnimationDrawable) context.getResources().getDrawable(R.drawable.anim_skill_1);
        binding.anim.setBackground(attackAnim);
        attackAnim.start();
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
        MonsterAttackManager.getInstance().attackEnemy(monster).subscribe(new Observer<MonsterWrapper.HarmResp>() {
            @Override
            public void onSubscribe(Disposable d) {
                heroDisposable = d;
            }

            @Override
            public void onNext(MonsterWrapper.HarmResp resp) {
                if (resp.harm == 0) {
                    enVm.harm.set(context.getString(R.string.string_attack_no_harm));
                } else {
                    enVm.resetLife(-resp.harm);
                    String harmStr = String.format(context.getString(R.string.string_hero_attack_harm), resp.harm);
                    if (resp.heroSuck > 0) {
                        myVm.resetLife(resp.heroSuck);
                        harmStr = String.format(context.getString(R.string.string_hero_attack_harm_with_suck), resp.harm, resp.heroSuck);
                    }
                    enVm.harm.set(harmStr);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(ObjectAnimator.ofFloat(binding.tvHarm, "scaleX", 1F, 2F, 1F),
                            ObjectAnimator.ofFloat(binding.tvHarm, "scaleY", 1F, 2F, 1F));
                    set.setDuration(750);
                    set.setInterpolator(new BounceInterpolator());
                    set.start();
                    Observable.just(1).delay(750, TimeUnit.MILLISECONDS)
                            .compose(RxTransformerHelper.<Integer>schedulerTransf())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    enVm.harm.set("");
                                }
                            });
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
                if (integer == 0) {
                    myVm.harm.set(context.getString(R.string.string_attack_no_harm));
                } else {
                    myVm.resetLife(-integer);
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