package com.mdove.levelgame.greendao.utils;

import android.text.TextUtils;

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
        map.put("A1", R.mipmap.a1);
        map.put("A2", R.mipmap.a2);
        map.put("A3", R.mipmap.a3);
        map.put("A4", R.mipmap.a4);
        map.put("A5", R.mipmap.a5);
        map.put("A6", R.mipmap.a6);
        map.put("A7", R.mipmap.a7);
        map.put("A8", R.mipmap.a8);
        map.put("A9", R.mipmap.a9);
        map.put("A12", R.mipmap.a12);
        map.put("A13", R.mipmap.a13);
        map.put("A14", R.mipmap.a14);
        map.put("A15", R.mipmap.a15);
        map.put("A16", R.mipmap.a16);
        map.put("A17", R.mipmap.a17);
        map.put("B1", R.mipmap.b1);
        map.put("B2", R.mipmap.b2);
        map.put("B3", R.mipmap.b3);
        map.put("B4", R.mipmap.b4);
        map.put("B5", R.mipmap.b5);
        map.put("B6", R.mipmap.b6);
        map.put("B7", R.mipmap.b7);
        map.put("B8", R.mipmap.b8);
        map.put("D1", R.mipmap.d1);
        map.put("D2", R.mipmap.d2);
        map.put("D3", R.mipmap.d3);
        map.put("D4", R.mipmap.d4);
        map.put("E1", R.mipmap.e1);
        map.put("E2", R.mipmap.e2);
        map.put("E3", R.mipmap.e3);
        map.put("E4", R.mipmap.e4);
        map.put("E5", R.mipmap.e5);
        map.put("E6", R.mipmap.e6);
        map.put("E7", R.mipmap.e7);
        map.put("E8", R.mipmap.e8);
        map.put("E9", R.mipmap.e9);
        map.put("E10", R.mipmap.e10);
        map.put("G1", R.mipmap.g1);
        map.put("G2", R.mipmap.g2);
        map.put("G3", R.mipmap.g3);
        map.put("main1", R.mipmap.main_1);
        map.put("main2", R.mipmap.main_2);
        map.put("main3", R.mipmap.main_3);
        map.put("main4", R.mipmap.main_4);
        map.put("main5", R.mipmap.main_5);
        map.put("I1", R.mipmap.i1);
        map.put("I2", R.mipmap.i2);
        map.put("I3", R.mipmap.i3);
        map.put("I4", R.mipmap.i4);
        map.put("I5", R.mipmap.i5);
        map.put("I6", R.mipmap.i6);
        map.put("I7", R.mipmap.i7);
    }

    public int getSrc(String type) {
        if (TextUtils.isEmpty(type)) {
            return R.mipmap.ic_launcher;
        }
        if (map.get(type) == null) {
            return R.mipmap.ic_launcher;
        }
        return map.get(type);
    }
}
