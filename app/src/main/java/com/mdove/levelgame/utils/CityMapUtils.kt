package com.mdove.levelgame.utils

/**
 * Created by MDove on 2019/1/5.
 */

object CityMapUtils {
    fun cityMapMonsterPlace(cityId: Long): Long {
        var monsterPlaceId: Long = when (cityId) {
            3.toLong() -> {
                1
            }
            4.toLong() -> {
                2
            }
            5.toLong() -> {
                4
            }
            6.toLong() -> {
                3
            }
            7.toLong() -> {
                5
            }
            8.toLong() -> {
                6
            }
            9.toLong() -> {
                7
            }
            else -> {
                cityId
            }
        }
        return monsterPlaceId
    }
}
