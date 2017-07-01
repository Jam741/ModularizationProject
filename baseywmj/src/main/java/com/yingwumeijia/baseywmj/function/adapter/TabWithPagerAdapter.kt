package com.yingwumeijia.baseywmj.function.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Jam on 2016/6/8 17:34.
 * Describe:
 */
class TabWithPagerAdapter : FragmentStatePagerAdapter {

    private var mTabs: List<String>? = null
    private var mFragments: List<Fragment>? = null
    private var mTitles: Array<String>? = null

    constructor(fm: FragmentManager, mTabs: List<String>, mFragments: List<Fragment>) : super(fm) {
        this.mTabs = mTabs
        this.mFragments = mFragments
    }


    constructor(fm: FragmentManager, titles: Array<String>, mFragments: List<Fragment>) : super(fm) {
        this.mTitles = titles
        this.mFragments = mFragments
    }

    constructor(fm: FragmentManager, mFragments: List<Fragment>) : super(fm) {
        this.mFragments = mFragments
    }

    override fun getItem(position: Int): Fragment {
        return mFragments!![position]
    }

    override fun getCount(): Int {
        return mFragments!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        if (mTitles == null)
            return mTabs!![position]
        else
            return mTitles!![position]
    }
}
