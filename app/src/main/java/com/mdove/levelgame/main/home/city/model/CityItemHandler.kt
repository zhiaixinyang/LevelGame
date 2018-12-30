package com.mdove.levelgame.main.home.city.model

import com.mdove.levelgame.main.home.city.presenter.CityPresenter

/**
 * Created by MDove on 2018/12/28.
 */
class CityItemHandler(val presenter: CityPresenter) {
    fun onClick(cityVM: CityVM) {
        presenter.onClick(cityVM)
    }
}