package com.mdove.levelgame.base;

import android.databinding.BindingAdapter;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.main.hero.model.HeroEquipModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.monsters.model.vm.HeroAttrModelVM;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.view.HorizontalSmoothProgressBar;

/**
 * Created by MDove on 2018/3/5.
 */

public class DataBindingAdapter {
    @BindingAdapter("htmlColorText")
    public static void htmlColorText(TextView view, String str) {
        if (!TextUtils.isEmpty(str)) {
            view.setText(Html.fromHtml(str));
        }
    }

    @BindingAdapter("noGoodsBackGround")
    public static void noGoodsBackGround(ConstraintLayout main, HeroEquipModelVM vm) {
        if (vm == null) {
            return;
        }
        TextView textView = main.findViewById(R.id.tv_name);
        TextView btnTakeOff = main.findViewById(R.id.btn_take_off);
        if (vm.pkId.get() == -1) {
            textView.setTextColor(ContextCompat.getColor(main.getContext(), R.color.white));
            main.setBackgroundResource(R.drawable.bg_blue_round);
            btnTakeOff.setVisibility(View.GONE);
        } else {
            textView.setTextColor(ContextCompat.getColor(main.getContext(), R.color.black));
            main.setBackgroundResource(R.drawable.bg_white_round);
            btnTakeOff.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("materialIsGone")
    public static void materialsIsGone(TextView view, HeroPackageModelVM vm) {
        // 材料隐藏 装备View
        if (vm.type.get().startsWith("E")) {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("convertBgBySpecial")
    public static void convertBgBySpecial(ConstraintLayout view, MonstersModelVM vm) {
        if (vm.isBusinessman.get()) {
            view.setBackgroundResource(R.drawable.bg_businessman);
        }else{
            view.setBackgroundResource(R.drawable.bg_white_round_t_80);
        }
    }

    @BindingAdapter("myCtrlProgress")
    public static void myCtrlProgress(HorizontalSmoothProgressBar view, HeroAttrModelVM vm) {
        view.setProgress(vm.lifeProgress.get());
    }
}