package com.mdove.levelgame.greendao.utils;

import com.mdove.levelgame.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MDove on 2018/11/2.
 */

public class WeaponsIconMap {
    private static final class Holder {
        private static final WeaponsIconMap INSTANCE = new WeaponsIconMap();
    }

    /**
     * 使用单例模式获得操作数据库的对象
     *
     * @return
     */
    public static WeaponsIconMap getInstance() {
        return Holder.INSTANCE;
    }

    private Map<String, Integer> map;

    private WeaponsIconMap() {
        map = new HashMap<>();
        map.put("A1", R.mipmap.ic_xiudao);
        map.put("A2", R.mipmap.ic_kanchaidao);
        map.put("A3", R.mipmap.ic_changdao);
        map.put("A4", R.mipmap.ic_zhongfu);
        map.put("A5", R.mipmap.ic_zhanshenqiang);
        map.put("B1", R.mipmap.ic_buyi);
        map.put("B2", R.mipmap.ic_bujia);
        map.put("B3", R.mipmap.ic_qiongkuijia);
        map.put("B4", R.mipmap.ic_zhongkuijia);
        map.put("B5", R.mipmap.ic_zhanshenjia);
        map.put("D1", R.mipmap.ic_zhongcaoyao);
        map.put("D2", R.mipmap.ic_diedayao);
        map.put("D3", R.mipmap.ic_jinchaungyao);
        map.put("D4", R.mipmap.ic_tianshanxuelian);
    }

    public int getSrc(String type) {
        return map.get(type);
    }
}
