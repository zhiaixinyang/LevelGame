package com.mdove.levelgame.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.DialogFightingBinding;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.monsters.manager.MonsterAttackManager;
import com.mdove.levelgame.main.monsters.manager.exception.AttackMonsterException;
import com.mdove.levelgame.main.monsters.model.MonsterWrapper;
import com.mdove.levelgame.main.monsters.model.vm.HeroAttrModelVM;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.shop.BusinessmanActivity;
import com.mdove.levelgame.utils.SystemUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by MDove on 2018/11/1.
 */

public class FightingDialog extends AppCompatDialog {
    private DialogFightingBinding binding;
    private HeroAttrModelVM myVm;
    private MonstersModelVM enVm;
    private Monsters monster;
    private Context context;
    private CompositeDisposable heroDisposable;

    public FightingDialog(Context context, Monsters monster) {
        super(context);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_fighting,
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
        enVm = new MonstersModelVM(monster);

        binding.setEnemyVm(enVm);
        binding.setMyVm(myVm);
        computeAttack(monster);
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

    private void computeAttack(Monsters monster) {
        MonsterAttackManager.getInstance().attackEnemy(monster).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                heroDisposable.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                enVm.resetLife(integer);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof AttackMonsterException) {
                    AttackMonsterException exception = (AttackMonsterException) e;
                    dismiss();
                    MyDialog.showMyDialog(context, exception.errorTitle, exception.errorMsg, true);
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
