package com.mdove.levelgame.main.shop.di

import com.mdove.levelgame.main.shop.adapter.ShopMedicinesAdapter
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesPresenter
import com.ss.android.buzz.dependencies.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * Created by MDove on 2018/12/23.
 */
@Module
class ShopMedicainesModule {

    @FragmentScope
    @Provides
    fun providesPresenter(): ShopMedicinesPresenter {
        return ShopMedicinesPresenter()
    }

    @FragmentScope
    @Provides
    fun providesMedicainesAdapter(presenter: ShopMedicinesPresenter): ShopMedicinesAdapter {
        return ShopMedicinesAdapter(presenter)
    }
}