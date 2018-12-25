package com.mdove.levelgame.view.cycleviewpager

import android.content.Context
import android.util.AttributeSet
import com.mdove.levelgame.view.base.SSImageView


class BannerViewPagerBase : BaseCycleViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadImage(url: String, imageView: SSImageView) {
//        GlideUtils.loadBmp(context, url, imageView)
    }
}
