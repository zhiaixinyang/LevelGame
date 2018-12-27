package com.mdove.levelgame.greendao.utils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/12/27.
 */
public class IntegerConverter implements PropertyConverter<List<Integer>, String> {
    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            List<Integer> list = new ArrayList<>();
            String[] strings = databaseValue.split(",");
            for (String s : strings) {
                list.add(Integer.valueOf(s));
            }
            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (Integer integer : entityProperty) {
                sb.append(integer);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
