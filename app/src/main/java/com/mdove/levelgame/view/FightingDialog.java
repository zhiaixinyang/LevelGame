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
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.monsters.model.vm.HeroAttrModelVM;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.utils.SystemUtils;

/**
 * Created by MDove on 2018/11/1.
 */

public class FightingDialog extends AppCompatDialog {
    private DialogFightingBinding binding;
    private HeroAttrModelVM myVm;
    private MonstersModelVM enVm;

    public FightingDialog(Context context) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_fighting,
                null, false);
        setContentView(binding.getRoot());
        WindowManager.LayoutParams paramsWindow = getWindow().getAttributes();
        paramsWindow.width = getWindowWidth();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myVm = new HeroAttrModelVM(HeroManager.getInstance().getHeroAttributes());
        enVm = new MonstersModelVM(DatabaseManager.getInstance().getMonstersDao().queryBuilder().where(MonstersDao.Properties.Id.eq(1)).unique());

        binding.setEnemyVm(enVm);
        binding.setMyVm(myVm);
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
}
