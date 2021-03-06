package com.mdove.levelgame.main.hero.model;

import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.SkillDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Skill;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.monsters.utils.DropGoodsManager;
import com.mdove.levelgame.main.task.TaskManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MDove on 2018/11/2
 */
public class HeroAttributesWrapper {
    public Long id;
    private int attack;
    private int armor;
    private int curLife;
    private int maxLife;
    // 攻击速度，毫秒级（对应Rx的弹射间隔，此值越来越低）
    public long attackSpeed;
    private List<Skill> equipSkill;
    private InnerSkillModel innerSkillModel;

    private static class SingletonHolder {
        static final HeroAttributesWrapper INSTANCE = new HeroAttributesWrapper();
    }

    public static HeroAttributesWrapper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private HeroAttributesWrapper() {
        innerSkillModel = new InnerSkillModel();
        resetAttributes();
        resetSkill();
    }

    // 获取最新的Attributes值
    private HeroAttributes resetAttributes() {
        HeroAttributes heroAttributes = HeroManager.getInstance().getHeroAttributes();
        resetAttributes(heroAttributes);
        return heroAttributes;
    }

    public void resetSkill() {
        equipSkill = new ArrayList<>();
        List<Packages> packages = DatabaseManager.getInstance().getPackagesDao().queryBuilder().where(PackagesDao.Properties.IsEquip.eq(0)).list();
        if (packages != null) {
            for (Packages pk : packages) {
                if (pk.type.startsWith("J")) {
                    Skill skill = DatabaseManager.getInstance().getSkillDao().queryBuilder().where(SkillDao.Properties.Type.eq(pk.type)).unique();
                    if (skill == null) {
                        continue;
                    }
                    equipSkill.add(skill);
                }
            }
        }
        computeSkills();
    }

    private void resetAttributes(HeroAttributes heroAttributes) {
        id = heroAttributes.id;
        attack = heroAttributes.attack;
        armor = heroAttributes.armor;
        curLife = heroAttributes.curLife;
        maxLife = heroAttributes.maxLife;
        attackSpeed = heroAttributes.attackSpeed;
    }

    // 为以后增加技能/特殊属性做准备
    public int realAttack() {
        resetAttributes();
        // 计算是否命中暴击
        if (DropGoodsManager.getInstance().isHitProbability(innerSkillModel.attackHeavyProbability)) {
            attack = (int) (attack * innerSkillModel.attackHeavy);
        }
        return attack;
    }

    // 计算伤害并更新数据库
    public int computeHarmLife(int enemyAttack) {
        HeroAttributes heroAttributes = resetAttributes();
        int harm = enemyAttack - armor;
        if (harm < 0) {
            harm = 0;
        }
        heroAttributes.curLife -= harm;
        if (heroAttributes.curLife < 0) {
            // 如果没有买药的钱，默认给10血
            if (heroAttributes.money <= 20) {
                heroAttributes.curLife = 10;
            } else {
                heroAttributes.curLife = 0;
            }
        }
        HeroManager.getInstance().save();
        curLife = heroAttributes.curLife;
        return harm;
    }

    public void setHeroBloodSuck(int suck) {
        HeroAttributes heroAttributes = resetAttributes();
        heroAttributes.curLife += suck;
        if (heroAttributes.curLife > heroAttributes.maxLife) {
            heroAttributes.curLife = heroAttributes.maxLife;
        }
        HeroManager.getInstance().save();
    }

    // 为以后增加技能/特殊属性做准备
    public int realArmor() {
        resetAttributes();
        return armor;
    }

    public InnerSkillModel getInnerSkillModel() {
        return innerSkillModel;
    }

    public void awardMonster(Monsters monsters) {
        if (monsters.isLimitCount == 0) {
            monsters.curCount -= 1;
            DatabaseManager.getInstance().getMonstersDao().update(monsters);
        }
        TaskManager.Companion.getInstance().computeTask(monsters.type);
        HeroAttributesManager.getInstance().heroLevel(monsters.exp);
        HeroAttributesManager.getInstance().saveMoney(monsters.money);

        int liLiangRandmo = monsters.getExpLiLiang().get(0) + new Random(System.currentTimeMillis()).nextInt(monsters.getExpLiLiang().get(1) - monsters.getExpLiLiang().get(0));
        int minJieRandmo = monsters.getExpMinJie().get(0) + new Random(System.currentTimeMillis() + 1).nextInt(monsters.getExpMinJie().get(1) - monsters.getExpMinJie().get(0));
        int zhiHuiRandmo = monsters.getExpZhiHui().get(0) + new Random(System.currentTimeMillis() + 2).nextInt(monsters.getExpZhiHui().get(1) - monsters.getExpZhiHui().get(0));
        int qiangZhuangRandmo = monsters.getExpQiangZhuang().get(0) + new Random(System.currentTimeMillis() + 3).nextInt(monsters.getExpQiangZhuang().get(1) - monsters.getExpQiangZhuang().get(0));
        HeroAttributesManager.getInstance().heroAttributesLevel(HeroAttributesManager.ATTRIBUTE_TYPE_LILIANG, liLiangRandmo);
        HeroAttributesManager.getInstance().heroAttributesLevel(HeroAttributesManager.ATTRIBUTE_TYPE_MINJIE, minJieRandmo);
        HeroAttributesManager.getInstance().heroAttributesLevel(HeroAttributesManager.ATTRIBUTE_TYPE_ZHIHUI, zhiHuiRandmo);
        HeroAttributesManager.getInstance().heroAttributesLevel(HeroAttributesManager.ATTRIBUTE_TYPE_QIANGZHUANG, qiangZhuangRandmo);
    }

    public int realCurLife() {
        return curLife;
    }

    private void computeSkills() {
        // 先重置
        innerSkillModel.clear();

        if (equipSkill != null && equipSkill.size() > 0) {
            for (Skill skill : equipSkill) {
                // 暴击倍数不叠加
                if (skill.attackHeavy > innerSkillModel.attackHeavy) {
                    innerSkillModel.attackHeavy = skill.attackHeavy;
                }
                innerSkillModel.attackHeavyProbability += skill.attackHeavyProbability;
                innerSkillModel.bloodSuckProbability += skill.bloodSuckProbability;
                innerSkillModel.ignoreArmorProbability += skill.ignoreArmorProbability;
                innerSkillModel.ignoreAttackProbability += skill.ignoreAttackProbability;
                innerSkillModel.realAttack += skill.realAttack;
                // 眩晕不叠加
                if (skill.dizziness > innerSkillModel.dizziness) {
                    innerSkillModel.dizziness = skill.dizziness;
                }
                innerSkillModel.dizzinessProbability += skill.dizzinessProbability;
            }
        }
        if (innerSkillModel.ignoreArmorProbability >= 1) {
            innerSkillModel.ignoreArmorProbability = 0.99F;
        }
        if (innerSkillModel.ignoreAttackProbability >= 1) {
            innerSkillModel.ignoreAttackProbability = 0.99F;
        }
    }

    // 技能的总属性变量
    public class InnerSkillModel {
        // 暴击倍数，如：1.2
        public float attackHeavy;
        // 暴击几率 如：02
        public float attackHeavyProbability;
        // 吸血率 如：0.2
        public float bloodSuckProbability;
        // 无视防御（%） 如：0.2
        public float ignoreArmorProbability;
        // 无视攻击（%） 如：0.2
        public float ignoreAttackProbability;
        // 真实额外伤害
        public long realAttack;
        // 眩晕 如：1200 毫秒
        public long dizziness;
        // 眩晕几率 如：0.2（应对被动）
        public float dizzinessProbability;

        public void clear() {
            attackHeavy = 0;
            attackHeavyProbability = 0;
            bloodSuckProbability = 0;
            ignoreArmorProbability = 0;
            ignoreAttackProbability = 0;
            realAttack = 0;
            dizziness = 0;
            dizzinessProbability = 0;
        }
    }
}
