package com.mdove.levelgame.main.shop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.mdove.levelgame.R
import com.mdove.levelgame.base.BaseActivity
import com.mdove.levelgame.main.shop.fragment.ShopArmorFragment
import com.mdove.levelgame.main.shop.fragment.ShopAttackFragment
import com.mdove.levelgame.main.shop.fragment.ShopMedicinesFragment
import com.mdove.levelgame.main.shop.presenter.contract.ShopContract
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by MDove on 2018/12/23.
 */
class ShopActivity : BaseActivity(), ShopContract.IMedicinesShopView, HasSupportFragmentInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>

    private lateinit var tab: TabLayout
    private lateinit var vp: ViewPager
    private val titles: Array<String> by lazy {
        arrayOf(getString(R.string.string_fragment_title_buy_attack), getString(R.string.string_fragment_title_buy_armor), getString(R.string.string_fragment_title_buy_medicine))
    }
    private val fragments: MutableList<Fragment> by lazy {
        mutableListOf<Fragment>()
    }

    override fun isNeedCustomLayout(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        tab = findViewById(R.id.tab)
        vp = findViewById(R.id.vp)

        fragments.add(ShopAttackFragment.newInstance())
        fragments.add(ShopArmorFragment.newInstance())
        fragments.add(ShopMedicinesFragment.newInstance())

        vp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                return titles[position]
            }
        }
        tab.setupWithViewPager(vp)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return injector
    }

    companion object {
        fun start(context: Context) {
            val start = Intent(context, ShopActivity::class.java)
            if (context !is Activity) {
                start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(start)
        }
    }
}
