package com.mdove.levelgame.main.monsters.manager;

import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.hero.model.HeroAttributesWrapper;
import com.mdove.levelgame.main.monsters.manager.exception.AttackMonsterException;
import com.mdove.levelgame.main.monsters.model.MonsterWrapper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
                        int heroRealAttack = HeroAttributesWrapper.getInstance().realAttack();
                        int enemyConsumeLife = wrapper.computeHarmLife(heroRealAttack);
                        if (wrapper.realCurLife() <= 0) {
                            // return 新状态
                            throw new AttackMonsterException(AttackMonsterException.ERROR_CODE_MONSTERS_IS_DEAD,
                                    AttackMonsterException.ERROR_TITLE_MONSTERS_IS_DEAD, AttackMonsterException.ERROR_MSG_MONSTERS_IS_DEAD);
                        }
                        return enemyConsumeLife;
                    }
                }).compose(RxTransformerHelper.<Integer>schedulerTransf());
    }
}
