package com.mdove.levelgame.main.hero.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.greendao.entity.Skill;
import com.mdove.levelgame.greendao.utils.SrcIconMap;

/**
 * @author MDove on 2018/11/10
 */
public class HeroSkillModelVM {
    public ObservableField<Long> pkId = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> btnName = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();
    public Skill skill;

    public HeroSkillModelVM(Skill skill) {
        this.skill = skill;
        pkId.set(skill.id);
        this.tips.set(skill.tips);
        src.set(SrcIconMap.getInstance().getSrc(skill.type));
        this.type.set(skill.type);
        this.name.set(skill.name);
    }

    public void reName(boolean isEquip) {
        if (isEquip){
            btnName.set("卸下技能");
        }else{
            btnName.set("装备技能");
        }
    }
}
