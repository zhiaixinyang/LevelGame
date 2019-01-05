package com.mdove.levelgame.main.hero.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mdove.levelgame.R

/**
 * Created by MDove on 2019/1/3.
 */
class HeroPackageDialogFragment : DialogFragment() {
    private val fragments: MutableList<Fragment> by lazy {
        mutableListOf<Fragment>()
    }
    private lateinit var titles: Array<String>
    private lateinit var heroEquipFragment: HeroEquipFragment
    private lateinit var heroPackageFragment: HeroPackageFragment
    private lateinit var heroSkillFragment: HeroSkillFragment
    private lateinit var btnClose: ImageView
    private lateinit var vp: ViewPager
    private lateinit var tab: TabLayout

    override fun onResume() {
        super.onResume()
        dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AppTheme_HeroPackageDialog)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hero_package_dialog, container, false)
        btnClose = view.findViewById(R.id.btn_close)
        vp = view.findViewById(R.id.vp)
        tab = view.findViewById(R.id.tab)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnClose.setOnClickListener {
            dismiss()
        }

        titles = arrayOf(getString(R.string.string_fragment_title_equip), getString(R.string.string_fragment_title_package), getString(R.string.string_fragment_title_skill))
        heroEquipFragment = HeroEquipFragment.newInstance()
        heroPackageFragment = HeroPackageFragment.newInstance()
        heroSkillFragment = HeroSkillFragment.newInstance()
        fragments.add(heroEquipFragment)
        fragments.add(heroPackageFragment)
        fragments.add(heroSkillFragment)

        vp.adapter = object : FragmentPagerAdapter(childFragmentManager) {
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

}