package com.mdove.levelgame.main.hero.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;

/**
 * Created by MDove on 2018/10/21.
 */

public class HeroAttributesModelVM {
    public ObservableField<String> attack = new ObservableField<>();
    public ObservableField<String> attackSpeed = new ObservableField<>();
    public ObservableField<String> armor = new ObservableField<>();
    public ObservableField<String> life = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> level = new ObservableField<>();
    public ObservableField<String> needExperience = new ObservableField<>();
    public ObservableField<String> skillCount = new ObservableField<>();
    public ObservableField<String> bodyPower = new ObservableField<>();
    public ObservableField<String> days = new ObservableField<>();
    public ObservableField<String> liLiang = new ObservableField<>();
    public ObservableField<String> minJie = new ObservableField<>();
    public ObservableField<String> zhiHui = new ObservableField<>();
    public ObservableField<String> qiangZhuang = new ObservableField<>();
    public ObservableField<Integer> liLiangProgress = new ObservableField<>();
    public ObservableField<Integer> minJieProgress = new ObservableField<>();
    public ObservableField<Integer> zhiHuiProgress = new ObservableField<>();
    public ObservableField<Integer> qiangZhuangProgress = new ObservableField<>();

    public HeroAttributesModelVM(HeroAttributes heroAttributes) {
        attack.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_attack), heroAttributes.attack, heroAttributes.attackIncrease));
        armor.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_armor), heroAttributes.armor, heroAttributes.armorIncrease));
        life.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_life), heroAttributes.curLife, heroAttributes.maxLife, heroAttributes.lifeIncrease));
        money.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_money), heroAttributes.money));
        level.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_level), heroAttributes.level));
        attackSpeed.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_attack_speed), heroAttributes.attackSpeed));
        days.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_days), heroAttributes.days));
        needExperience.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_need_exp), heroAttributes.experience,
                HeroAttributesManager.getInstance().getLevelExp(heroAttributes)));
        bodyPower.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_body_power), heroAttributes.bodyPower, 100));
        skillCount.set(String.format(App.getAppContext().getString(R.string.hero_attributes_msg_skill_count), heroAttributes.skillCount, heroAttributes.level));

        long liliangExp = heroAttributes.liLiangExp;
        long liliangLevelExp = HeroAttributesManager.getInstance().getAttributeExp(HeroAttributesManager.ATTRIBUTE_TYPE_LILIANG, heroAttributes);
        liLiangProgress.set((int)(((float) liliangExp / (float) liliangLevelExp) * 100));
        liLiang.set(String.format(App.getAppContext().getString(R.string.hero_attributes_liliang_and_exp),
                heroAttributes.liLiang, liliangExp, liliangLevelExp));

        long minJieExp = heroAttributes.minJieExp;
        long minJieLevelExp = HeroAttributesManager.getInstance().getAttributeExp(HeroAttributesManager.ATTRIBUTE_TYPE_MINJIE, heroAttributes);
        minJieProgress.set((int)(((float) minJieExp / (float) minJieLevelExp) * 100));
        minJie.set(String.format(App.getAppContext().getString(R.string.hero_attributes_minjie_and_exp),
                heroAttributes.minJie, minJieExp, minJieLevelExp));

        long zhiHuiExp = heroAttributes.zhiHuiExp;
        long zhiHuiLevelExp = HeroAttributesManager.getInstance().getAttributeExp(HeroAttributesManager.ATTRIBUTE_TYPE_ZHIHUI, heroAttributes);
        zhiHuiProgress.set((int)(((float) zhiHuiExp / (float) zhiHuiLevelExp) * 100));
        zhiHui.set(String.format(App.getAppContext().getString(R.string.hero_attributes_zhihui_and_exp),
                heroAttributes.zhiHui, zhiHuiExp, zhiHuiLevelExp));

        long qiangZhuangExp = heroAttributes.qiangZhuangExp;
        long qiangZhunagLevelExp = HeroAttributesManager.getInstance().getAttributeExp(HeroAttributesManager.ATTRIBUTE_TYPE_QIANGZHUANG, heroAttributes);
        qiangZhuangProgress.set((int)(((float) qiangZhuangExp / (float) qiangZhunagLevelExp) * 100));
        qiangZhuang.set(String.format(App.getAppContext().getString(R.string.hero_attributes_qiangzhuang_and_exp),
                heroAttributes.qiangZhuang, qiangZhuangExp, qiangZhunagLevelExp));
    }
}
