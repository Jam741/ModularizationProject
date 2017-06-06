package com.yingwumeijia.baseywmj.function.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yingwumeijia.baseywmj.base.JBaseFragment

/**
 * Created by jamisonline on 2017/5/31.
 * 主页面适配器
 */
class MainPageAdapter(var mFragment: ArrayList<JBaseFragment>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return mFragment[position]
    }

    override fun getCount(): Int {
        return mFragment.size
    }

}