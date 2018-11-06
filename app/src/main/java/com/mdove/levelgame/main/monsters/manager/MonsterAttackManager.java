package com.mdove.levelgame.main.monsters.manager;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.hero.model.HeroAttributesWrapper;
import com.mdove.levelgame.main.monsters.manager.exception.AttackMonsterException;
import com.mdove.levelgame.main.monsters.model.MonsterWrapper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author MDove on 2018/11/2
 */
public class MonsterAttackManager {
    private HeroAttributes heroAttributes;

    private static class SingletonHolder {
        static final MonsterAttackManager INSTANCE = new MonsterAttackManager();
    }

    public static MonsterAttackManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private MonsterAttackManager() {
        heroAttributes = HeroManager.getInstance().getHeroAttributes();
    }

    public Observable<Integer> attackEnemy(final Monsters monsters) {
        final MonsterWrapper wrapper = new MonsterWrapper(monsters);
        return Observable.interval(heroAttributes.attackSpeed, TimeUnit.MILLISECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        if (!HeroAttributesManager.getInstance().computeCurLife()) {
                            throw new AttackMonsterException(AttackMonsterException.ERROR_CODE_HERO_IS_NO_LIFE,
                                    AttackMonsterException.ERROR_TITLE_HERO_IS_NO_LIFE, AttackMonsterException.ERROR_MSG_HERO_IS_NO_LIFE);
                        }
                        if (!HeroAttributesManager.getInstance().computePowerIsHas(monsters.consumePower)) {
                            throw new AttackMonsterException(AttackMonsterException.ERROR_CODE_HERO_NO_POWER,
                                    AttackMonsterException.ERROR_TITLE_HERO_NO_POWER, AttackMonsterException.ERROR_MSG_HERO_NO_POWER);
                        }
                        if (!HeroAttributesManager.getInstance().computeLimitConut(monsters)) {
                            throw new AttackMonsterException(AttackMonsterException.ERROR_CODE_HERO_NO_COUNT,
                                    AttackMonsterException.ERROR_TITLE_HERO_NO_COUNT, AttackMonsterException.ERROR_MSG_HERO_NO_COUNT);
                        }
                        int heroRealAttack = HeroAttributesWrapper.getInstance().realAttack();
                        int enemyConsumeLife = wrapper.computeHarmLife(heroRealAttack);
                        if (wrapper.realCurLife() <= 0) {
                            HeroAttributesWrapper.getInstance().awardMonster(monsters);
                            throw new AttackMonsterException(AttackMonsterException.ERROR_CODE_MONSTERS_IS_DEAD,
                                    AttackMonsterException.ERROR_TITLE_MONSTERS_IS_DEAD,
                                    AttackMonsterException.ERROR_MSG_MONSTERS_IS_DEAD + String.format(App.getAppContext().getString(R.string.string_attack_win_new), monsters.money, monsters.exp));
                        }
                        return enemyConsumeLife;
                    }
                }).compose(RxTransformerHelper.<Integer>schedulerTransf());
    }

    public Observable<Integer> attackHero(final Monsters monsters) {
        final HeroAttributesWrapper heroWrapper = HeroAttributesWrapper.getInstance();
        final MonsterWrapper monstersWrapper = new MonsterWrapper(monsters);

        return Observable.interval(monsters.attackSpeed, TimeUnit.MILLISECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        int monsterRealAttack = monstersWrapper.realAttack();
                        int heroConsumeLife = heroWrapper.computeHarmLife(monsterRealAttack);
                        if (heroWrapper.realCurLife() <= 0) {
                            // return 新状态
                            throw new AttackMonsterException(AttackMonsterException.ERROR_CODE_HERO_IS_DEAD,
                                    AttackMonsterException.ERROR_TITLE_HERO_IS_DEAD, AttackMonsterException.ERROR_MSG_HERO_IS_DEAD);
                        }
                        return heroConsumeLife;
                    }
                }).compose(RxTransformerHelper.<Integer>schedulerTransf());
    }
}
